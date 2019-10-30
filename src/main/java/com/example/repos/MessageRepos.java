package com.example.repos;

import com.example.domain.Message;
import com.example.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepos extends CrudRepository<Message, Integer> {

    List<Message> findByTitle(String title);

    List<Message> findAll();

    Message deleteByAuthor(User author);

}
