create sequence hibernate_sequence start 1 increment 1;

create table message
(
    id       int4 not null,
    filename varchar(2048),
    text     varchar(2048),
    title    varchar(255),
    id_user  int4,
    primary key (id)
);

create table user_role
(
    user_id int4 not null,
    roles   varchar(255)
);

create table usr
(
    id              int4         not null,
    activation_code varchar(255),
    active          boolean,
    email           varchar(255) not null,
    first_name      varchar(255) not null,
    last_name       varchar(255) not null,
    mobile          varchar(255),
    password        varchar(255) not null,
    username        varchar(255) not null,
    primary key (id)
);

alter table if exists message
    add constraint message_user_fk foreign key (id_user) references usr ON DELETE cascade;
alter table if exists user_role
    add constraint user_role_user_fk foreign key (user_id) references usr ON DELETE Cascade;