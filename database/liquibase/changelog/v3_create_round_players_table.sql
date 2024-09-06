--liquibase formatted sql
--changeset SkorikArtur:3 stripComments:true splitStatements:true

create table public.round_players
(
    round_id  bigint not null
        constraint round_id_fk
            references public.rounds (id)
            on update restrict on delete restrict,
    player_id uuid   not null,
    constraint round_players_pk
        primary key (round_id, player_id)
);

comment
on column public.round_players.round_id is 'Reference to a round';
comment
on column public.round_players.player_id is 'Playhub user id';