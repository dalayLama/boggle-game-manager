--liquibase formatted sql
--changeset SkorikArtur:4 stripComments:true splitStatements:true

create table public.participant_answers
(
    id             bigserial
        constraint participant_answers_pk
            primary key,
    round_id       bigint                    not null,
    participant_id uuid                      not null,
    answer         varchar(255)              not null,
    created_at     timestamptz default now() not null,
    constraint round_participant_fk
        foreign key (round_id, participant_id)
            references public.round_participants (round_id, participant_id)
            on update restrict on delete restrict
);

comment
    on column public.participant_answers.participant_id is 'Playhub user id';
comment
    on column public.participant_answers.round_id is 'Reference to a round';
comment
    on column public.participant_answers.answer is 'Answer of the round';