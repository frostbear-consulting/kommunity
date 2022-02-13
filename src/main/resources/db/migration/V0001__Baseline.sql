-- Baseline migration for Kommunity

create extension if not exists "uuid-ossp";

create table account
(
    idAccount uuid not null default uuid_generate_v4(),
    username  varchar(16),
    password  varchar(64),

    constraint account_pk primary key (idAccount),
    constraint account_username_unq unique (username)
);

create table thread
(
    idThread   uuid         not null default uuid_generate_v4(),
    title      varchar(128) not null,
    text       text,
    author     uuid         not null,
    created_at timestamp    not null,

    constraint thread_pk primary key (idThread),
    constraint thread_author_fk foreign key (author) references account (idAccount)
);

create table comment
(
    idComment uuid not null default uuid_generate_v4(),
    comment   text,
    author    uuid not null,

    constraint comment_pk primary key (idComment),
    constraint comment_author_fk foreign key (author) references account (idAccount)
)