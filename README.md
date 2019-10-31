# Project information   
This project is the test-project for Infostroy Technologies Inc company

## Author
Ivaschenko Oleksandr

Email: 
oleksandr.ivashchenko@gmail.com

## Installation

For installation you need authorize in HerokuApp and start project from repository.

https://test-spring-copybook.herokuapp.com/login

login - admin

password - 123 

## Functionality

Users in this project have two roles(user,admin). Admin can manipulate User.  
User can make functions such as:

- Log in the site;
- Registration;
- Forgot password;
- Add new article;
- Show all article;
- Delete article;
- Edit profile;

Admin has all functions of User and :

- Show User list;
- Edit user(give him role 'ADMIN');
- Remove user;

Additional functions:

- Save image to server;
- Forgot password(generate new password and send his to email);
- Validation on the server;

## Main classes

- UserService - service for all main methods(saveUser, addUser, activateUser);

- UserController - main controller for ADMIN functions(EditUser, UserList) and main for EditProfile page;

- RegisterController - main controller for registration and forgot password pages;

- MainController - main controller for articles(add,show,delete);

- Message - entity class for articles which contains description of variables of table Message;

- User - entity class for users which contains description of variables of table User;

- WebSecurityConfig - config for Spring Security and manipulate with roles;

- MailConfig - set all properties for mail configuration;

- MailSender - service for send email notifications;

## Main methods

- saveUser() - method that can save user to db;

- activateUser() - method that activated user after email notification;

- sendMessage() - method that formed message for email notification;

- updateProfile() - method that checked input users data and update his;

- setNewPassword() - method that formed message for email notification and set new password;

## Main tools

- Java 8;
- Spring Framework;
- Thymeleaf;
- Bootstrap 4;
- Postgres SQL;
- JavaScript;









## License
[MIT](https://choosealicense.com/licenses/mit/)