--liquibase formatted sql
--changeset SkorikArtur:1 stripComments:true splitStatements:true

create table public.games
(
    id              uuid        default gen_random_uuid()
        constraint games_pk
            primary key,
    owner_id        uuid                      not null,
    room_id         uuid                      not null,
    locale          varchar(5)                not null,
    grid_size       integer                   not null,
    rounds_duration bigint                    not null,
    state           varchar(10)               not null,
    created_at      timestamptz default now() not null,
    started_at      timestamptz,
    finished_at     timestamptz
);

comment
on column public.games.owner_id is 'Playhub user id who created the game';
comment
on column public.games.room_id is 'Room id associated with the game';
comment
on column public.games.locale is 'Language of the game (xx-YY format)';
comment
on column public.games.grid_size is 'Grid size of boggle board';
comment
on column public.games.rounds_duration is 'Durations of rounds in the game';
comment
on column public.games.state is 'State of the game: WAIT, ACTIVE, FINISHED';
comment
on column public.games.created_at is 'Timestamp of creation of the game';
comment
on column public.games.started_at is 'Timestamp of start of the game';
comment
on column public.games.finished_at is 'Timestamp of end of the game';
