-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Gen 05, 2025 alle 15:52
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
-- Database: `caccia`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `booking_form`
--

CREATE TABLE `booking_form` (
  `id` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `general_form_id` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `generalform_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `category`
--

CREATE TABLE `category` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `category`
--

INSERT INTO `category` (`id`, `name`) VALUES
('1', 'Scuola elementare'),
('2', 'Scuola media'),
('3', 'Scuola superiore'),
('4', 'Università'),
('5', 'Oratorio'),
('6', 'Evento privato generico'),
('7', 'Evento pubblico generico'),
('8', 'Altro');

-- --------------------------------------------------------

--
-- Struttura della tabella `general_form`
--

CREATE TABLE `general_form` (
  `id` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `category_id` varchar(255) NOT NULL,
  `submission_date` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `general_form`
--

INSERT INTO `general_form` (`id`, `email`, `name`, `surname`, `category_id`, `submission_date`) VALUES
('0c038a07-8960-4294-9a33-00e199838423', 'alessandra.pedrotto@edu.itspiemonte.it', 'Riccardo', 'Tarenzi', '1', '2024-12-29 12:15:53.955710'),
('14f0c79b-c3c0-4820-b002-17cf5462919b', 'alessandra.pedrotto@edu.itspiemonte.it', 'ale', 'Pittelli', '2', '2024-12-30 14:48:48.527239'),
('315d6d32-206a-41b7-b0ee-173b7d31da44', 'alessandra.pedrotto@edu.itspiemonte.it', 'Alessandra', 'Pedrotto', '4', NULL),
('39444dbe-bd7c-4221-b2cd-4853d5247963', 'alessandra.pedrotto@edu.itspiemonte.it', 'Alessandra', 'Pedrotto', '1', NULL),
('4ecd7d09-7608-4ee1-b40e-e23743c2b654', 'alessandra.pedrotto@edu.itspiemonte.it', 'Alessandra', 'Pedrotto', '7', NULL),
('59c1df89-df61-48f8-979a-d51ea0b41f0d', 'alessandra.pedrotto@edu.itspiemonte.it', 'Alessandra', 'Pedrotto', '4', '2024-12-28 14:22:12.344034'),
('5c9d640e-bbbe-47cc-8a0c-ea183285626d', 'balmaneayoub3@gmail.com', 'prova', 'prova', '3', '2025-01-05 15:26:08.074883'),
('656c0d3a-9810-4d65-af08-de7cd16a665f', 'alessandra.pedrotto@edu.itspiemonte.it', 'Sara', 'Pittelli', '5', '2024-12-28 14:28:20.776137'),
('80dbd23f-a069-4f31-8e28-7f28d32a1d75', 'alessandra.pedrotto@edu.itspiemonte.it', 'Sara', 'jkn ', '1', '2024-12-30 14:51:57.690358'),
('a59d7083-1c31-436b-805b-0b43b94a0df3', 'alessandra.pedrotto@edu.itspiemonte.it', 'pika', 'Pedrotto', '1', '2024-12-30 15:17:18.242001'),
('b68a14c5-a3ed-4f64-b27b-273e5f8f04ee', 'alessandra.pedrotto@edu.itspiemonte.it', 'Alessandra', 'Pedrotto', '3', '2024-12-30 13:13:26.404573'),
('d60d6d7a-6c3e-44fe-887d-847be98fff8d', 'alessandra.pedrotto@edu.itspiemonte.it', 'pika', 'jkn ', '7', '2024-12-30 14:53:08.256662'),
('dcbf528c-1fca-493f-8945-7a486ad2413e', 'alessandra.pedrotto@edu.itspiemonte.it', 'Alessandra', 'Pedrotto', '1', NULL),
('e175f8bc-a0f4-4b97-a78d-61d5c291c4c3', 'alessandra.pedrotto@edu.itspiemonte.it', 'ale', 'Pieretti', '1', '2024-12-30 18:22:58.790382'),
('e454dc00-bd2a-4856-955e-d5d87a5abb5d', 'alessandra.pedrotto@edu.itspiemonte.it', 'Giorgia', 'Pons', '4', '2024-12-28 14:49:25.439460');

-- --------------------------------------------------------

--
-- Struttura della tabella `information_form`
--

CREATE TABLE `information_form` (
  `id` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `generalform_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `general_form_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `information_form`
--

INSERT INTO `information_form` (`id`, `content`, `generalform_id`, `user_id`, `status`, `general_form_id`) VALUES
('0daee361-0cc1-4e15-a2aa-ccd01f0ae9b8', 'Quali attività si possono fare?', '0c038a07-8960-4294-9a33-00e199838423', '5e0c5595-32e1-40b0-8b1f-4f9ffb2afc87', 'TO DO', NULL),
('1dbc8636-190f-4e7e-953d-502a4c1a5623', 'Siamo dell\'accademia militare, vorremmo portare i nostri ragazzi da voi per imparare di più sull\'argomento mafia, quando costa?', 'e454dc00-bd2a-4856-955e-d5d87a5abb5d', NULL, '', NULL),
('203b8182-b9e0-46c7-898f-518cba8d30bd', 'ciaouuu', 'e175f8bc-a0f4-4b97-a78d-61d5c291c4c3', '5e0c5595-32e1-40b0-8b1f-4f9ffb2afc87', 'IN PROGRESS', NULL),
('25e01fd9-f2a2-4c59-abb2-beb81028eb42', 'prova', '5c9d640e-bbbe-47cc-8a0c-ea183285626d', NULL, 'TO DO', NULL),
('3af7ca9b-0c20-4952-bb81-2e42b1568dbd', 'La cascina dove si trova?', 'b68a14c5-a3ed-4f64-b27b-273e5f8f04ee', NULL, 'TO_DO', NULL),
('3c180bad-827f-4a53-80fa-2e7559b64a4f', 'Per quante notti si può stare in cascina?', 'dcbf528c-1fca-493f-8945-7a486ad2413e', NULL, '', NULL),
('5c19d302-f6bd-4b47-8794-ebad67e7903c', 'ciaouuu', '39444dbe-bd7c-4221-b2cd-4853d5247963', 'dccf76d3-1a94-4076-88d3-e3d447441d30', 'IN PROGRESS', NULL),
('826bc89a-199f-43f0-a761-884314683112', 'Bisogna portare il pranzo al sacco oppure offrite anche la mensa?', '656c0d3a-9810-4d65-af08-de7cd16a665f', NULL, '', NULL),
('c2cac40c-7779-4e3a-a2ff-bb03a55280d5', 'Quanto costa una giornata in cascina per 20 ragazzi e 2 professori?', '315d6d32-206a-41b7-b0ee-173b7d31da44', '4e1161c6-56a8-4041-9902-4fa5417598cd', '', NULL),
('d1adcd70-6447-4193-b53e-6c23f44bc377', 'Siamo un gruppo di studenti  di giurisprudenza e vorremmo fare una visita da noi, è possibile fare foto?', '59c1df89-df61-48f8-979a-d51ea0b41f0d', NULL, '', NULL),
('d37d0a7e-3559-4253-aa8c-7ff62032759b', 'Saraebbe possibile organizzare un\'asta di beneficienza all\'interno della cascina? Gli invitati sarebbero più o meno 50, senza contare lo staff.', 'a59d7083-1c31-436b-805b-0b43b94a0df3', '5e0c5595-32e1-40b0-8b1f-4f9ffb2afc87', 'IN PROGRESS', NULL),
('da420aa9-8095-4c12-8fdc-8f068a84184c', 'Se c\'è mal tempo cosa si può fare?', 'd60d6d7a-6c3e-44fe-887d-847be98fff8d', 'feb91c1a-8558-41eb-9a0a-7660fd7f4d72', 'TO_DO', NULL),
('de281730-4b65-4817-a678-78868c3bcb58', 'Saraebbe possibile organizzare un\'asta di beneficienza all\'interno della cascina? Gli invitati sarebbero più o meno 50, senza contare lo staff.', '4ecd7d09-7608-4ee1-b40e-e23743c2b654', NULL, '', NULL),
('fa341d97-a74b-4b3b-9e7c-545ea440abac', 'La cascina è accessibile per persone disabili?', '39444dbe-bd7c-4221-b2cd-4853d5247963', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'IN PROGRESS', NULL),
('ff0b6377-98f7-4583-b784-587fc3f9ff34', 'Se c\'è mal tempo cosa si può fare?', '315d6d32-206a-41b7-b0ee-173b7d31da44', 'dccf76d3-1a94-4076-88d3-e3d447441d30', 'DONE', NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `password_reset`
--

CREATE TABLE `password_reset` (
  `id` int(11) NOT NULL,
  `expiry_date_time` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `password_reset`
--

INSERT INTO `password_reset` (`id`, `expiry_date_time`, `token`, `user_id`) VALUES
(53, '2024-12-30 15:32:59.917933', '0e157a7d-6f2a-474a-aba2-b5f9dd331003', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14');

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
('1', 'ROLE_EMPLOYEE'),
('2', 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_img_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`id`, `name`, `surname`, `email`, `password`, `user_img_id`) VALUES
('25884b9e-f625-4ad9-b281-bed1936f7833', 'Sara', 'Scaringelli', 'sara.scaringelli.emp@gmail.com', '$2a$10$S3zQgBLQZ/D4UisG0O72NO49B442PSxoHETkM80ywj6AGdhBFLP9i', NULL),
('4e1161c6-56a8-4041-9902-4fa5417598cd', 'Riccardo', 'Tarenzi', 'riccardo.tarenzi.emp@gmail.com', '$2a$10$3Kcww1KSQAR4F49hJPPH3unM4Q81J6KO4JC59vrmO0CUqMXL4Ol46', 26),
('5e0c5595-32e1-40b0-8b1f-4f9ffb2afc87', 'Bianca', 'Deriachi', 'biancha.deriachi.emp@gmail.com', '$2a$10$2Acv0tL7da1VGMtNPnHkp.x4nN2z84.HNGZ0L0lSS1PU2hx0.N4Yu', 25),
('6e2bf5a4-ba2d-4da0-8294-adcf55a6977a', 'Sara', 'Pittelli', 'employee@gmail.com', '$2a$10$wXN/7AoO1wcbySopJS8mS.vQK1eA6Uz9e155vlFgtu6ALm5g1yJ6m', NULL),
('871b28da-5b50-431f-b930-b11dc0308b89', 'Alessia', 'Vigliarolo', 'alessia.vigliarolo.emp@gmail.com', '$2a$10$Dhaqgb2usquV2UYF52fiD.cPUMFLmnu4eoQPYTVSy/T7rKooxe3wW', 3),
('a686b382-eef6-4f15-973d-da77c90793fa', 'Alessia', 'Marcimino', 'alessia.marcimino.emp@gmail.com', '$2a$10$8JnJ8eGQgBwtcGCwb5YlFuRAsvkVrgQ4S.DLNtHcqdIeAbAUDlOv2', 18),
('aa76f92e-bd0a-4647-8678-a409cc2fe9cf', 'Ilaria', 'Pieretti', 'admin@gmail.com', '$2a$10$Ui8SmE4OyhD9ucq/isMzIOXZMxYG7FyAvNjLr58dsCQoXWCcBKt2S', NULL),
('bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'Alessandra', 'Pedrotto', 'alessandra.pedrotto@edu.itspiemonte.it', '$2a$10$vlfyutwyZd/l4EdzIQFiUO2c4uUBQb.J22hpIKfUBjyFqPBCvxeFe', 24),
('dccf76d3-1a94-4076-88d3-e3d447441d30', 'Logan', 'Paul', 'admin.employee@gmail.com', '$2a$10$3B5WxFfLQBl465KwyHj1HOAgEiAdBYgV2yUemN2luLxUwO4MY1BSm', 20),
('f35183ad-9740-46f4-a11b-0b3d8b842154', 'Andrea', 'Daga', 'andrea.daga.emp@gmail.com', '$2a$10$BhjbUHYf10/8HVecdOtLDO3yXo5M7op5dHGrw2mTyxyknSiM8xnpi', NULL),
('f87cc84e-324e-4bc2-b9b1-6bfa58b8bd75', 'Arianna', 'Zavaleta', 'arianna.zavaleta.emp@gmail.com', '$2a$10$Obl2E2.AnX3IOm3BrMfjfuIYaWH7ekjFVAv6Elc0oKD6TeHYKsTNi', 15),
('feb91c1a-8558-41eb-9a0a-7660fd7f4d72', 'Enrico', 'Cotroneo', 'enrico.cotroneo@gmail.com', '$2a$10$6c2J.eXEYH0lsakb/ceMdeEtkKQ1Bk97ZfoNLFYpPH3QJxuYluHri', 12);

-- --------------------------------------------------------

--
-- Struttura della tabella `user_image`
--

CREATE TABLE `user_image` (
  `id` bigint(20) NOT NULL,
  `img_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `user_image`
--

INSERT INTO `user_image` (`id`, `img_path`) VALUES
(1, 'https://cdn-icons-png.freepik.com/256/3890/3890142.png?ga=GA1.1.1838490280.1706974849'),
(2, 'https://cdn-icons-png.freepik.com/256/3045/3045363.png?ga=GA1.1.1838490280.1706974849'),
(3, 'https://cdn-icons-png.freepik.com/256/7642/7642647.png?ga=GA1.1.1838490280.1706974849'),
(4, 'https://cdn-icons-png.freepik.com/256/1396/1396222.png?ga=GA1.1.1838490280.1706974849'),
(5, 'https://cdn-icons-png.freepik.com/256/5121/5121004.png?ga=GA1.1.1838490280.1706974849'),
(6, 'https://cdn-icons-png.freepik.com/256/7642/7642660.png?ga=GA1.1.1838490280.1706974849'),
(7, 'https://cdn-icons-png.freepik.com/256/7028/7028924.png?ga=GA1.1.1838490280.1706974849'),
(8, 'https://cdn-icons-png.freepik.com/256/6928/6928001.png?ga=GA1.1.1838490280.1706974849'),
(9, 'https://cdn-icons-png.freepik.com/256/7752/7752966.png?ga=GA1.1.1838490280.1706974849'),
(10, 'https://cdn-icons-png.freepik.com/256/6028/6028353.png?ga=GA1.1.1838490280.1706974849'),
(11, 'https://cdn-icons-png.freepik.com/256/4325/4325464.png?ga=GA1.1.1838490280.1706974849'),
(12, 'https://cdn-icons-png.freepik.com/256/3985/3985214.png?ga=GA1.1.1838490280.1706974849&semt=ais_hybrid'),
(13, 'https://cdn-icons-png.freepik.com/256/2967/2967475.png?ga=GA1.1.1838490280.1706974849&semt=ais_hybrid'),
(14, 'https://cdn-icons-png.freepik.com/256/2967/2967514.png?ga=GA1.1.1838490280.1706974849'),
(15, 'https://cdn-icons-png.freepik.com/256/1465/1465410.png?ga=GA1.1.1838490280.1706974849'),
(16, 'https://cdn-icons-png.freepik.com/256/2795/2795648.png?ga=GA1.1.1838490280.1706974849'),
(17, 'https://cdn-icons-png.freepik.com/256/5294/5294806.png?ga=GA1.1.1838490280.1706974849'),
(18, 'https://cdn-icons-png.freepik.com/256/6929/6929098.png?ga=GA1.1.1838490280.1706974849'),
(19, 'https://cdn-icons-png.freepik.com/256/1396/1396224.png?ga=GA1.1.1838490280.1706974849'),
(20, 'https://cdn-icons-png.freepik.com/256/4078/4078480.png?ga=GA1.1.1838490280.1706974849'),
(21, 'https://cdn-icons-png.freepik.com/256/4079/4079619.png?ga=GA1.1.1838490280.1706974849'),
(22, 'https://cdn-icons-png.freepik.com/256/4694/4694447.png?ga=GA1.1.1838490280.1706974849'),
(23, 'https://cdn-icons-png.freepik.com/256/2640/2640788.png?ga=GA1.1.1838490280.1706974849'),
(24, 'https://cdn-icons-png.freepik.com/256/3597/3597742.png?ga=GA1.1.1838490280.1706974849'),
(25, 'https://cdn-icons-png.freepik.com/256/3605/3605703.png?ga=GA1.1.1838490280.1706974849'),
(26, 'https://cdn-icons-png.freepik.com/256/7417/7417530.png?ga=GA1.1.1838490280.1706974849'),
(27, 'https://cdn-icons-png.freepik.com/256/947/947668.png?ga=GA1.1.1838490280.1706974849');

-- --------------------------------------------------------

--
-- Struttura della tabella `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
('6e2bf5a4-ba2d-4da0-8294-adcf55a6977a', '1'),
('aa76f92e-bd0a-4647-8678-a409cc2fe9cf', '2'),
('871b28da-5b50-431f-b930-b11dc0308b89', '1'),
('f35183ad-9740-46f4-a11b-0b3d8b842154', '1'),
('dccf76d3-1a94-4076-88d3-e3d447441d30', '2'),
('dccf76d3-1a94-4076-88d3-e3d447441d30', '1'),
('25884b9e-f625-4ad9-b281-bed1936f7833', '1'),
('a686b382-eef6-4f15-973d-da77c90793fa', '1'),
('5e0c5595-32e1-40b0-8b1f-4f9ffb2afc87', '1'),
('4e1161c6-56a8-4041-9902-4fa5417598cd', '1'),
('f87cc84e-324e-4bc2-b9b1-6bfa58b8bd75', '1'),
('bad3ad59-c90d-42a3-a12b-602ccfa2dc14', '1'),
('feb91c1a-8558-41eb-9a0a-7660fd7f4d72', '1');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `booking_form`
--
ALTER TABLE `booking_form`
  ADD PRIMARY KEY (`id`),
  ADD KEY `general_form_id` (`general_form_id`),
  ADD KEY `FKpxfjm648qs5befywlmht3h4tq` (`generalform_id`);

--
-- Indici per le tabelle `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `general_form`
--
ALTER TABLE `general_form`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKexviuaoaiiafyxvsoce0rs0n7` (`category_id`);

--
-- Indici per le tabelle `information_form`
--
ALTER TABLE `information_form`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK737wsxxu99pnpex9q84gq9xm6` (`generalform_id`),
  ADD KEY `FKk9a8x0t26tvpkou727e2go2cm` (`general_form_id`),
  ADD KEY `assiged_to` (`user_id`);

--
-- Indici per le tabelle `password_reset`
--
ALTER TABLE `password_reset`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKa8wdbuprq1bigxs2mkb1ag367` (`user_id`);

--
-- Indici per le tabelle `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKcqsxvibh5kghaelidkv0eb554` (`user_img_id`);

--
-- Indici per le tabelle `user_image`
--
ALTER TABLE `user_image`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `user_roles`
--
ALTER TABLE `user_roles`
  ADD KEY `FKrhfovtciq1l558cw6udg0h0d3` (`role_id`),
  ADD KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `password_reset`
--
ALTER TABLE `password_reset`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT per la tabella `user_image`
--
ALTER TABLE `user_image`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `booking_form`
--
ALTER TABLE `booking_form`
  ADD CONSTRAINT `FKpxfjm648qs5befywlmht3h4tq` FOREIGN KEY (`generalform_id`) REFERENCES `general_form` (`id`);

--
-- Limiti per la tabella `general_form`
--
ALTER TABLE `general_form`
  ADD CONSTRAINT `FKexviuaoaiiafyxvsoce0rs0n7` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

--
-- Limiti per la tabella `information_form`
--
ALTER TABLE `information_form`
  ADD CONSTRAINT `FK737wsxxu99pnpex9q84gq9xm6` FOREIGN KEY (`generalform_id`) REFERENCES `general_form` (`id`),
  ADD CONSTRAINT `FKk9a8x0t26tvpkou727e2go2cm` FOREIGN KEY (`general_form_id`) REFERENCES `general_form` (`id`),
  ADD CONSTRAINT `assiged_to` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `password_reset`
--
ALTER TABLE `password_reset`
  ADD CONSTRAINT `FK3rcc5avyc21uriav34cjrqc91` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FKcqsxvibh5kghaelidkv0eb554` FOREIGN KEY (`user_img_id`) REFERENCES `user_image` (`id`);

--
-- Limiti per la tabella `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
