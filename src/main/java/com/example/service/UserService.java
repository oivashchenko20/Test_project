package com.example.service;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
/**
 * This service show all main realize all main methods with user and articles
 *
 * @author Oleksandr_Ivaschenko
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    UserRepos userRepos;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepos.findByUsername(username);
        if (userDetails == null) {
            new UsernameNotFoundException("User is not exist");
        }

        return userDetails;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepos.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(user.getPassword());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setMobile(user.getMobile());

        userRepos.save(user);

        sendMessage(user);

        return true;

    }


    private void sendMessage(User user) {

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Copybook. Please, visit next link: http://test-spring-copybook.herokuapp.com//activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }


    public boolean activateUser(String code) {
        User user = userRepos.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);
        userRepos.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepos.findAll();
    }

    public void saveUser(User user, Map<String, String> form) {


        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepos.save(user);
    }

    public void updateProfile(User user, String password, String email, String firstName, String lastName, String mobile) {

        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);
        }

        if (!StringUtils.isEmpty(email)) {
            user.setActivationCode(UUID.randomUUID().toString());

        }


        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }

        if (!StringUtils.isEmpty(firstName)) {
            user.setFirstName(firstName);
        }

        if (!StringUtils.isEmpty(lastName)) {
            user.setLastName(lastName);
        }

        if (!StringUtils.isEmpty(mobile)) {
            user.setMobile(mobile);
        }

        user.setActive(false);
        userRepos.save(user);

        sendMessage(user);
    }


    public void setNewPassword(User user, String email) {

        Random random = new Random();
        Integer rand = random.nextInt(10000);

        if (user.getUsername() == null) {
            user = userRepos.findByEmail(email);
        }

        if (user != null && !StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, user \n" +
                            "Welcome to Copybook. Your new password " + rand +
                            " visit next link:http://https://test-spring-copybook.herokuapp.com//login "
            );
            user.setPassword(rand.toString());

            userRepos.save(user);
            mailSender.send(user.getEmail(), "Get password", message);
        }
    }
}

