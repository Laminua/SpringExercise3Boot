create table user_profile(
    id bigserial not null primary key,
    username varchar,
    role varchar,
    name varchar,
    email varchar,
    password varchar
);

insert into user_profile (username, role, name, email, password)
values ('admin', 'ROLE_ADMIN', 'Kirill', 'Kirill@email.com', 'admin');