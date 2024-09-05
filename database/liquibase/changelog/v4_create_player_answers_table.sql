--liquibase formatted sql
--changeset SkorikArtur:4 stripComments:true splitStatements:true

create table public.player_answers
(
    id         bigserial
        constraint player_answers_pk
            primary key,
    round_id   bigint                    not null,
    player_id  uuid                      not null,
    answer     varchar(255)              not null,
    created_at timestamptz default now() not null,
    constraint round_players_fk
        foreign key (round_id, player_id)
            references public.round_players (round_id, player_id)
            on update restrict on delete restrict
);

comment
on column public.player_answers.round_id is 'Reference to a round';
comment
on column public.player_answers.player_id is 'Playhub user id';
comment
on column public.player_answers.answer is 'Answer of the round';