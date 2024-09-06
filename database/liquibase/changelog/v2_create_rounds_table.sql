--liquibase formatted sql
--changeset SkorikArtur:2 stripComments:true splitStatements:true

create table public.rounds
(
    id          bigserial
        constraint rounds_pk
            primary key,
    game_id     uuid                      not null
        constraint games_id_fk
            references public.games (id)
            on update restrict on delete restrict,
    number      integer                   not null,
    board       text[][]                  not null,
    state       varchar(10)               not null,
    created_at  timestamptz default now() not null,
    started_at  timestamptz,
    finished_at timestamptz
);

create unique index rounds_game_id_number_uindex
    on public.rounds (game_id, number);

comment
    on column public.rounds.game_id is 'Reference to a game';
comment
    on column public.rounds.board is 'Boggle chars matrix';
comment
    on column public.rounds.state is 'State of the game: WAIT, ACTIVE, FINISHED';
comment
    on column public.rounds.created_at is 'Timestamp of creation of the round';
comment
    on column public.rounds.started_at is 'Timestamp of start of the round';
comment
    on column public.rounds.finished_at is 'Timestamp of end of the round';