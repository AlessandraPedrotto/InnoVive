-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Gen 07, 2025 alle 15:40
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
  `check_in` date DEFAULT NULL,
  `check_out` date DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `generalform_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `booking_form`
--

INSERT INTO `booking_form` (`id`, `check_in`, `check_out`, `content`, `status`, `user_id`, `generalform_id`) VALUES
('10a60b32-fb71-4768-934d-ddf56fd4d62e', '2025-02-07', '2025-02-07', 'F. Asta di beneficenza, 50-60 persone con lo staff', 'TO DO', 'dccf76d3-1a94-4076-88d3-e3d447441d30', '0564d9aa-9949-4dcd-b016-070b76795c22'),
('5bd185b2-0111-4989-9bfb-de51e67f0aca', '2025-01-10', '2025-01-12', 'A. 4 adulti e 20 bambini', 'TO DO', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', '2595b09b-9c9c-498a-bef8-2e9fa4ca4231'),
('62d881dc-c346-45be-b974-10fbdc4fcd7f', '2025-01-22', '2025-01-22', 'D. 10 ragazzi', 'TO DO', '2c5bce10-aa81-4cdb-884b-d608e3a98074', 'b0541118-39d6-4eed-914d-2f7883b1f219'),
('8d01c86d-fb3c-4679-8e37-53bac561e564', '2025-01-08', '2025-01-08', 'E. 4 accompagnatori e 15 ragazzi', 'TO DO', 'dccf76d3-1a94-4076-88d3-e3d447441d30', '3619e90f-3050-402b-86a7-1c61796e003a'),
('d2870110-8c4e-40ef-b9be-85f1fe6af733', '2025-01-31', '2025-02-01', 'C. 2 classi', 'TO DO', '2c5bce10-aa81-4cdb-884b-d608e3a98074', '1e6b085e-5ad0-4a6c-a892-037c3a8c9826'),
('d33d8e4d-0d6e-4871-be66-feb5a053627c', '2025-01-21', '2025-01-21', 'B. 4 classi da 20 alunni e 3 professori', 'TO DO', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'b4fd5bf4-8f85-4816-9763-bf6f668447e9'),
('d6a92680-e3c0-4f3b-90ff-c1ca338879e6', '2025-02-09', '2025-02-09', 'G. Fiera dei fiori del paese', 'TO DO', 'b91d25aa-cab8-476d-ac9e-c76d72d34c9f', 'fcee204d-24ed-4d38-b729-ccfa20f3b1cc');

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
('0564d9aa-9949-4dcd-b016-070b76795c22', 'hanji.zoe.cliente@gmail.com', 'Hanji', 'Zoe', '6', '2025-01-06 20:55:39.392235'),
('07775b70-75b6-4936-8a9a-d4d283ddcac6', 'kazushi.hasegawa.cliente@gmail.com', 'Kazushi', 'Hasegawa', '7', '2025-01-06 20:11:43.351929'),
('1e6b085e-5ad0-4a6c-a892-037c3a8c9826', 'erwin.smith.cliente@gmail.com', 'Erwin', 'Smith', '3', '2025-01-06 20:49:33.651851'),
('2595b09b-9c9c-498a-bef8-2e9fa4ca4231', 'eren.jaeger.cliente@gmail.com', 'Eren', 'Jaeger', '1', '2025-01-06 20:39:30.931813'),
('3342248d-e47b-4ee8-a398-5875a083cc90', 'hisashi.mitsui.cliente@gmail.com', 'Hisashi', 'Mitsui', '5', '2025-01-06 20:09:19.510312'),
('3619e90f-3050-402b-86a7-1c61796e003a', 'reiner.braun.cliente@gmail.com', 'Reiner', 'Braun', '5', '2025-01-06 20:53:06.672630'),
('4730b91c-c207-437a-a5cd-c1e3c6383527', 'kaede.rukawa.cliente@gmail.com', 'Kaede', 'Rukawa', '2', '2025-01-06 20:04:59.511910'),
('86e707e2-dd12-4b7a-982c-a58762f6c7dd', 'kiyota.nobunaga.cliente@gmail.com', 'Kiyota', 'Nobunaga', '6', '2025-01-06 20:10:35.529912'),
('86f4c92b-e1a2-49b2-a7c6-96b252b8f16d', 'haruko.akagi.cliente@gmail.com', 'Haruko', 'Akagi', '4', '2025-01-06 20:08:11.802766'),
('87b5893a-3267-4d50-a49f-e853dbfd75eb', 'hanamichi.sakuragi.cliente@gmail.com', 'Hanamichi', 'Sakuragi', '1', '2025-01-06 19:59:10.092818'),
('a4bf66ef-b02a-4fa6-b788-f119495e85e1', 'alessandra.pedrotto@edu.itspiemonte.it', 'Alessandra', 'Pedrotto', '8', '2025-01-07 00:09:12.840982'),
('b0541118-39d6-4eed-914d-2f7883b1f219', 'sasha.braus.cliente@gmail.com', 'Sasha', 'Braus', '4', '2025-01-06 20:51:34.817381'),
('b4fd5bf4-8f85-4816-9763-bf6f668447e9', 'mikasa.akerman.cliente@gmail.com', 'Mikasa', 'Ackerman', '2', '2025-01-06 20:43:42.669948'),
('ec03bb89-d5c6-4b11-a5f1-a5014f096236', 'ryota.miyagi.cliente@gmail.com', 'Ryota', 'Miyagi', '3', '2025-01-06 20:06:49.890422'),
('fcee204d-24ed-4d38-b729-ccfa20f3b1cc', 'levi.ackerman.cliente@gmail.com', 'Levi', 'Ackerman', '7', '2025-01-06 20:57:14.769935');

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
('1904cd18-218b-4adf-9ed1-bfd9a8efdebc', 'G. Come vengono raccontate le storie delle vittime di mafia per ispirare una riflessione?', '07775b70-75b6-4936-8a9a-d4d283ddcac6', 'd058b959-86a6-4a90-afa3-81f5704d334a', 'TO DO', NULL),
('5c48582b-a827-4fd1-ad03-9a7511c92a1c', 'E. In che modo questa cascina combatte concretamente la mafia?', '3342248d-e47b-4ee8-a398-5875a083cc90', '7436f7bc-f635-4a9c-8b4f-44c491dd16c4', 'TO DO', NULL),
('67a9ada0-5ab5-477e-8600-3195b8420318', 'A. Qual è la storia di questa cascina? Quali erano le sue origini prima di essere gestita dall’azienda attuale?', '87b5893a-3267-4d50-a49f-e853dbfd75eb', '252259e8-b6e3-40c1-887e-e6c1524898f1', 'TO DO', NULL),
('adf21696-15ca-4786-8275-97da6975d1ee', 'F. Quali laboratori o esperienze vengono proposte agli studenti durante la permanenza?', '86e707e2-dd12-4b7a-982c-a58762f6c7dd', '7436f7bc-f635-4a9c-8b4f-44c491dd16c4', 'TO DO', NULL),
('b7e4302e-e35a-4957-aa8d-e541deab0460', 'C. Come si integra questa cascina con il territorio circostante?', 'ec03bb89-d5c6-4b11-a5f1-a5014f096236', '5bbb6b29-a009-4d49-a1d2-5b9b37564735', 'TO DO', NULL),
('d10fdaf6-b839-491f-b1e0-27f60d556516', 'D. Qual è l’impatto della mafia sulle comunità rurali come questa?', '86f4c92b-e1a2-49b2-a7c6-96b252b8f16d', '5bbb6b29-a009-4d49-a1d2-5b9b37564735', 'TO DO', NULL),
('e94c8e90-6638-468f-bf5f-4d061259c32f', 'B. In che modo questa cascina è stata recuperata o trasformata in uno spazio per la sensibilizzazione sulla mafia?', '4730b91c-c207-437a-a5cd-c1e3c6383527', '252259e8-b6e3-40c1-887e-e6c1524898f1', 'TO DO', NULL);

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
(72, '2025-01-04 16:49:40.265286', 'c74ccecf-7785-4cd8-8f78-97acc247c0e6', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14');

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
('252259e8-b6e3-40c1-887e-e6c1524898f1', 'Beatrice', 'Ferrari', 'beatrice.ferrari.emp@gmail.com', '$2a$10$5BmmnQYRdU.xa3VDwYlOnu5DFtGwK6K/YK1fwlgVvpyjFErWOauQq', 12),
('2c5bce10-aa81-4cdb-884b-d608e3a98074', 'Enrico', 'Romano', 'enrico.romano.cliente@gmail.com', '$2a$10$fMz5RveK03ZkkprzRp8kueAD0IptQag9kf27/jguvzYSnaZ3kBwaG', 12),
('5bbb6b29-a009-4d49-a1d2-5b9b37564735', 'Carola', 'Esposito', 'carola.esposito.emp@gmail.com', '$2a$10$MWJKmkdeM5iZLjg7HJzXrOLxbfRnh6/WYlhHNqHP1EdhvUPigtPBO', 12),
('7436f7bc-f635-4a9c-8b4f-44c491dd16c4', 'Domenico', 'Bianchi', 'domenico.bianchi.emp@gmail.com', '$2a$10$cQK0V39Ion6fGdsZ00xVnOH8JWjsxyvMRSKWnmuh./NWZaRWw32PW', 12),
('b91d25aa-cab8-476d-ac9e-c76d72d34c9f', 'Fabiola', 'Colombo', 'fabiola.colombo.cliente@gmail.com', '$2a$10$hkeYsvSzetcCv.KiCIdoOeXdSGDp1IsIkomXpqe1cvrH4X2Iwi7GC', 12),
('bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'Alessandra', 'Pedrotto', 'alessandra.pedrotto@edu.itspiemonte.it', '$2a$10$dxmtIsu6A7PB9djr8WHQfefeSYoHhJkovWft6BkeuA4Q2Fuf4QwBa', 24),
('d058b959-86a6-4a90-afa3-81f5704d334a', 'Amelia', 'Russo', 'amelia.russo.emp@gmail.com', '$2a$10$gjgYZqlQ0H/g3Kwx7JTai.pp6ZHPZgZUpoThuyJeSYcBg6L89u9Ie', 16),
('dccf76d3-1a94-4076-88d3-e3d447441d30', 'Logan', 'Paul', 'admin.employee@gmail.com', '$2a$10$3B5WxFfLQBl465KwyHj1HOAgEiAdBYgV2yUemN2luLxUwO4MY1BSm', 23);

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
('dccf76d3-1a94-4076-88d3-e3d447441d30', '2'),
('dccf76d3-1a94-4076-88d3-e3d447441d30', '1'),
('bad3ad59-c90d-42a3-a12b-602ccfa2dc14', '1'),
('d058b959-86a6-4a90-afa3-81f5704d334a', '1'),
('252259e8-b6e3-40c1-887e-e6c1524898f1', '1'),
('5bbb6b29-a009-4d49-a1d2-5b9b37564735', '1'),
('7436f7bc-f635-4a9c-8b4f-44c491dd16c4', '1'),
('2c5bce10-aa81-4cdb-884b-d608e3a98074', '1'),
('b91d25aa-cab8-476d-ac9e-c76d72d34c9f', '1');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `booking_form`
--
ALTER TABLE `booking_form`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKe3hhn50pfkylr7l17kjlheort` (`user_id`),
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
  ADD UNIQUE KEY `UKtc3jyt7i3d2hms10u3lq4jog0` (`email`,`category_id`,`name`,`surname`) USING HASH,
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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=73;

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
  ADD CONSTRAINT `FKe3hhn50pfkylr7l17kjlheort` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
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
  ADD CONSTRAINT `FK737wsxxu99pnpex9q84gq9xm6` FOREIGN KEY (`generalform_id`) REFERENCES `general_form` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `FKk9a8x0t26tvpkou727e2go2cm` FOREIGN KEY (`general_form_id`) REFERENCES `general_form` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `assiged_to` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Limiti per la tabella `password_reset`
--
ALTER TABLE `password_reset`
  ADD CONSTRAINT `FK3rcc5avyc21uriav34cjrqc91` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

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
