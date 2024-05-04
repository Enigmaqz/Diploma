create schema if not exists netology_diploma;

create table if not exists netology_diploma.users
(
    login varchar not null primary KEY,
    password varchar not null

);

create table if not exists netology_diploma.files
(
    filename    varchar not null,
    size        bigint not null,
    content     bigint not null,
    login       varchar(255),

    CONSTRAINT fk_user
    	FOREIGN KEY(login)
        	REFERENCES netology_diploma.users(login)
);

INSERT INTO netology_diploma.users  (login , password)
VALUES ('qwerty@gmail.com', '$2a$12$KEePb5sYVRITl4OxM4QwMO977MZyP9KdWrKB3f.rDzSLjrBVl7gGG');