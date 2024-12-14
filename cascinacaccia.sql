-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Dic 14, 2024 alle 12:46
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
-- Database: `cascinacaccia`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `employees`
--

CREATE TABLE `employees` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `employee_roles`
--

CREATE TABLE `employee_roles` (
  `employee_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `modulo_appuntamento`
--

CREATE TABLE `modulo_appuntamento` (
  `nome` varchar(255) NOT NULL,
  `cognome` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `messaggio` text NOT NULL,
  `id_slot_data` int(11) NOT NULL,
  `id_categoria` int(11) NOT NULL,
  `id_richiesta_appunt` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `modulo_info`
--

CREATE TABLE `modulo_info` (
  `id_richiestainfo` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `cognome` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `id_categoria` int(11) NOT NULL,
  `messaggio` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(55) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `slot_data`
--

CREATE TABLE `slot_data` (
  `id_slot_data` int(11) NOT NULL,
  `data` date NOT NULL,
  `disponibilita` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `utente_modulo_appunt`
--

CREATE TABLE `utente_modulo_appunt` (
  `id_richiesta_appunt` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `utente_modulo_info`
--

CREATE TABLE `utente_modulo_info` (
  `id_utente` int(11) NOT NULL,
  `id_richiestainfo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `employee_roles`
--
ALTER TABLE `employee_roles`
  ADD KEY `id_ruolo` (`employee_id`,`role_id`),
  ADD KEY `id_utente` (`role_id`);

--
-- Indici per le tabelle `modulo_appuntamento`
--
ALTER TABLE `modulo_appuntamento`
  ADD PRIMARY KEY (`id_richiesta_appunt`),
  ADD KEY `id_slot_data` (`id_slot_data`),
  ADD KEY `id_categoria` (`id_categoria`);

--
-- Indici per le tabelle `modulo_info`
--
ALTER TABLE `modulo_info`
  ADD PRIMARY KEY (`id_richiestainfo`);

--
-- Indici per le tabelle `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `slot_data`
--
ALTER TABLE `slot_data`
  ADD PRIMARY KEY (`id_slot_data`);

--
-- Indici per le tabelle `utente_modulo_appunt`
--
ALTER TABLE `utente_modulo_appunt`
  ADD KEY `id_richiesta_appunt` (`id_richiesta_appunt`),
  ADD KEY `id_utente` (`id_utente`);

--
-- Indici per le tabelle `utente_modulo_info`
--
ALTER TABLE `utente_modulo_info`
  ADD KEY `id_utente` (`id_utente`,`id_richiestainfo`),
  ADD KEY `id_richiestainfo` (`id_richiestainfo`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `employees`
--
ALTER TABLE `employees`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `modulo_appuntamento`
--
ALTER TABLE `modulo_appuntamento`
  MODIFY `id_richiesta_appunt` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `modulo_info`
--
ALTER TABLE `modulo_info`
  MODIFY `id_richiestainfo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `slot_data`
--
ALTER TABLE `slot_data`
  MODIFY `id_slot_data` int(11) NOT NULL AUTO_INCREMENT;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `employee_roles`
--
ALTER TABLE `employee_roles`
  ADD CONSTRAINT `employee_roles_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `employees` (`id`),
  ADD CONSTRAINT `employee_roles_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `roles` (`id`);

--
-- Limiti per la tabella `modulo_appuntamento`
--
ALTER TABLE `modulo_appuntamento`
  ADD CONSTRAINT `modulo_appuntamento_ibfk_1` FOREIGN KEY (`id_slot_data`) REFERENCES `slot_data` (`id_slot_data`),
  ADD CONSTRAINT `modulo_appuntamento_ibfk_2` FOREIGN KEY (`id_categoria`) REFERENCES `categories` (`id`);

--
-- Limiti per la tabella `utente_modulo_appunt`
--
ALTER TABLE `utente_modulo_appunt`
  ADD CONSTRAINT `utente_modulo_appunt_ibfk_1` FOREIGN KEY (`id_utente`) REFERENCES `employees` (`id`),
  ADD CONSTRAINT `utente_modulo_appunt_ibfk_2` FOREIGN KEY (`id_richiesta_appunt`) REFERENCES `modulo_appuntamento` (`id_richiesta_appunt`);

--
-- Limiti per la tabella `utente_modulo_info`
--
ALTER TABLE `utente_modulo_info`
  ADD CONSTRAINT `utente_modulo_info_ibfk_1` FOREIGN KEY (`id_richiestainfo`) REFERENCES `modulo_info` (`id_richiestainfo`),
  ADD CONSTRAINT `utente_modulo_info_ibfk_2` FOREIGN KEY (`id_utente`) REFERENCES `employees` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
