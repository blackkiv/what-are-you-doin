create extension if not exists "uuid-ossp";

create table if not exists ${schema}.app_user
(
    id       uuid primary key unique default uuid_generate_v4(),
    username text not null unique,
    password text not null,
    token    uuid not null unique    default uuid_generate_v4()
);

create table if not exists ${schema}.track_log
(
    id        uuid primary key unique default uuid_generate_v4(),
    user_id   uuid   not null,
    app_name  text   not null,
    timestamp bigint not null,

    unique (user_id, app_name, timestamp)
);