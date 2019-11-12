-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 06. Nov 2019 um 16:47
-- Server-Version: 10.4.8-MariaDB
-- PHP-Version: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


drop database if exists trainee_verwaltung;
create DATABASE trainee_verwaltung;

use trainee_verwaltung;

--
-- Datenbank: `trainee_verwaltung`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `kurs`
--

CREATE TABLE `kurs` (
  `id` int(11) NOT NULL,
  `jahrgang` varchar(50) NOT NULL,
  `raum` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `kurs`
--

INSERT INTO `kurs` (`id`, `jahrgang`, `raum`) VALUES
(1, '2019-2', 'EG 1'),
(2, '2019-1', 'EG 1');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `person`
--

CREATE TABLE `person` (
  `id` int(11) NOT NULL,
  `vorname` varchar(50) NOT NULL,
  `nachname` varchar(50) NOT NULL,
  `fk_standort` varchar(50) NOT NULL,
  `vorkenntnisse` varchar(50) NOT NULL,
  `fk_kurs_id` int(11)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `person`
--

INSERT INTO `person` (`id`, `vorname`, `nachname`, `fk_standort`, `vorkenntnisse`, `fk_kurs_id`) VALUES
(1, 'Hansi', 'Hinterseer', 'Istanbul', 'Guru', 1),
(2, 'DJ', 'Ötzi', 'Istanbul', 'Guru', 1),
(3, 'Helene', 'Fischer', 'Istanbul', 'Guru', 1),
(4, 'Andreas', 'Gabalier', 'Istanbul', 'Guru', 1),
(5, 'Beatrice', 'Egli', 'Istanbul', 'Guru', 2);


CREATE TABLE `standort` (
    `standort` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `standort` ADD PRIMARY KEY (`standort`);

INSERT INTO `standort` (`standort`) VALUES
('Istanbul'),
('New York'),
('Berlin'),
('Mumbai'),
('Teheran'),
('Tel Aviv'),
('Moskau'),
('Rom'),
('Bari'),
('Edinbourgh'),
('Liverpool'),
('London'),
('Nizza'),
('Stockholm'),
('Tschappina'),
('Marrakech');


--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `kurs`
--
ALTER TABLE `kurs`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_Person_Kurs` (`fk_kurs_id`),
  ADD KEY `fk_Person_Standort` (`fk_standort`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `kurs`
--
ALTER TABLE `kurs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT für Tabelle `person`
--
ALTER TABLE `person`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `person`
--
ALTER TABLE `person`
  ADD CONSTRAINT `fk_Person_Kurs` FOREIGN KEY (`fk_kurs_id`) REFERENCES `kurs` (`id`);

ALTER TABLE `person`
  ADD CONSTRAINT `fk_Person_Standort` FOREIGN KEY (`fk_standort`) REFERENCES `standort` (`standort`);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
