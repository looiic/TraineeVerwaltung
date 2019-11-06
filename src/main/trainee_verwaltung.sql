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
  `standort` varchar(50) NOT NULL,
  `vorkenntnisse` int(11) DEFAULT NULL,
  `kurs_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `person`
--

INSERT INTO `person` (`id`, `vorname`, `nachname`, `standort`, `vorkenntnisse`, `kurs_id`) VALUES
(1, 'Oliver', 'Wack', 'MANNHEIM', 10, 1),
(2, 'Nadine', 'Burkert', 'ZURICH', 10, 1),
(3, 'Pradeep', 'Kadavil', 'ZURICH', 10, 1),
(4, 'Loïc', 'Jermann', 'BASEL', 10, 1),
(5, 'Andrada', 'Serban', 'MANNHEIM', 10, 2);

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
  ADD KEY `fk_Person_Kurs` (`kurs_id`);

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
  ADD CONSTRAINT `fk_Person_Kurs` FOREIGN KEY (`kurs_id`) REFERENCES `kurs` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
