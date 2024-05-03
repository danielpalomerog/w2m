INSERT INTO "starship" (id, name, movie) VALUES (1,'x-wing', 'star wars'), (2,'uss enterprise', 'star trek');

SELECT SETVAL('starship_id_seq', (SELECT MAX(id) FROM "starship"));