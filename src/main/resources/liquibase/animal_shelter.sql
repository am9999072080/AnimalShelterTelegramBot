--liquibase formatted sql

--changeset am:1

create table users
(
    telegram_id  bigint not null primary key,
    first_name   varchar(200),
    last_name    varchar(200),
    phone_number varchar(200),
    shelter_type varchar(200),
    shelter_name varchar(200)
);

create table owners
(
    id           bigserial primary key,
    telegram_id  bigint,
    first_name   varchar(200),
    last_name    varchar(200),
    phone_number varchar(200),
    animal       varchar(200) not null
);

create table cat_shelter
(
    id            bigserial not null primary key,
    about_me      varchar(200),
    location      varchar(200),
    name          varchar(200),
    safety        varchar(200),
    security      varchar(200),
    working_hours varchar(200)
);


create table dog_shelter
(
    id            bigserial not null primary key,
    about_me      varchar(200),
    location      varchar(200),
    name          varchar(200),
    safety        varchar(200),
    security      varchar(200),
    working_hours varchar(200)
);


create table dog
(
    id         bigserial not null primary key,
    name       varchar(200),
    age        integer
        constraint check_age check ( age > 0 ),
    healthy    boolean,
    vaccinated boolean,
    owner_id   bigint,
    shelter_id bigint
        constraint dog_dog_shelter_id_fk references dog_shelter
);

create table cat
(
    id         bigserial not null primary key,
    name       varchar(200),
    age        integer
        constraint check_age check ( age > 0 ),
    healthy    boolean,
    vaccinated boolean,
    owner_id   bigint,
    shelter_id bigint
        constraint cat_cat_shelter_id_fk references cat_shelter
);


create table volunteers
(
    telegram_id bigint not null primary key,
    first_name  varchar(200),
    last_name   varchar(200)
);

create table adaptation_period
(
    id                  bigserial primary key,
    owner_id            bigint,
    animal_id           bigint,
    animal_type         varchar(200),
    start_date          date,
    end_date            date,
    result              varchar(200),
    date_of_last_report date
);


create table owner_report
(
    id                   bigserial primary key,
    adaptation_period_id bigint,
    receive_date         date,
    photo_id             varchar(200),
    food_ration          varchar(200),
    animal_health        varchar(200),
    animal_behavior      varchar(200)

);
