--liquibase formatted sql
--changeset SkorikArtur:3 stripComments:true splitStatements:true

create table public.round_participants
(
    round_id       bigint not null
        constraint round_id_fk
            references public.rounds (id)
            on update restrict on delete restrict,
    participant_id uuid       not null,
    constraint round_participants_pk
        primary key (round_id, participant_id)
);

comment
on column public.round_participants.round_id is 'Reference to a round';
comment
on column public.round_participants.participant_id is 'Playhub user id';