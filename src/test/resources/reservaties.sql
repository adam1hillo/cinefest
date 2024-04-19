insert into reservaties(filmId, emailAdres, plaatsen, besteld) values
((select id from films where titel = 'test1'), 'test@example.org', 1, now()),
((select id from films where titel = 'test1'), 'test@example.org', 2, now());