-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Dic 16, 2024 alle 09:27
-- Versione del server: 10.4.28-MariaDB
-- Versione PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cascina_caccia`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `category`
--

CREATE TABLE `category` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `category`
--

INSERT INTO `category` (`id`, `name`) VALUES
('1', 'evento_pubblico'),
('2', 'evento_privato'),
('3', 'scuola_elementare'),
('4', 'scuola_media'),
('5', 'scuola_superiore'),
('6', 'universita'),
('7', 'altro');

-- --------------------------------------------------------

--
-- Struttura della tabella `employee`
--

CREATE TABLE `employee` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `employee_general_form`
--

CREATE TABLE `employee_general_form` (
  `employee_id` varchar(255) NOT NULL,
  `general_form_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `employee_role`
--

CREATE TABLE `employee_role` (
  `employee_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `general_form`
--

CREATE TABLE `general_form` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `suname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `category_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `information_form`
--

CREATE TABLE `information_form` (
  `id` varchar(255) NOT NULL,
  `general_form_id` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `prenotation_form`
--

CREATE TABLE `prenotation_form` (
  `id` varchar(255) NOT NULL,
  `general_form_id` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `message` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `role`
--

CREATE TABLE `role` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
('1', 'role_admin'),
('2', 'role_employee');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `employee_general_form`
--
ALTER TABLE `employee_general_form`
  ADD KEY `FK_employee` (`employee_id`),
  ADD KEY `FK_general_form_e` (`general_form_id`);

--
-- Indici per le tabelle `employee_role`
--
ALTER TABLE `employee_role`
  ADD KEY `FK_employee_id` (`employee_id`),
  ADD KEY `FK_role_id` (`role_id`);

--
-- Indici per le tabelle `general_form`
--
ALTER TABLE `general_form`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_category` (`category_id`);

--
-- Indici per le tabelle `information_form`
--
ALTER TABLE `information_form`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_general_form` (`general_form_id`);

--
-- Indici per le tabelle `prenotation_form`
--
ALTER TABLE `prenotation_form`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_general_form_prenotation` (`general_form_id`);

--
-- Indici per le tabelle `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `employee_general_form`
--
ALTER TABLE `employee_general_form`
  ADD CONSTRAINT `FK_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_general_form_e` FOREIGN KEY (`general_form_id`) REFERENCES `general_form` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `employee_role`
--
ALTER TABLE `employee_role`
  ADD CONSTRAINT `FK_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `general_form`
--
ALTER TABLE `general_form`
  ADD CONSTRAINT `FK_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

--
-- Limiti per la tabella `information_form`
--
ALTER TABLE `information_form`
  ADD CONSTRAINT `FK_general_form` FOREIGN KEY (`general_form_id`) REFERENCES `general_form` (`id`) ON DELETE CASCADE;

--
-- Limiti per la tabella `prenotation_form`
--
ALTER TABLE `prenotation_form`
  ADD CONSTRAINT `FK_general_form_prenotation` FOREIGN KEY (`general_form_id`) REFERENCES `general_form` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
