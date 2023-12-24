drop table if exists role cascade;
create table if not exists role
(
    id   bigserial primary key,
    name varchar not null,
    unique (name)
);

drop table if exists "user" cascade;
create table if not exists "user"
(
    id         bigserial primary key,
    password   varchar not null,
    email      varchar not null,
    role_id    bigint  not null,
    premium    boolean not null,
    created_at timestamp without time zone,
    unique (email),
    foreign key (role_id) references role (id)
);

drop table if exists "post" cascade;
create table if not exists "post"
(
    id         bigserial primary key,
    context   varchar not null,
    user_id      bigserial not null,
    posted_at timestamp without time zone,
    unique (id),
    foreign key (user_id) references "user" (id)
);

drop table if exists "comment" cascade;
create table if not exists "comment"
(
    id         bigserial primary key,
    context   varchar not null,
    user_id      bigserial not null,
    post_id      bigserial not null,
    posted_at timestamp without time zone,
    unique (id),
    foreign key (user_id) references "user" (id),
    foreign key (post_id) references "post" (id)
);

drop table if exists "follow" cascade;
create table if not exists "follow"
(
    id         bigserial primary key,
    follower_id     bigserial not null,
    following_id     bigserial not null,
    foreign key (follower_id) references "user" (id),
    foreign key (following_id) references "user" (id)
);