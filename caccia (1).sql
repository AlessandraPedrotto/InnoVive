-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Dic 22, 2024 alle 21:16
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
-- Struttura della tabella `category`
--

CREATE TABLE `category` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `general_form`
--

CREATE TABLE `general_form` (
  `id` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `category_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `information_form`
--

CREATE TABLE `information_form` (
  `id` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `general_form_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(29, '2024-12-22 21:24:24.053722', '8c6bc70e-d19a-4e0e-8ea6-b56d9fca7340', 'cd7b3594-e4af-448b-b835-171848ebec31');

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
('4e1161c6-56a8-4041-9902-4fa5417598cd', 'Riccardo', 'Tarenzi', 'riccardo.tarenzi.emp@gmail.com', '$2a$10$3Kcww1KSQAR4F49hJPPH3unM4Q81J6KO4JC59vrmO0CUqMXL4Ol46', 6),
('5e0c5595-32e1-40b0-8b1f-4f9ffb2afc87', 'Bianca', 'Deriachi', 'biancha.deriachi.emp@gmail.com', '$2a$10$GpEo9fIJ9fiWBkXwpKKci.nW1x.TNqGiwJ/Zup8dMWVibxUBdCo0y', 1),
('6e2bf5a4-ba2d-4da0-8294-adcf55a6977a', 'Sara', 'Pittelli', 'employee@gmail.com', '$2a$10$wXN/7AoO1wcbySopJS8mS.vQK1eA6Uz9e155vlFgtu6ALm5g1yJ6m', NULL),
('871b28da-5b50-431f-b930-b11dc0308b89', 'Alessia', 'Vigliarolo', 'alessia.vigliarolo.emp@gmail.com', '$2a$10$Dhaqgb2usquV2UYF52fiD.cPUMFLmnu4eoQPYTVSy/T7rKooxe3wW', NULL),
('a686b382-eef6-4f15-973d-da77c90793fa', 'Alessia', 'Marcimino', 'alessia.marcimino.emp@gmail.com', '$2a$10$8JnJ8eGQgBwtcGCwb5YlFuRAsvkVrgQ4S.DLNtHcqdIeAbAUDlOv2', 1),
('aa76f92e-bd0a-4647-8678-a409cc2fe9cf', 'Ilaria', 'Pieretti', 'admin@gmail.com', '$2a$10$Ui8SmE4OyhD9ucq/isMzIOXZMxYG7FyAvNjLr58dsCQoXWCcBKt2S', NULL),
('cd7b3594-e4af-448b-b835-171848ebec31', 'Alessandra', 'Pedrotto', 'alessandra.pedrotto@edu.itspiemonte.it', '$2a$10$an8HcISVPwzH9NGuLf9r/.bhftWdjK.tUAJB3DqyaHV8s12BvGId2', 3),
('dccf76d3-1a94-4076-88d3-e3d447441d30', 'Logan', 'Paul', 'admin.employee@gmail.com', '$2a$10$3B5WxFfLQBl465KwyHj1HOAgEiAdBYgV2yUemN2luLxUwO4MY1BSm', 7),
('f35183ad-9740-46f4-a11b-0b3d8b842154', 'Andrea', 'Daga', 'andrea.daga.emp@gmail.com', '$2a$10$BhjbUHYf10/8HVecdOtLDO3yXo5M7op5dHGrw2mTyxyknSiM8xnpi', NULL);

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
(1, 'https://images.pexels.com/photos/4792288/pexels-photo-4792288.jpeg?auto=compress&cs=tinysrgb&w=400'),
(2, 'https://images.pexels.com/photos/1000445/pexels-photo-1000445.jpeg?auto=compress&cs=tinysrgb&w=400'),
(3, 'https://images.pexels.com/photos/3184418/pexels-photo-3184418.jpeg?auto=compress&cs=tinysrgb&w=400'),
(4, 'https://images.pexels.com/photos/1198171/pexels-photo-1198171.jpeg?auto=compress&cs=tinysrgb&w=400'),
(5, 'https://images.pexels.com/photos/3184431/pexels-photo-3184431.jpeg?auto=compress&cs=tinysrgb&w=400'),
(6, 'https://images.pexels.com/photos/3184396/pexels-photo-3184396.jpeg?auto=compress&cs=tinysrgb&w=400'),
(7, 'https://images.pexels.com/photos/313691/pexels-photo-313691.jpeg?auto=compress&cs=tinysrgb&w=400');

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
('cd7b3594-e4af-448b-b835-171848ebec31', '1');

--
-- Indici per le tabelle scaricate
--

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
  ADD UNIQUE KEY `UKcj3a4jf1ititv9q9ied8my9s5` (`general_form_id`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT per la tabella `user_image`
--
ALTER TABLE `user_image`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `general_form`
--
ALTER TABLE `general_form`
  ADD CONSTRAINT `FKexviuaoaiiafyxvsoce0rs0n7` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

--
-- Limiti per la tabella `information_form`
--
ALTER TABLE `information_form`
  ADD CONSTRAINT `FKk9a8x0t26tvpkou727e2go2cm` FOREIGN KEY (`general_form_id`) REFERENCES `general_form` (`id`);

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
