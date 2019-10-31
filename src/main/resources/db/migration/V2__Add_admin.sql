insert into usr(id,  active, email, first_name, last_name, mobile, password, username)
values (0, true,'admin@gmail.com','Oleksandr','Ivaschenko','096-278-7501','123','admin');

insert into user_role(user_id, roles) VALUES (0,'USER'), (0,'ADMIN');

insert into message(id,filename,text,title,id_user) VALUES (0,'',
'По информации Sportando, защитник «Майами» Дион Уэйтерс возвращен в состав команды, но пропустит сегодняшний матч с «Атлантой».
Сообщалось, что Уэйтерс отстранен клубом от участия в первом матче сезона за «нарушение профессиональной этики». Однако защитник пропустит уже четвертую встречу подряд.
Также сообщалось, что «Майами» на протяжении года безуспешно пытается обменять Уэйтерса в другую команду.
','Дион Уэйтерс возвращен в состав «Майами», но пропустит матч с «Атлантой»',0);