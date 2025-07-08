DROP SCHEMA IF EXISTS webprojekat;
CREATE SCHEMA webprojekat DEFAULT CHARACTER SET utf8mb4;
USE webprojekat;

CREATE TABLE lokacije (
	id BIGINT AUTO_INCREMENT,
    Grad VARCHAR(100) NOT NULL,
    Drzava VARCHAR(100) NOT NULL,
    Kontinent ENUM("Evropa", "Amerika", "Azija", "Australija", "Afrika", "Antartika", "Okeanija"),
    PRIMARY KEY(id)
);

/*drop table if exists lokacije;*/
INSERT INTO lokacije (Grad, Drzava, Kontinent) VALUES
('Beograd', 'Srbija', 'Evropa'),
('New York', 'Sjedinjene Američke Države', 'Amerika'),
('Tokio', 'Japan', 'Azija'),
('Sidnej', 'Australija', 'Australija'),
('Kairo', 'Egipat', 'Afrika'),
('Buenos Aires', 'Argentina', 'Amerika'),
('Pariz', 'Francuska', 'Evropa'),
('Kejp Taun', 'Južnoafrička Republika', 'Afrika'),
('Auckland', 'Novi Zeland', 'Okeanija'),
('McMurdo Station', 'Antarktik', 'Antartika');

SELECT * FROM lokacije l
ORDER BY l.id;

/*drop table if exists korisnici;*/
CREATE TABLE korisnici (
	id BIGINT AUTO_INCREMENT,
    korisnickoIme VARCHAR(20) NOT NULL,
    lozinka varchar(20) NOT NULL,
    email varchar(50) not null,
    ime varchar(20) not null,
    prezime varchar(20) not null,
    datumRodjenja date not null,
    datumIVremeRegistracije timestamp default current_timestamp not null,
    uloga ENUM("PUTNIK", "ADMIN"),
    blokiran boolean,
    PRIMARY KEY(id)
);

INSERT INTO korisnici (korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, datumIVremeRegistracije, blokiran, uloga) VALUES
('peraZdera', 'pera123', 'petar.petrovic@gmail.com', 'Petar', 'Petrović', '1976-06-24', '2023-11-05 01:32:48', false, 'ADMIN'),
('marko123', 'pass123', 'marko.petrovic@example.com', 'Marko', 'Petrović', '1990-03-15', '2024-12-21 17:34:22', false, 'PUTNIK'),
('ana.j', 'securepass', 'ana.jovanovic@example.com', 'Ana', 'Jovanović', '1985-07-10', '2024-12-21 18:45:30', false, 'PUTNIK'),
('ivan.n', 'mypassword', 'ivan.nikolic@example.com', 'Ivan', 'Nikolić', '2000-01-25', '2024-12-21 19:12:10', false, 'PUTNIK'),
('milica.s', 'qwerty123', 'milica.stankovic@example.com', 'Milica', 'Stanković', '1995-11-05', '2024-12-21 20:05:50', false, 'PUTNIK'),
('stefan.p', 'abc12345', 'stefan.popovic@example.com', 'Stefan', 'Popović', '1988-09-12', '2024-12-21 21:30:15', false, 'PUTNIK'),
('jelena.k', 'jelena2024', 'jelena.kovac@example.com', 'Jelena', 'Kovač', '1993-06-18', '2024-12-21 22:10:05', false, 'PUTNIK'),
('nikola.t', 'tesla987', 'nikola.tesla@example.com', 'Nikola', 'Tesla', '1976-01-07', '2024-12-21 23:45:30', false, 'PUTNIK'),
('dragana.b', 'dragon789', 'dragana.bogdanovic@example.com', 'Dragana', 'Bogdanović', '1992-04-22', '2024-12-22 00:15:40', true, 'PUTNIK'),
('aleksandar.v', 'alex123', 'aleksandar.vukovsic@example.com', 'Aleksandar', 'Vuković', '1998-11-30', '2024-12-22 01:30:00', true, 'PUTNIK'),
('katarina.m', 'katy2024', 'katarina.milosevic@example.com', 'Katarina', 'Milošević', '1994-08-15', '2024-12-22 02:20:50', false, 'PUTNIK');

SELECT * FROM korisnici k
ORDER BY k.id;

