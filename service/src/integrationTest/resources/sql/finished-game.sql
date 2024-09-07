insert into games (id, owner_id, room_id, locale, grid_size, rounds_duration, state, created_at, started_at,
                   finished_at)
values ('8e5a5e37-6c7e-4cc3-a010-7458db3e80bf', '590f1642-1959-4185-b602-f6b3fcdf1fd5',
        'f1162b93-1034-4d7b-a037-e4c282dc354a', 'en-EN', 4, 60000000000, 'FINISHED', now(), now(), now());

insert into rounds (id, game_id, number, board, state, created_at, started_at, finished_at)
VALUES (1, '8e5a5e37-6c7e-4cc3-a010-7458db3e80bf', 1, '{{A, B}, {C, V}, {A, B}, {C, V}}', 'FINISHED', now(), now(), now());

insert into rounds (id, game_id, number, board, state, created_at, started_at, finished_at)
VALUES (2, '8e5a5e37-6c7e-4cc3-a010-7458db3e80bf', 2, '{{K, N}, {Z, K}, {A, B}, {C, V}}', 'WAITING', now(), now(), now());

insert into round_players (round_id, player_id)
VALUES (1, '590f1642-1959-4185-b602-f6b3fcdf1fd5'),
       (1, '0d57d4cd-8218-4ac8-950a-1d876d0f0293');
insert into round_players (round_id, player_id)
VALUES (2, '590f1642-1959-4185-b602-f6b3fcdf1fd5'),
       (2, '0d57d4cd-8218-4ac8-950a-1d876d0f0293');