DROP SCHEMA IF EXISTS webprojekat;
CREATE SCHEMA webprojekat DEFAULT CHARACTER SET utf8mb4;
USE webprojekat;

CREATE TABLE lokacije (
	id BIGINT AUTO_INCREMENT,
    Grad VARCHAR(100) NOT NULL,
    Drzava VARCHAR(100) NOT NULL,
    Kontinent ENUM("Evropa", "Amerika", "Azija", "Australija", "Afrika", "Antartika", "Okeanija"),
    putanjaDoSlike VARCHAR(1000) NOT NULL,
    PRIMARY KEY(id)
);

/*drop table if exists lokacije;*/
INSERT INTO lokacije (Grad, Drzava, Kontinent, putanjaDoSlike) VALUES
('Beograd', 'Srbija', 'Evropa', 'plaza.webp'),
('New York', 'Sjedinjene Američke Države', 'Amerika', 'plaza.webp'),
('Tokio', 'Japan', 'Azija', 'plaza.webp'),
('Sidnej', 'Australija', 'Australija', 'plaza.webp'),
('Kairo', 'Egipat', 'Afrika', 'plaza.webp'),
('Buenos Aires', 'Argentina', 'Amerika', 'plaza.webp'),
('Pariz', 'Francuska', 'Evropa', 'plaza.webp'),
('Kejp Taun', 'Južnoafrička Republika', 'Afrika', 'plaza.webp'),
('Auckland', 'Novi Zeland', 'Okeanija', 'plaza.webp'),
('McMurdo Station', 'Antarktik', 'Antartika', 'plaza.webp'),
('Washington', 'Sjedinjene Američke Države', 'Amerika', 'plaza.webp');

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
    blokiran BOOLEAN,
    loyaltyBodovi INT,
    zahtevaLoyalty BOOLEAN,
    paraPotroseno INT,
    listaZelja VARCHAR(85),
    PRIMARY KEY(id)
);

INSERT INTO korisnici (korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, datumIVremeRegistracije, blokiran, uloga, loyaltyBodovi, zahtevaLoyalty,
paraPotroseno, listaZelja) VALUES
('pera', 'pera123', 'petar.petrovic@gmail.com', 'Petar', 'Petrović', '1976-06-24', '2023-11-05 01:32:48', false, 'ADMIN', 5, false, 0, ''),
('marko', 'marko123', 'marko.petrovic@example.com', 'Marko', 'Petrović', '1990-03-15', '2024-12-21 17:34:22', false, 'PUTNIK', 6, false, 0, ''),
('nikola', 'nikola123', 'nikola.tesla@example.com', 'Nikola', 'Tesla', '1976-01-07', '2024-12-21 23:45:30', false, 'PUTNIK', -1, false, 0, ''),
('ana.j', 'securepass', 'ana.jovanovic@example.com', 'Ana', 'Jovanović', '1985-07-10', '2024-12-21 18:45:30', false, 'PUTNIK', -1, false, 0, ''),
('ivan.n', 'mypassword', 'ivan.nikolic@example.com', 'Ivan', 'Nikolić', '2000-01-25', '2024-12-21 19:12:10', false, 'PUTNIK', -1, false, 0, ''),
('milica.s', 'qwerty123', 'milica.stankovic@example.com', 'Milica', 'Stanković', '1995-11-05', '2024-12-21 20:05:50', false, 'PUTNIK', -1, false, 0, ''),
('stefan.p', 'abc12345', 'stefan.popovic@example.com', 'Stefan', 'Popović', '1988-09-12', '2024-12-21 21:30:15', false, 'PUTNIK', -1, false, 0, ''),
('jelena.k', 'jelena2024', 'jelena.kovac@example.com', 'Jelena', 'Kovač', '1993-06-18', '2024-12-21 22:10:05', false, 'PUTNIK', -1, false, 0, ''),
('dragana.b', 'dragon789', 'dragana.bogdanovic@example.com', 'Dragana', 'Bogdanović', '1992-04-22', '2024-12-22 00:15:40', true, 'PUTNIK', -1, false, 0, ''),
('aleksandar.v', 'alex123', 'aleksandar.vukovsic@example.com', 'Aleksandar', 'Vuković', '1998-11-30', '2024-12-22 01:30:00', true, 'PUTNIK', -1, false, 0, ''),
('milos', 'milos123', 'aleksandar.vukovsic@example.com', 'Aleksandar', 'Vuković', '1998-11-30', '2024-12-22 01:30:00', true, 'PUTNIK', -1, false, 0, ''),
('katarina.m', 'katy2024', 'katarina.milosevic@example.com', 'Katarina', 'Milošević', '1994-08-15', '2024-12-22 02:20:50', false, 'PUTNIK', -1, false, 0, '');

SELECT * FROM korisnici k
ORDER BY k.id;

CREATE TABLE aerodromi (
	id BIGINT AUTO_INCREMENT,
	oznaka VARCHAR(3) NOT NULL,
    lokacijaId BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY (lokacijaId) REFERENCES lokacije(id)
);

INSERT INTO aerodromi (oznaka, lokacijaId) VALUES
('BEG', 1),  -- Beograd
('JFK', 2),  -- New York
('FKN', 2),  -- New York
('HND', 3),  -- Tokio
('SYD', 4),  -- Sidnej
('CAI', 5),  -- Kairo
('EZE', 6),  -- Buenos Aires
('CDG', 7),  -- Pariz
('CPT', 8),  -- Kejp Taun
('AKL', 9),  -- Auckland
('MCM', 10), -- McMurdo Station (fictional IATA code)
('WSG', 11); -- Washinton

SELECT * FROM aerodromi a
ORDER BY a.id;

CREATE TABLE avioni (
	id BIGINT AUTO_INCREMENT,
    naziv VARCHAR(100) NOT NULL,
    brojKolona int,
    brojRedova int,
    PRIMARY KEY(id)
);

INSERT INTO avioni(naziv, brojKolona, brojRedova) VALUES
('Boeing 747', 4, 8),
('Airbus A330', 5, 10),
('Cessna C52', 2, 5);

SELECT * FROM avioni av
ORDER BY av.id;

CREATE TABLE letovi (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    oznaka VARCHAR(100) NOT NULL,
    polazisteId BIGINT NOT NULL,
    odredisteId BIGINT NOT NULL,
    avionId BIGINT NOT NULL,
    terminPolaska DATETIME NOT NULL,
    trajanjeLeta INT NOT NULL,
    cena INT NOT NULL,
    naAkciji BOOLEAN NOT NULL,
    brojMesta INT NOT NULL,
    razlogOtkaza VARCHAR(100) NOT NULL,
    datumVazenjaAkcije DATE NOT NULL,
    staraCena INT NOT NULL,

    FOREIGN KEY (polazisteId) REFERENCES aerodromi(id),
    FOREIGN KEY (odredisteId) REFERENCES aerodromi(id),
    FOREIGN KEY (avionId) REFERENCES avioni(id)
);

INSERT INTO letovi (oznaka, polazisteId, odredisteId, avionId, terminPolaska, trajanjeLeta, cena, naAkciji, brojMesta ,razlogOtkaza, 
datumVazenjaAkcije, staraCena) VALUES
('FL001', 1, 2, 1, '2025-07-15 08:30:00', 600, 40000, TRUE, 31, '', '2035-11-29', 80000),
('FL002', 2, 3, 2, '2025-07-16 12:00:00', 840, 55000, TRUE, 50, '', '2035-11-29', 80000),
('FL003', 3, 4, 3, '2025-07-17 05:45:00', 540, 38000, TRUE, 10, '', '2035-11-29', 80000),
('FL004', 4, 5, 1, '2025-07-18 19:20:00', 420, 30000, FALSE, 32, '', '1970-01-01', 30000),
('FL005', 5, 6, 2, '2025-07-19 07:10:00', 720, 47000, TRUE, 50, '', '2035-11-29', 80000),
('FL006', 8, 2, 1, '2025-07-15 08:30:00', 600, 40000, FALSE, 32, '', '1970-01-01', 40000),
('FL007', 9, 3, 2, '2025-07-16 12:00:00', 840, 55000, FALSE, 50, '', '1970-01-01', 55000),
('FL008', 10, 4, 3, '2025-07-17 05:45:00', 540, 38000, FALSE, 10, '', '1970-01-01', 38000),
('FL009', 2, 4, 1, '2025-07-18 19:20:00', 420, 30000, FALSE, 32, '', '1970-01-01', 30000),
('FL010', 1, 9, 2, '2025-07-19 07:10:00', 720, 47000, FALSE, 50, '', '1970-01-01', 47000),
('FL011', 1, 2, 1, '2025-07-16 08:30:00', 600, 40000, FALSE, 32, '', '1970-01-01', 40000),
('FL012', 1, 12, 1, '2025-07-16 08:30:00', 600, 40000, FALSE, 32, '', '1970-01-01', 40000),
('FLT', 4, 9, 2, '2027-04-26 13:15:00', 555, 5000, FALSE, 50, '', '1970-01-01', 5000);

SELECT * FROM letovi lt
ORDER BY lt.id;

CREATE TABLE karte (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    letId BIGINT,
    brojSedista VARCHAR(10) NOT NULL, -- Trebalo bi biti unikatno za celu bazu, pa try/catch u kodu ako dvojica iz razlicitih sesija pokusaju da rezervisu isto mesto
    cena INT NOT NULL,
    imeIPrezimePutnika VARCHAR(100) NOT NULL,
    brojPasosa VARCHAR(50) NOT NULL,
    FOREIGN KEY (letId) REFERENCES letovi(id)
);

INSERT INTO karte (letId, brojSedista, cena, imeIPrezimePutnika, brojPasosa) VALUES
(1, '2-5', 40000, 'Petar Petrovic', 'SRB123456');
-- (1, '2-5', 15000, 'Petar Petrovic', 'SRB123456'),
-- (2, '1-3', 200, 'Jovana Jovic', 'SRB654321'),
-- (1, '4-7', 15000, 'Nikola Nikolic', 'SRB112233');

SELECT * FROM karte k
ORDER BY k.id;

CREATE TABLE rezervacije (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    idKorisnika BIGINT NOT NULL,
    datumIVremeKreiranja TIMESTAMP default current_timestamp NOT NULL,
    ukupnaCena INT NOT NULL,
    FOREIGN KEY (idKorisnika) REFERENCES korisnici(id)
);

INSERT INTO rezervacije (idKorisnika, ukupnaCena) VALUES
(1, 40000);

SELECT * FROM rezervacije r
ORDER BY r.id;

CREATE TABLE rezervacija_karta (
    rezervacijaId BIGINT,
    kartaId BIGINT,
    PRIMARY KEY (rezervacijaId, kartaId),
    FOREIGN KEY (rezervacijaId) REFERENCES rezervacije(id),
    FOREIGN KEY (kartaId) REFERENCES karte(id)
);

INSERT INTO rezervacija_karta (rezervacijaId, kartaId) VALUES
(1, 1);

SELECT *
FROM rezervacije r
LEFT JOIN rezervacija_karta rk ON rk.rezervacijaId = r.id
LEFT JOIN karte k ON rk.kartaId = k.id
ORDER BY k.id;





