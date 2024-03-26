create sequence game_seq start with 1 increment by 50;
create sequence ship_location_seq start with 1 increment by 50;
create sequence shot_seq start with 1 increment by 50;
create sequence user_game_seq start with 1 increment by 50;
create sequence user_profile_seq start with 1 increment by 50;
create table game
(
    board_sizex  integer                     not null,
    board_sizey  integer                     not null,
    player_count integer                     not null,
    created      timestamp(6) with time zone not null,
    game_id      bigint                      not null,
    external_key UUID                        not null unique,
    primary key (game_id)
);
create table ship_location
(
    ship_number  integer not null check (ship_number >= 1),
    x            integer,
    y            integer,
    ship_id      bigint  not null,
    user_game_id bigint  not null,
    primary key (ship_id)
);
create table shot
(
    x                 integer,
    y                 integer,
    from_user_game_id bigint                      not null,
    shot_id           bigint                      not null,
    timestamp         timestamp(6) with time zone not null,
    to_user_game_id   bigint                      not null,
    primary key (shot_id)
);
create table user_game
(
    game_id      bigint not null,
    user_game_id bigint not null,
    user_id      bigint not null,
    external_key UUID   not null unique,
    primary key (user_game_id)
);
create table user_profile
(
    created         timestamp(6) with time zone not null,
    modified        timestamp(6) with time zone not null,
    user_profile_id bigint                      not null,
    external_key    UUID                        not null unique,
    oauth_key       varchar(30)                 not null unique,
    display_name    varchar(50)                 not null unique,
    primary key (user_profile_id)
);
create index IDX7aaakjv4ati399tlj58i62sj3 on game (game_id, board_sizex, board_sizey, player_count);
create index IDX8ct5kv6ektb7rm9jqrj01ddne on ship_location (ship_id, ship_number);
create index IDXcfvjwbji3vjuetj741civphy0 on shot (shot_id, timestamp);
create index IDXqq60d6id4r5fpif66xhe48ibe on user_game (user_game_id, user_id);
alter table if exists ship_location
    add constraint FKpnytx8ylbmhqt7xurkwwpsg2y foreign key (user_game_id) references user_game;
alter table if exists shot
    add constraint FKu769m4ibh9m2qqbey17niyws foreign key (from_user_game_id) references user_game;
alter table if exists shot
    add constraint FK1mef4ttohvr02ixp8pbqypqad foreign key (to_user_game_id) references user_game;
alter table if exists user_game
    add constraint FKg1pwaakahpjiu1io84bnnthys foreign key (game_id) references game;
alter table if exists user_game
    add constraint FKn3mi7ar6wxvb1t8sw6ycfkak6 foreign key (user_id) references user_profile;
create sequence game_seq start with 1 increment by 50;
create sequence ship_location_seq start with 1 increment by 50;
create sequence shot_seq start with 1 increment by 50;
create sequence user_game_seq start with 1 increment by 50;
create sequence user_profile_seq start with 1 increment by 50;
create table game
(
    board_sizex  integer                     not null,
    board_sizey  integer                     not null,
    player_count integer                     not null,
    created      timestamp(6) with time zone not null,
    game_id      bigint                      not null,
    external_key UUID                        not null unique,
    primary key (game_id)
);
create table ship_location
(
    ship_number  integer not null check (ship_number >= 1),
    x            integer,
    y            integer,
    ship_id      bigint  not null,
    user_game_id bigint  not null,
    primary key (ship_id)
);
create table shot
(
    x                 integer,
    y                 integer,
    from_user_game_id bigint                      not null,
    shot_id           bigint                      not null,
    timestamp         timestamp(6) with time zone not null,
    to_user_game_id   bigint                      not null,
    primary key (shot_id)
);
create table user_game
(
    game_id      bigint not null,
    user_game_id bigint not null,
    user_id      bigint not null,
    external_key UUID   not null unique,
    primary key (user_game_id)
);
create table user_profile
(
    created         timestamp(6) with time zone not null,
    modified        timestamp(6) with time zone not null,
    user_profile_id bigint                      not null,
    external_key    UUID                        not null unique,
    oauth_key       varchar(30)                 not null unique,
    display_name    varchar(50)                 not null unique,
    primary key (user_profile_id)
);
create index IDX7aaakjv4ati399tlj58i62sj3 on game (game_id, board_sizex, board_sizey, player_count);
create index IDX8ct5kv6ektb7rm9jqrj01ddne on ship_location (ship_id, ship_number);
create index IDXcfvjwbji3vjuetj741civphy0 on shot (shot_id, timestamp);
create index IDXqq60d6id4r5fpif66xhe48ibe on user_game (user_game_id, user_id);
alter table if exists ship_location
    add constraint FKpnytx8ylbmhqt7xurkwwpsg2y foreign key (user_game_id) references user_game;
alter table if exists shot
    add constraint FKu769m4ibh9m2qqbey17niyws foreign key (from_user_game_id) references user_game;
alter table if exists shot
    add constraint FK1mef4ttohvr02ixp8pbqypqad foreign key (to_user_game_id) references user_game;
alter table if exists user_game
    add constraint FKg1pwaakahpjiu1io84bnnthys foreign key (game_id) references game;
alter table if exists user_game
    add constraint FKn3mi7ar6wxvb1t8sw6ycfkak6 foreign key (user_id) references user_profile;
