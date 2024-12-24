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
