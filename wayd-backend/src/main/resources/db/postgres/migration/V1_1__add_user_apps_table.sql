create table if not exists ${schema}.app_user_preference
(
    user_id        uuid primary key unique,
    apps_blacklist text[] not null default array []::text[],
    apps_whitelist text[] not null default array []::text[],
    foreign key (user_id) references ${schema}.app_user (id)
);

insert into ${schema}.app_user_preference(user_id)
select id
from ${schema}.app_user;

create table if not exists ${schema}.user_apps
(
    user_id  uuid not null,
    app_name text not null,
    primary key (user_id, app_name)
);

insert into ${schema}.user_apps (user_id, app_name)
select distinct user_id, app_name
from ${schema}.track_log
on conflict (user_id, app_name) do nothing;


create or replace function sync_user_apps_table() returns trigger as
$$
begin
    insert into ${schema}.user_apps (user_id, app_name)
    values (new.user_id, new.app_name)
    on conflict (user_id, app_name) do nothing;
    return new;
end;
$$ language plpgsql;

create trigger user_apps_projection_trigger
    after insert or update
    on ${schema}.track_log
    for each row
execute function sync_user_apps_table();

