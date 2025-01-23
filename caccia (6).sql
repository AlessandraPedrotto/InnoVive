-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Gen 23, 2025 alle 14:37
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
('03e19a5f-1336-4273-824f-68d382b890c8', '2025-01-31', '2026-01-02', 'Siamo 5 adulti e 30 ragazzi', 'TO DO', NULL, '5746f6d9-7c66-40d3-81ad-fe16e0a46906'),
('081114eb-a95d-41bf-b743-41d3f9a4ec8f', '2025-01-28', '2025-01-28', 'Saremmo 4 classi da 20 ragazzi cadauna e 10 professori', 'TO DO', NULL, '5381e324-4658-4534-9601-946542113423'),
('0cb97320-fe39-416a-b38c-2d8b108fef58', '2025-01-18', '2025-01-18', 'Ci saranno 10 ragazzi disabili e 5 accompagnatori', 'TO DO', NULL, 'a974f6be-d252-4c3b-adef-738ee3b6bc4e'),
('0e6eb1f2-c685-4f92-a1da-c55197382c94', '2025-01-22', '2025-01-22', 'Considerando lo staff saremo 50 persone adulte', 'TO DO', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', '8065abd6-2238-4b68-8785-2f5ce69686d2'),
('11c663b5-381a-4a8b-9c27-295beb22dbe6', '2025-01-26', '2025-01-26', 'Siamo 2 classi (60 persone) e 6 professori', 'TO DO', NULL, 'a01b702f-ab08-4307-b3ce-9551e256672b'),
('12b691d7-41dd-48be-87e4-6844ea7bd582', '2025-01-30', '2025-01-30', 'Vorremmo organizzare un mercatino di beneficienza', 'TO DO', NULL, '89fa770c-a495-40b1-9a95-2286f5a3d6c6'),
('1af29a9a-c142-46bc-bcb5-93fbc8365004', '2025-01-29', '2025-01-29', 'Vorremmo utilizzare la vostra cascina per celebrare un matrimonio', 'TO DO', NULL, 'c57cbcd8-977c-412c-838c-a0c2b59e7383'),
('1c8721a0-e1f6-4b71-b67d-ca99f5002286', '2025-01-28', '2025-01-28', 'Siamo 2 professori e 20 alunni', 'TO DO', NULL, 'a255909d-560d-4223-b975-71cfd103240c'),
('2036803b-831a-48e7-be26-77cfdb20c814', '2025-01-22', '2025-01-22', 'Siamo 4 accompagnatori e 30 ragazzi (scout)', 'TO DO', NULL, '0940ee7f-2159-4d89-872c-e98807918266'),
('24ee95a6-afef-4d4d-9d86-600d2bc1b2ae', '2025-01-30', '2025-01-30', 'Sarebbe possibile avere un preventivo sui costi per trascorrere una giornata da voi? ', 'TO DO', NULL, '32ea72a7-068b-4457-8744-b059a928c6e0'),
('270d1e2c-a017-4504-a718-f71a928f1a5e', '2025-01-28', '2025-01-28', 'Arriveremmo per le 10:00 per poi andarcene verso le 17:00', 'TO DO', NULL, '7406fd99-db46-47af-9063-e78bf3a80088'),
('2b7b8857-5ac9-462d-96ea-9cd9c18b17db', '2025-01-29', '2025-01-29', 'La cascina è disponibile per ospitare un evento aziendale?', 'TO DO', NULL, 'b4a1cdaf-3817-44ed-bfad-c47faefa98af'),
('2c29ae02-fa9b-4aa1-839d-ad59fb4f902e', '2025-01-21', '2025-01-21', 'In totale saremo 20 ragazzi, di cui uno disabile, e 3 professori', 'TO DO', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', '2ab66a44-4a74-4d7e-9735-b83f85377f0f'),
('2d2586f9-08af-4dcc-9aa1-c1bcba2a59bd', '2025-01-27', '2025-01-27', 'Siamo 30 alunni e 4 insegnanti', 'DONE', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', '563c7f30-56c5-48a7-9f72-2ea2e60d32fa'),
('2f780bb2-f072-43c0-b944-6508ea5cd55d', '2025-01-22', '2025-01-22', 'Avete un massimo/minimo di persone che ci devono essere per le attività?', 'TO DO', NULL, '7a80cbad-3563-476e-8f9f-47e911ea479c'),
('30069db4-4e69-4cc9-9185-df47a4997a92', '2025-01-27', '2025-01-28', 'Gli animali sono ammessi?', 'TO DO', NULL, '51a5e9f2-0de5-4834-b119-2081c7495d65'),
('469fe08d-a7cc-4e46-adb0-6f12779bce1d', '2025-01-13', '2025-01-13', 'Possiamo svolgere solo laboratori pratici?', 'TO DO', NULL, '31ca6ed6-6999-4eaa-abeb-3157d1d155c5'),
('4a515ba8-dde0-40c1-a7d8-7096555e3c55', '2025-01-18', '2025-01-18', 'E\' possibile arrivare per le 09:00 ed iniziare subito le attività?', 'TO DO', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', '199b8d16-db13-444c-9d6c-582d92465ac9'),
('5bd185b2-0111-4989-9bfb-de51e67f0aca', '2025-01-10', '2025-01-12', 'Siamo 4 adulti e 20 bambini di 9-10 anni', 'IN PROGRESS', 'dccf76d3-1a94-4076-88d3-e3d447441d30', '2595b09b-9c9c-498a-bef8-2e9fa4ca4231'),
('61c343fc-20ff-45ba-b225-7027f84fd08a', '2025-01-31', '2025-01-31', 'Per i ragazzi disabili ci sono delle agevolazioni per i costi?', 'TO DO', NULL, '3daab7a0-4fd8-4f24-bc61-e2f835d964ab'),
('684a5341-6c80-401b-9223-9771dff21b3c', '2025-01-22', '2025-01-22', 'In caso di sole sarebbe possibile svolgere le attività all\'aperto?', 'TO DO', NULL, '5bb75537-6ac7-4432-9990-ee9fae81c692'),
('737e97be-1b7d-4fd7-885d-8256ceea7be5', '2025-01-30', '2025-01-30', 'In totale siamo 4 persone: 35 ragazzi e 5 insegnanti', 'TO DO', NULL, 'b7176b16-9fe1-4726-a374-092ba2aaea6f'),
('74a305e4-b463-47c8-963a-27920b9af3ff', '2025-01-26', '2025-01-26', 'In una giornata quante attività si possono svolgere?', 'TO DO', NULL, 'dcaaa24f-5862-4a8e-bb45-72ee4b7e89bd'),
('7898ea4e-1fd5-4319-ac4c-d8c43b320b1f', '2025-01-26', '2025-01-26', 'Vorremmo scrivere un articolo sulla cascina, sarebbe possibile intervistare degli operatori?', 'TO DO', 'dccf76d3-1a94-4076-88d3-e3d447441d30', '2fcfcaa1-77a9-4925-9c15-9fd92a5b82e8'),
('85eaeba2-88a7-4cb5-a115-024a0192c0a8', '2025-01-23', '2025-01-23', 'Ci sono delle agevolazioni per gruppi di studenti universitari?', 'TO DO', NULL, '9740de6d-f866-4966-b96b-7d1f58de81a4'),
('8cef99ef-cbdf-4cc1-8672-cad0a54cf791', '2025-01-31', '2025-01-31', 'A che ora bisogna arrivare?', 'TO DO', NULL, '1589c6cc-02dd-4293-8fdd-996a2d146b11'),
('920eb56b-aecd-409d-a459-8dd3461831aa', '2025-01-28', '2025-01-31', 'Gruppo scout di 14 ragazzi e 2 accompagnatori', 'TO DO', NULL, '77c02a70-17bf-48c4-a137-039288a3a0b5'),
('98af2a2d-1126-4b9c-b001-2a4f695afee3', '2025-05-20', '2025-05-28', 'Una maestra e 13 bambini', 'TO DO', NULL, 'c85568fd-ba12-437c-9772-4854c7734a24'),
('9940dbdb-a256-4c7a-8ac7-a9659d9414ab', '2025-01-31', '2025-01-31', '5 professori e 40 alunni', 'TO DO', NULL, '88098b3e-f56e-4bd9-841c-78e8dc9e3430'),
('9a6831e8-4831-4cf5-af19-aed8d409c334', '2025-01-22', '2025-01-22', 'Siamo 2 maestre e 20 bambini', 'TO DO', NULL, '764a53ec-40be-4c2d-b342-a9a3c9a99654'),
('9b5bd3aa-cc75-467b-8fc5-763b75cc9890', '2025-01-26', '2025-01-26', 'Si possono svolgere solo attività pratiche?', 'TO DO', NULL, '7cc377bf-e53d-4914-8f90-09e0695c55bb'),
('a313df64-5c1e-457b-a286-60e72ca55fe5', '2025-01-21', '2025-01-21', 'Quali film fate vedere durante l\'attività mafia e cinema?', 'TO DO', NULL, '648067e4-b75c-4af0-ab72-360ad08a4d2b'),
('a91b4172-816c-492e-9f33-416106cd82af', '2025-01-21', '2025-01-21', 'Le attività hanno costi diversi?', 'TO DO', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', '3ed7926d-8072-481f-b542-b4dfabf99840'),
('b7567561-d6e3-4b9f-9350-d3a23c3a4b1c', '2025-04-08', '2025-04-08', 'Bisogna portarsi il pranzo?', 'TO DO', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', '01e6d306-6c44-4cf4-a350-28209f2ca318'),
('b99a12df-8f8a-4750-9255-0138067c0a07', '2025-01-31', '2025-01-31', 'C\'è un orario per l\'inizio e la fine della visita?', 'DONE', NULL, '6bb7de06-d466-4f50-82f8-d79dd9109115'),
('c85edde4-3534-4794-92d2-ebf7d0805282', '2025-01-22', '2025-01-22', '4 professori e 24 alunni', 'TO DO', NULL, '14f14abc-8b6d-4989-8b58-b41ea5d7cbb5'),
('ca3d924d-f056-40c8-862d-48ac0736c5df', '2025-01-21', '2025-01-21', 'Siamo 2 professori e 17 ragazzi', 'TO DO', NULL, 'db13b8df-9c89-4cc7-beac-98fd91d4e5c9'),
('cb08a4ca-6a37-4ee3-a395-238468ea3cd0', '2025-01-23', '2025-01-23', 'Come attività ci sono anche quelle che comprendono dei giochi?', 'TO DO', NULL, 'c2adf1b7-13d1-4b7d-a812-7490c268d603'),
('cd9a4cc3-d111-43e5-ae34-d4f50972b8ea', '2025-01-21', '2025-01-21', 'Quante attività si possono svolgere in una giornata?', 'TO DO', NULL, 'a709e6d7-9f62-44e2-8b9e-9a2e47658139'),
('d00c94b4-d20c-495c-97e0-0e00d8fc1c54', '2025-01-26', '2025-01-26', '3 professori e 20 alunni', 'TO DO', NULL, '57e4e0d1-26c2-4fca-96b3-8894c8c63e2a'),
('d24261e1-b99c-4842-89bf-d952343f4962', '2025-06-19', '2025-06-27', 'Gruppo scout da 15 ragazzi e 4 accompagnatori', 'IN PROGRESS', '252259e8-b6e3-40c1-887e-e6c1524898f1', '58746ef4-f15e-4d80-9204-d146a1ec2a8d'),
('d33d8e4d-0d6e-4871-be66-feb5a053627c', '2025-01-21', '2025-01-21', '2 classi da 20 alunni e 3 professori', 'DONE', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'b4fd5bf4-8f85-4816-9763-bf6f668447e9'),
('d3485269-6cfe-47f4-b0c4-b14d733d98e0', '2025-01-23', '2025-01-23', 'Una classe da 19 alunni e 2 professori', 'TO DO', NULL, 'f19d96a6-444b-4212-a439-9b38a189da12'),
('d6a92680-e3c0-4f3b-90ff-c1ca338879e6', '2025-02-09', '2025-02-09', 'Un professore e 18 alunni', 'TO DO', NULL, 'fcee204d-24ed-4d38-b729-ccfa20f3b1cc'),
('d958c5dc-765a-4795-accc-7eb96d71eff9', '2025-01-25', '2025-01-25', 'Evento aziendale, 60 adulti', 'IN PROGRESS', 'dccf76d3-1a94-4076-88d3-e3d447441d30', '1deede6a-7653-485d-a5f4-1b02db8eb7e4'),
('e0c17a49-e064-466f-bb9a-67c4f03ade90', '2025-01-29', '2025-01-29', 'Classe di 15 alunni e 4 accompagnatori', 'TO DO', NULL, 'bdda3760-0102-45b6-89e3-76300a235232'),
('eb240f45-7ce2-4091-9af5-bce75cd2df6a', '2025-02-06', '2025-02-13', 'Gita per 20 ragazzi e 4 professori', 'TO DO', NULL, '365c8287-378e-4f96-a190-0e08b3e00bee'),
('eb7cb483-0810-4d02-8773-9bdf28209c66', '2025-01-19', '2025-01-19', 'Siamo in 3 insegnanti e 25 ragazzi', 'DONE', NULL, 'a6382799-1edd-4f17-b41b-66cde121c063'),
('ec1d1be1-a514-4990-b3df-c2b88ab5d79f', '2025-01-26', '2025-01-26', 'Si può organizzare una giornata con dei ragazzi disabili?', 'TO DO', NULL, '0b6b8c3f-20ce-46ef-96e4-cf4d5161116d'),
('f5746006-6ac3-451c-b433-2ada5ef895c3', '2025-01-31', '2025-01-31', 'Vorremmo organizzare un visita per tutte le classi della scuola, qual è il massimo di persone che ci possono essere durante una visita?', 'TO DO', NULL, '7f615f5b-de31-4eef-a462-12d04a59f33c');

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
('01831c65-e7f1-412b-908e-5619a6dc26a9', 'mattia.posa.cliente@gmail.com', 'Mattia', 'Posa', '1', '2025-01-21 11:26:12.606731'),
('01e6d306-6c44-4cf4-a350-28209f2ca318', 'paola.lista.cliente@gmail.com', 'Paola', 'Lista', '2', '2025-01-21 15:45:18.743654'),
('0242fc49-f59b-4cf9-9bd3-c34663413328', 'livia.bianchi.cliente@gmail.com', 'Livia', 'Bianchi', '3', '2025-01-21 12:02:31.281840'),
('0499637b-8f00-47cb-a1e1-3fa7bbb47471', 'alessia.fiore.cliente@gmail.com', 'Alessia', 'Fiore', '4', '2025-01-21 11:59:14.896187'),
('07775b70-75b6-4936-8a9a-d4d283ddcac6', 'kazushi.hasegawa.cliente@gmail.com', 'Kazushi', 'Hasegawa', '5', '2025-01-06 20:11:43.351929'),
('0940ee7f-2159-4d89-872c-e98807918266', 'simba.leone.cliente@gmail.com', 'Simba', 'Leone', '6', '2025-01-21 15:34:52.841920'),
('0b6b8c3f-20ce-46ef-96e4-cf4d5161116d', 'michele.dona.cliente@gmail.com', 'Michele', 'Dona', '7', '2025-01-21 15:59:01.579717'),
('0ebf9822-fd99-46da-9300-74b52563b68c', 'federico.ferdinando.cliente@gmail.com', 'Federico', 'Ferdinando', '8', '2025-01-21 00:20:14.797889'),
('0f076167-a6fa-4c08-b2db-896e435c660d', 'sara.fino.cliente@gmail.com', 'Sara', 'Fino', '1', '2025-01-14 17:07:32.429476'),
('11b2c7e2-5faf-4225-97a9-8a26be2399be', 'sara.pittelli.cliente@gmail.com', 'Sara', 'Pittelli', '2', '2025-01-21 14:03:23.181919'),
('131cac74-0cac-4167-a90f-c9f10532c4a4', 'noemi.vurchio.cliente@gmail.com', 'Noemi', 'Vurchio', '3', '2025-01-15 17:30:25.657814'),
('14f14abc-8b6d-4989-8b58-b41ea5d7cbb5', 'arianna.casa.cliente@gmail.com', 'Arianna', 'Casa', '4', '2025-01-21 10:07:46.937889'),
('1589c6cc-02dd-4293-8fdd-996a2d146b11', 'sandra.vaso.cliente@gmail.com', 'Sandra', 'Vaso', '5', '2025-01-20 23:05:42.834947'),
('199b8d16-db13-444c-9d6c-582d92465ac9', 'enrico.sicili.cliente@gmail.com', 'Enrico', 'Sicilia', '6', '2025-01-18 11:50:10.467341'),
('1bfcb592-a6d8-43d5-81ca-da17eb41599b', 'icaro.ancona.cliente@gmail.com', 'Icaro', 'Ancona', '7', '2025-01-21 00:19:56.379723'),
('1c49cc4a-b73f-4758-9ea0-a447958ce613', 'roberto.rosso.cliente@gmail.com', 'Roberto', 'Rosso', '8', '2025-01-21 00:23:23.453924'),
('1deede6a-7653-485d-a5f4-1b02db8eb7e4', 'ellen.ripley.cliente@gmail.com', 'Ellen', 'Ripley', '1', '2025-01-12 13:28:57.676762'),
('1e6b085e-5ad0-4a6c-a892-037c3a8c9826', 'erwin.smith.cliente@gmail.com', 'Erwin', 'Smith', '2', '2025-01-06 20:49:33.651851'),
('1f646219-1760-48ea-9c8c-e618ea6ff0ac', 'lisa.bianchi.cliente@gmail.com', 'Lisa', 'Bianchi', '3', '2025-01-20 23:06:11.945940'),
('21830915-a3fb-4063-8108-0925552dfb14', 'giovanni.tomaino.cliente@gmail.com', 'Giovanni', 'Tomaino', '4', '2025-01-21 11:49:41.985934'),
('2271a3f4-71f7-4103-aadd-c43faf02892a', 'giulio.milo.cliente@gmail.com', 'Giulio', 'Milo', '5', '2025-01-15 10:29:02.076763'),
('228e5a77-0ba1-47ac-bc9e-d65f860a715a', 'giorgio.teodoro.cliente@gmail.com', 'Giorgio', 'Teodoro', '6', '2025-01-20 23:14:23.576791'),
('25007eb1-303c-4f15-b37d-e49b8628629b', 'carlo.bali.cliente@gmail.com', 'Carlo', 'Bali', '7', '2025-01-21 11:53:11.651512'),
('2595b09b-9c9c-498a-bef8-2e9fa4ca4231', 'eren.jaeger.cliente@gmail.com', 'Eren', 'Jaeger', '8', '2025-01-06 20:39:30.931813'),
('27e1aaac-e7f3-4758-875e-5f26d1ee9d06', 'diana.viglialoro.cliente@gmail.com', 'Diana', 'Viglialoro', '1', '2025-01-21 23:12:23.801032'),
('281ea734-c1e2-40a2-8f66-3cb951ac75de', 'camillo.oman.cliente@gmail.com', 'Camillo', 'Oman', '2', '2025-01-15 09:40:21.115206'),
('298b2a94-6041-4dd4-96db-4e8794205f65', 'pino.tasso.cliente@gmail.com', 'Pino', 'Tasso', '3', '2025-01-15 21:46:52.918442'),
('2ab66a44-4a74-4d7e-9735-b83f85377f0f', 'silvia.eritrea.cliente@gmail.com', 'Silvia', 'Eritrea', '4', '2025-01-21 23:19:35.794194'),
('2ab95d62-2d97-4ff3-baba-0d0c2d98e0c1', 'paolina.angola.cliente@gmail.com', 'Paolina', 'Angola', '5', '2025-01-15 10:38:38.090488'),
('2c353787-b99a-4ff5-8e7c-0b2f29996da4', 'emanuele.marino.cliente@gmail.com', 'Emanuele', 'Marino', '6', '2025-01-20 23:46:19.707797'),
('2d528322-baf7-4086-850d-df72e437c7d7', 'sebastiano.zutti.cliente@gmail.com', 'Sebastiano', 'Zutti', '7', '2025-01-21 11:58:40.556209'),
('2d8f7311-5dc7-49c3-8722-becead8a6404', 'fabrizio.rinaldi.cliente@gmail.com', 'Fabrizio', 'Rinaldi', '8', '2025-01-20 20:42:09.188818'),
('2f74e388-c55c-4aca-9f67-b33caeb43a80', 'delia.nima.cliente@gmail.com', 'Delia', 'Nima', '1', '2025-01-22 15:54:11.094464'),
('2fcfcaa1-77a9-4925-9c15-9fd92a5b82e8', 'hilaria.beta.cliente@gmail.com', 'Hilaria', 'Beta', '2', '2025-01-22 15:56:18.498176'),
('30ea7992-544e-4122-832d-97a9ee84da2e', 'mattia.ferrero.cliente@gmail.com', 'Mattia', 'Ferrero', '3', '2025-01-20 22:43:07.144008'),
('31ca6ed6-6999-4eaa-abeb-3157d1d155c5', 'mauro.pasta.cliente@gmail.com', 'Mauro', 'Pasta', '4', '2025-01-13 21:34:58.415251'),
('32ea72a7-068b-4457-8744-b059a928c6e0', 'massimo.destro.cliente@gmail.com', 'Massimo', 'Destro', '5', '2025-01-21 16:39:24.802783'),
('3342248d-e47b-4ee8-a398-5875a083cc90', 'hisashi.mitsui.cliente@gmail.com', 'Hisashi', 'Mitsui', '6', '2025-01-06 20:09:19.510312'),
('352ea773-a1d1-485e-9a63-9db30560302e', 'domenico.posa.cliente@gmail.com', 'Domenico', 'Posa', '7', '2025-01-21 11:33:35.859788'),
('365c8287-378e-4f96-a190-0e08b3e00bee', 'tessa.togo.cliente@gmail.com', 'Tessa', 'Togo', '8', '2025-01-21 10:07:04.412449'),
('36dd87eb-7f8c-42d2-a90b-63cdd9bb9500', 'bianca.perla.cliente@gmail.com', 'Bianca', 'Perla', '1', '2025-01-21 00:27:28.073395'),
('3b2db3cb-3055-4834-8420-955001aa057b', 'rosalinda.dono.cliente@gmail.com', 'Rosalinda', 'Dono', '2', '2025-01-20 23:29:05.601110'),
('3ca4e3a9-926e-4b21-9978-145ff628cc28', 'rossana.lota.cliente@gmail.com', 'Rossana', 'Lota', '3', '2025-01-21 10:34:39.538325'),
('3daab7a0-4fd8-4f24-bc61-e2f835d964ab', 'giordano.musu.cliente@gmail.com', 'Giordano', 'Musu', '4', '2025-01-22 21:28:13.389912'),
('3ed7926d-8072-481f-b542-b4dfabf99840', 'elisa.livorno.cliente@gmail.com', 'Elisa', 'Livorno', '5', '2025-01-21 11:25:58.034682'),
('4730b91c-c207-437a-a5cd-c1e3c6383527', 'kaede.rukawa.cliente@gmail.com', 'Kaede', 'Rukawa', '6', '2025-01-06 20:04:59.511910'),
('47ef28c4-f991-4937-b729-cd91447c79cb', 'mara.genova.cliente@gmail.com', 'Mara', 'Genova', '7', '2025-01-15 17:02:30.294686'),
('498b4192-6d0f-40b6-87ce-36e257401042', 'benedetta.consiglia.cliente@gmail.com', 'Benedetta', 'Consiglia', '8', '2025-01-21 11:57:04.532247'),
('4b7974c2-546e-4b82-89e6-56b09574acc9', 'simone.oras.cliente@gmail.com', 'Simone', 'Oras', '1', '2025-01-21 11:06:16.912606'),
('4c8f907e-4d1f-4c43-aa34-a18e7f226014', 'sergio.milo.cliente@gmail.com', 'Sergio', 'Milo', '2', '2025-01-22 15:55:09.010659'),
('50c8499d-e265-4db5-87d7-9a3f2e7a8437', 'daniela.passa.cliente@gmail.com', 'Daniela', 'Passa', '3', '2025-01-21 19:12:13.402913'),
('51a5e9f2-0de5-4834-b119-2081c7495d65', 'samuele.tol.cliente@gmail.com', 'Samuele', 'Tola', '4', '2025-01-21 15:29:25.227505'),
('524a73b8-d6f4-46f0-8290-acb0d1e7ed42', 'riccardo.caruso.cliente@gmail.com', 'Riccardo', 'Caruso', '5', '2025-01-14 17:11:51.971900'),
('5381e324-4658-4534-9601-946542113423', 'alina.cosa.cliente@gmail.com', 'Alina', 'Cosa', '6', '2025-01-21 12:02:43.663370'),
('54caaf43-1cbd-45da-89bf-03ebc4c2b7d6', 'lisa.pisa.cliente@gmail.com', 'Lisa', 'Pisa', '7', '2025-01-20 20:38:38.875322'),
('55c11745-4af4-499b-870b-83aba5d476ed', 'maria.posta.cliente@gmail.com', 'Maria', 'Posta', '8', '2025-01-21 16:38:39.397368'),
('563c7f30-56c5-48a7-9f72-2ea2e60d32fa', 'kentaro.miura.cliente@gmail.com', 'Kentaro', 'Miura', '1', '2025-01-12 13:32:29.659896'),
('5746f6d9-7c66-40d3-81ad-fe16e0a46906', 'ridley.scott.cliente@gmail.com', 'Ridley', 'Scott', '2', '2025-01-12 13:26:31.056894'),
('57e4e0d1-26c2-4fca-96b3-8894c8c63e2a', 'ciro.esposito.cliente@gmail.com', 'Ciro', 'Esposito', '3', '2025-01-21 15:27:43.803776'),
('580819eb-03d0-487f-b56f-70ac0b9d7c8e', 'roberto.caruso.cliente@gmail.com', 'Roberto', 'Caruso', '4', '2025-01-15 10:12:18.981388'),
('58746ef4-f15e-4d80-9204-d146a1ec2a8d', 'alessandra.pedrotto@edu.itspiemonte.it', 'Alessandra', 'Pedrotto', '5', '2025-01-13 17:26:11.869627'),
('59dc61da-3783-405c-8132-0f8e3be41ea6', 'loredana.vittorio.cliente@gmail.com', 'Loredana', 'Vittorio', '6', '2025-01-21 11:06:32.423075'),
('5a0f6a3f-f509-4ab7-b6af-e4bcf00881e8', 'kevin.ludovico.cliente@gmail.com', 'Kevin', 'Ludovico', '7', '2025-01-20 22:51:04.148532'),
('5bb75537-6ac7-4432-9990-ee9fae81c692', 'livia.cola.cliente@gmail.com', 'Livia', 'Cola', '8', '2025-01-21 16:03:48.238894'),
('5d033811-8b19-4973-bd87-42266653d8ec', 'oras.corvo.cliente@gmail.com', 'Oras', 'Corvo', '1', '2025-01-21 11:58:58.998665'),
('5d4664b1-797e-4f25-ab6f-416a2864a852', 'nadia.nemo.cliente@gmail.com', 'Nadia', 'Nemo', '2', '2025-01-20 20:41:52.023685'),
('5f4b7f9e-680b-4858-bd1e-9274feade2d1', 'luciano.pitta.cliente@gmail.com', 'Luciano', 'Pitta', '3', '2025-01-21 11:55:35.387914'),
('5f740593-72d9-4de2-91f0-b64832c4333d', 'orazio.colombo.cliente@gmail.com', 'Orazio', 'Colombo', '4', '2025-01-21 10:32:07.281456'),
('6391ad45-cb6f-4ecd-afed-6b5f0a2ba6c6', 'paola.rossi.cliente@gmail.com', 'Paola', 'Rossi', '5', '2025-01-20 23:46:39.360198'),
('63e4d857-a318-4d13-b0ae-0965b29a27c3', 'fabiola.mucciolo.cliente@gmail.com', 'Fabiola', 'Mucciolo', '6', '2025-01-21 11:09:30.713350'),
('648067e4-b75c-4af0-ab72-360ad08a4d2b', 'alessia.asta.cliente@gmail.com', 'Alessia', 'Asta', '7', '2025-01-20 20:37:55.681550'),
('6b61cfc6-4943-48ff-baa8-40fc3b8c0fa1', 'aria.corallo.cliente@gmail.com', 'Aria', 'Corallo', '8', '2025-01-21 10:12:16.277613'),
('6bb7de06-d466-4f50-82f8-d79dd9109115', 'marinella.etto.cliente@gmail.com', 'Marinella', 'Etto', '1', '2025-01-15 19:09:58.495151'),
('6bdc7d06-7e4a-4afe-936c-56d98e62d696', 'james.corradini.cliente@gmail.com', 'James', 'Corradini', '2', '2025-01-15 20:13:35.349938'),
('6f7caab3-01c6-4e4e-8499-d70940bd1a23', 'noemi.esposito.cliente@gmail.com', 'Noemi', 'Esposito', '3', '2025-01-21 11:32:53.515336'),
('70b6a261-94a0-46a4-95b2-efc566e880f2', 'ronald.cifra.cliente@gmail.com', 'Ronald', 'Cifra', '4', '2025-01-21 11:57:25.104817'),
('7399e9fe-2d95-4a34-ad5e-32241aef5095', 'remo.stato.cliente@gmail.com', 'Remo', 'Stato', '5', '2025-01-21 16:39:51.277401'),
('73c6da8e-47c6-47a8-9438-2a510ca78b09', 'alita.sano.cliente@gmail.com', 'Alita', 'Sano', '6', '2025-01-18 14:50:04.475937'),
('7406fd99-db46-47af-9063-e78bf3a80088', 'marianna.scaringella.cliente@gmail.com', 'Marianna', 'Scaringella', '7', '2025-01-21 15:10:16.916024'),
('74a1e050-edeb-42bb-8ba8-22e1da97a159', 'renato.ricci.cliente@gmail.com', 'Renato', 'Ricci', '8', '2025-01-21 19:06:54.577398'),
('75fb05b8-84af-4257-b79e-e0e840ae8d2c', 'giulia.greco.cliente@gmail.com', 'Giulia', 'Greco', '1', '2025-01-21 11:52:11.208027'),
('764a53ec-40be-4c2d-b342-a9a3c9a99654', 'loris.bella.cliente@gmail.com', 'Loris', 'Bella', '2', '2025-01-21 15:06:12.878842'),
('76653954-fd01-4d5c-806a-434fdf4bcb1d', 'ludovica.corta.cliente@gmail.com', 'Ludovica', 'Corta', '3', '2025-01-21 23:08:41.371479'),
('76ee9ab0-3b07-40f9-bed6-fa983c964570', 'cara.dino.cliente@gmail.com', 'Cara', 'Dino', '4', '2025-01-15 10:41:35.603769'),
('77c02a70-17bf-48c4-a137-039288a3a0b5', 'selena.gora.cliente@gmail.com', 'Selena', 'Gora', '5', '2025-01-20 14:02:57.375144'),
('78e482fe-3ade-43fd-96e0-dbefed4f7a8d', 'mirco.martino.cliente@gmail.com', 'Mirco', 'Martino', '6', '2025-01-21 11:28:12.320196'),
('7a51f110-d2e9-474c-8af8-494653e06674', 'maia.nana.cliente@gmail.com', 'Maia', 'Nana', '7', '2025-01-14 12:57:10.673084'),
('7a80cbad-3563-476e-8f9f-47e911ea479c', 'danilo.samba.cliente@gmail.com', 'Danilo', 'Samba', '8', '2025-01-21 16:38:08.892383'),
('7c8ea4e3-79f0-402a-8b86-a1eec5855726', 'viviana.nomali.cliente@gmail.com', 'Viviana', 'Nomalia', '1', '2025-01-15 09:40:44.880608'),
('7cc377bf-e53d-4914-8f90-09e0695c55bb', 'nova.veneto.cliente@gmail.com', 'Nova', 'Veneto', '2', '2025-01-21 16:03:00.960006'),
('7e893f3e-d9eb-47ae-b0c3-26f9855a1306', 'lucia.sirena.cliente@gmail.com', 'Lucia', 'Sirena', '3', '2025-01-21 11:56:13.764378'),
('7f553f62-0c55-41c4-ad20-9001e91db78c', 'carla.daga.cliente@gmail.com', 'Carlo', 'Daga', '4', '2025-01-15 10:33:59.358767'),
('7f615f5b-de31-4eef-a462-12d04a59f33c', 'benedetta.pisa.cliente@gmail.com', 'Benedetta', 'Pisa', '3', '2025-01-21 10:33:14.791712'),
('8065abd6-2238-4b68-8785-2f5ce69686d2', 'rita.sigla.cliente@gmail.com', 'Rita', 'Sigla', '4', '2025-01-21 11:27:51.685485'),
('807c54d4-8aa4-48b0-8b84-c1e114bc346f', 'tomas.treno.cliente@gmail.com', 'Tomas', 'Treno', '5', '2025-01-20 23:27:33.602890'),
('80b516cc-1fd8-4dfe-90af-517243c6c793', 'tommaso.indovinello.cliente@gmail.com', 'Tommaso', 'Indovinello', '6', '2025-01-21 11:59:38.279505'),
('8205ae48-46f0-415b-861d-6e2b06ab61e7', 'marco.maesano.cliente@gmail.com', 'Marco', 'Maesano', '7', '2025-01-21 11:19:54.318603'),
('840cf865-e45f-41f8-bce1-69d100bbdb00', 'tina.pina.cliente@gmail.com', 'Tina', 'Pina', '8', '2025-01-21 14:09:45.449248'),
('84af1193-2c82-43e3-b41b-ecccabc553bc', 'sofia.vasilache.cliente@gmail.com', 'Sofia', 'Vasilache', '1', '2025-01-15 21:12:36.976229'),
('85a86409-864c-4d38-841f-5fc9f6746003', 'fiorello.roma.cliente@gmail.com', 'Fiorello', 'Roma', '1', '2025-01-21 11:49:13.129206'),
('8613abf6-347f-4328-be8d-0a41584ebd6c', 'alberto.casa.cliente@gmail.com', 'Alberto', 'Casa', '2', '2025-01-18 10:34:46.880187'),
('86e707e2-dd12-4b7a-982c-a58762f6c7dd', 'kiyota.nobunaga.cliente@gmail.com', 'Kiyota', 'Nobunaga', '3', '2025-01-06 20:10:35.529912'),
('86f4c92b-e1a2-49b2-a7c6-96b252b8f16d', 'haruko.akagi.cliente@gmail.com', 'Haruko', 'Akagi', '4', '2025-01-06 20:08:11.802766'),
('87b5893a-3267-4d50-a49f-e853dbfd75eb', 'hanamichi.sakuragi.cliente@gmail.com', 'Hanamichi', 'Sakuragi', '5', '2025-01-06 19:59:10.092818'),
('88098b3e-f56e-4bd9-841c-78e8dc9e3430', 'carlos.topo.cliente@gmail.com', 'Carlos', 'Topo', '6', '2025-01-21 15:28:17.770304'),
('89a7f9eb-ac65-48c5-913a-3dbcb75a0d3d', 'andrea.allegri.cliente@gmail.com', 'Andrea', 'Allegri', '7', '2025-01-22 15:55:49.616412'),
('89fa770c-a495-40b1-9a95-2286f5a3d6c6', 'ettore.bianchi.cliente@gmail.com', 'Ettore', 'Bianchi', '8', '2025-01-20 20:38:58.219544'),
('8c2ff0be-0e57-45fa-890a-981afa05a477', 'greta.massa.cliente@gmail.com', 'Greta', 'Massa', '1', '2025-01-21 11:08:48.172662'),
('8d6c8d32-37b0-4a50-ba4d-33540424f8e8', 'ivan.ludovico.cliente@gmail.com', 'Ivan', 'Ludovico', '2', '2025-01-21 19:05:12.638568'),
('90c59e44-816d-4e34-a59c-e3a85cf8e771', 'monica.marina.cliente@gmail.com', 'Monica', 'Marina', '3', '2025-01-21 11:16:52.158215'),
('956912fb-1053-4876-b0a1-e625d2f39e85', 'ivan.travascio.cliente@gmail.com', 'Ivan', 'Travascio', '4', '2025-01-22 15:55:26.188228'),
('9740de6d-f866-4966-b96b-7d1f58de81a4', 'mirco.fava.cliente@gmail.com', 'Mirco', 'Fava', '5', '2025-01-15 21:19:24.139863'),
('98827656-3bec-41c4-beaa-0a03f4e4d048', 'sergio.vita.cliente@gmail.com', 'Sergio', 'Vita', '6', '2025-01-20 23:41:29.861486'),
('9c9bae61-21db-49b2-9e9c-af0a8effc0aa', 'silvia.ronda.cliente@gmail.com', 'Silvio', 'Ronda', '7', '2025-01-21 11:19:17.522426'),
('9cc8891f-ad98-404a-97d5-d06976a9366a', 'armando.giusti.cliente@gmail.com', 'Armando', 'Giusti', '8', '2025-01-18 14:50:45.377242'),
('9cf1b6f8-8269-4d85-8f9d-232e67ae7530', 'leda.lita.cliente@gmail.com', 'Leda', 'Lite', '1', '2025-01-21 00:20:35.445685'),
('9f287db1-6454-4898-b0b6-8b820d837ec8', 'vincenzo.stele.cliente@gmail.com', 'Vincenzo', 'Stele', '2', '2025-01-21 11:25:01.400667'),
('a01b702f-ab08-4307-b3ce-9551e256672b', 'peter.parker.cliente@gmail.com', 'Peter', 'Parker', '3', '2025-01-21 16:01:20.483807'),
('a06cdaa0-1d9a-4a98-ae03-15635b1c4009', 'otto.bruno.cliente@gmail.com', 'Otto', 'Bruno', '4', '2025-01-20 20:37:20.335140'),
('a12ac8d5-e7df-49e9-9293-df68d277362d', 'bea.tata.cliente@gmail.com', 'Bea', 'Tata', '5', '2025-01-21 19:09:34.089233'),
('a1c8b330-cef9-4b9c-b2a8-60cd221b4c55', 'elisabetta.santoro.cliente@gmail.com', 'Elisabetta', 'Santoro', '6', '2025-01-21 16:33:57.158760'),
('a21d9e85-d2ab-4970-8503-353c83066046', 'serena.lopez.cliente@gmail.com', 'Serena', 'Lopez', '7', '2025-01-20 23:15:16.517515'),
('a255909d-560d-4223-b975-71cfd103240c', 'lorenzo.bolzon.cliente@gmail.com', 'Lorenzo', 'Bolzon', '8', '2025-01-21 10:22:42.684945'),
('a4bf66ef-b02a-4fa6-b788-f119495e85e1', 'alessandra.pedrotto@edu.itspiemonte.it', 'Alessandra', 'Pedrotto', '1', '2025-01-07 00:09:12.840982'),
('a6382799-1edd-4f17-b41b-66cde121c063', 'tiziano.coro.cliente@gmail.com', 'Tiziano', 'Coro', '2', '2025-01-15 20:22:45.604855'),
('a709e6d7-9f62-44e2-8b9e-9a2e47658139', 'alessandra.pedrotto@edu.itspiemonte.it', 'Elisa', 'Pieretti', '3', '2025-01-21 10:08:33.801095'),
('a79a6725-6e7a-43cd-9bd1-f6140229574c', 'luigi.gigi.cliente@gmail.com', 'Luigi', 'Gigi', '4', '2025-01-20 20:42:48.589265'),
('a7b1f1b7-94da-4d37-92cc-fb995af37325', 'omar.salento.cliente@gmail.com', 'Omar', 'Salento', '5', '2025-01-21 11:33:15.348348'),
('a974f6be-d252-4c3b-adef-738ee3b6bc4e', 'stefania.gina.cliente@gmail.com', 'Stefania', 'Gina', '6', '2025-01-18 11:49:58.170824'),
('a9791400-23fd-4b60-8c64-1e03c119c8f9', 'pietro.romano.clienti@gmail.com', 'Pietro', 'Romano', '7', '2025-01-20 23:42:19.098543'),
('a9b1a71f-40c6-463b-9595-084e16b3f0e7', 'arianna.costa.cliente@gmail.com', 'Arianna', 'Costa', '8', '2025-01-15 17:33:39.205061'),
('abbced08-22c9-4ef4-8067-ce159ad4c0d3', 'lisa.fichio.cliente@gmail.com', 'Lisa', 'Fischio', '3', '2025-01-21 11:56:36.811209'),
('abc08374-6a8a-4673-b499-97701da5f994', 'ernesto.pirandello.cliente@gmail.com', 'Ernesto', 'Pirandello', '2', '2025-01-21 10:09:11.425208'),
('ade3a802-73fa-49d5-bc32-43a5ff6c8ffe', 'ginevra.gentili.cliente@gmail.com', 'Ginevra', 'Gentili', '3', '2025-01-21 19:10:54.605433'),
('b12dc4dc-ab41-4eea-ab34-21be14e2efa6', 'erika.vico.cliente@gmail.com', 'Erika', 'Vico', '2', '2025-01-15 10:15:43.517558'),
('b310bb24-af3d-44d5-8e2d-e3e6a5c7300c', 'lorenzo.palma.cliente@gmail.com', 'Lorenzo', 'Palma', '2', '2025-01-21 10:13:07.916813'),
('b3fa1bdc-3cf5-4614-adb8-951b960f234e', 'danilo.mostra.cliente@gmail.com', 'Danilo', 'Mostra', '1', '2025-01-22 12:03:16.190898'),
('b42ad8f4-da92-4171-8be8-5da71880284d', 'muzan.kibutsujicliente@gmail.com', 'Muzan', 'Kibutsuji', '2', '2025-01-12 16:37:04.684046'),
('b4a1cdaf-3817-44ed-bfad-c47faefa98af', 'maria.rossi.cliente@gmail.com', 'Maria', 'Rossi', '2', '2025-01-20 15:31:36.158376'),
('b4fd5bf4-8f85-4816-9763-bf6f668447e9', 'mikasa.akerman.cliente@gmail.com', 'Mikasa', 'Ackerman', '2', '2025-01-06 20:43:42.669948'),
('b7176b16-9fe1-4726-a374-092ba2aaea6f', 'silvio.pino.cliente@gmail.com', 'Silvio', 'Pino', '8', '2025-01-15 17:54:35.832487'),
('b844d18a-ae15-46c6-90e8-ff967dc95495', 'nicola.tassa.cliente@gmail.com', 'Nicola', 'Tassa', '1', '2025-01-21 11:48:56.916750'),
('b86898d4-2ab4-4451-bb2e-b4af2e124716', 'fiona.liba.cliente@gmail.com', 'Fiona', 'Liba', '6', '2025-01-21 11:27:04.547690'),
('b88231ea-cd9c-44af-89c7-f040a5802c6e', 'ilario.giusto.cliente@gmail.com', 'Ilario', 'Giusto', '2', '2025-01-21 23:19:07.029194'),
('b8f2bce4-660b-431d-a970-d65ed60dfd7c', 'pietro.sola.cliente@gmail.com', 'Pietro', 'Sola', '6', '2025-01-22 21:21:32.348318'),
('b97f2253-840b-497b-acf7-add9cd348f1a', 'ugo.busto.cliente@gmail.com', 'Ugo', 'Busto', '1', '2025-01-21 11:52:54.807928'),
('bb042605-1667-4653-a9af-2b2c0f106f4b', 'loredana.saga.cliente@gmail.com', 'Loredana', 'Saga', '3', '2025-01-21 11:05:06.533455'),
('bb7964f9-6476-45dd-91d1-9cf52fda57de', 'fiorello.minuto.cliente@gmail.com', 'Fiorello', 'Minuto', '1', '2025-01-21 11:05:27.804389'),
('bb86270b-3866-4e47-a692-d3ad64d8f0d2', 'daniele.isola.cliente@gmail.com', 'Daniele', 'Isola', '2', '2025-01-21 11:51:50.845594'),
('bc4ac247-e987-4fbe-b728-450c429ab01f', 'iris.villa.cliente@gmail.com', 'Iris', 'Villa', '7', '2025-01-20 22:35:51.491737'),
('bdda3760-0102-45b6-89e3-76300a235232', 'elisa.true.crime.cliente@gmail.com', 'Elisa', 'True Crime', '5', '2025-01-12 13:18:16.333057'),
('bf0bba10-f150-4ce8-9f40-7c867f71c765', 'marco.macario.cliente@gmail.com', 'Marco', 'Macario', '2', '2025-01-21 19:11:54.131125'),
('c02741d3-d652-412f-90de-9b1247195ac5', 'dario.moccia.cliente@gmail.com', 'Dario', 'Moccia', '1', '2025-01-21 10:10:53.077120'),
('c0fc98a1-1b13-4aec-8ae4-8606c964529e', 'dominic.postolov.cliente@gmail.com', 'Dominic', 'Postolov', '1', '2025-01-21 14:10:25.123476'),
('c27bdf59-b4a0-4bde-80fd-061589182cbe', 'elisa.pozzo.cliente@gmail.com', 'Elisa', 'Pozzo', '4', '2025-01-15 10:39:52.140922'),
('c2adf1b7-13d1-4b7d-a812-7490c268d603', 'andrea.siberia.cliente@gmail.com', 'Andrea', 'Siberia', '2', '2025-01-21 10:32:57.665310'),
('c3d8b72c-df40-4c81-bf39-81b3ffbf2688', 'damiano.bellini.cliente@gmail.com', 'Damiano', 'Bellini', '2', '2025-01-14 16:35:33.216052'),
('c3e35b8d-ace8-4f3f-9b8a-1618a98eb44e', 'luca.rizzo.cliente@gmail.com', 'Luca', 'Rizzo', '6', '2025-01-21 10:06:43.371751'),
('c43d2654-e930-4f74-b71c-9aaeb99d18c6', 'gianni.bruno.cliente@gmail.com', 'Gianni', 'Bruno', '4', '2025-01-21 11:06:51.826287'),
('c57cbcd8-977c-412c-838c-a0c2b59e7383', 'enrico.manica.cliente@gmail.com', 'Enrico', 'Manica', '3', '2025-01-21 10:23:08.687921'),
('c85568fd-ba12-437c-9772-4854c7734a24', 'rosa.rimini.cliente@gmail.com', 'Rosa', 'Rimini', '6', '2025-01-20 15:13:38.316157'),
('c9ef0dbf-39af-4288-bb19-8bdc72bbf556', 'carla.gallo.cliente@gmail.com', 'Carla', 'Gallo', '1', '2025-01-21 11:33:55.547001'),
('cad17271-fdd2-46d4-ba62-b8ddbebc9810', 'rosario.pittelli.cliente@gmail.com', 'Rosario', 'Pittelli', '2', '2025-01-21 16:03:26.740650'),
('cbb901a0-5325-44c9-b2b4-94d94172946f', 'ronda.franco.cliente@gmail.com', 'Ronda', 'Franco', '5', '2025-01-15 09:53:07.754310'),
('cbed07f0-ddae-4f05-8f0e-5a91c18f8263', 'roberta.rossi.cliente@gmail.com', 'Roberta', 'Rossi', '1', '2025-01-21 11:05:44.875026'),
('cde54fa4-35b4-4093-a028-ebe77e176d24', 'francesca.faricelli.cliente@gmail.com', 'Francesca', 'Faricelli', '5', '2025-01-21 11:25:38.120764'),
('ce8ac215-0a77-4c20-b965-dd165d04ffab', 'serafino.nostromo.cliente@gmail.com', 'Serafino', 'Nostromo', '4', '2025-01-14 16:46:13.423507'),
('d209cde0-9708-4529-8c33-36274ea29cbc', 'franco.lindo.cliente@gmail.com', 'Franco', 'Lindo', '3', '2025-01-22 11:38:29.607726'),
('d22bf4d1-a0e3-4562-8773-24e522ebdcc8', 'giovanna.arpa.cliente@gmail.com', 'Giovanna', 'Arpa', '1', '2025-01-21 00:19:13.454582'),
('d28b13e8-2751-4724-b7fd-7dc9e87855ad', 'bianca.mola.cliente@gmail.com', 'Bianca', 'Mola', '1', '2025-01-22 15:54:35.028731'),
('d2e0be53-3ba4-4a81-b864-bc7722ca7805', 'simone.fontana.cliente@gmail.com', 'Simone', 'Fontana', '2', '2025-01-20 23:21:28.171890'),
('d5396f39-54a3-45d9-9155-4c94938c7591', 'elias.bianchi.cliente@gmail.com', 'Elias', 'Bianchi', '6', '2025-01-14 16:58:23.385492'),
('d8206bc2-3bc1-4c2d-bbfb-c51e84b2fb2d', 'sara.russo.cliente@gmail.com', 'Sara', 'Russo', '5', '2025-01-22 21:18:46.948083'),
('d92d621d-41b4-4b0b-8d88-93b1fb688061', 'benedetto.coro.cliente@gmail.com', 'Benedetto', 'Coro', '1', '2025-01-20 23:32:12.042567'),
('d9a43d5a-57f2-43d3-a8b7-071d1813468d', 'simona.venti.cliente@gmail.com', 'Simona', 'Venti', '6', '2025-01-21 00:26:57.479757'),
('db13b8df-9c89-4cc7-beac-98fd91d4e5c9', 'anna.lucca.cliente@gmail.com', 'Anna', 'Lucca', '6', '2025-01-21 10:07:29.147958'),
('dcaaa24f-5862-4a8e-bb45-72ee4b7e89bd', 'camilla.rodriguez.cliente@gmail.com', 'Camilla', 'Rodriguez', '3', '2025-01-16 22:47:07.279730'),
('dcf7efa5-3584-4548-bf7e-7ce339b2c7d5', 'camillo.palmieri.cliente@gmail.com', 'Camillo', 'Palmieri', '2', '2025-01-21 10:09:29.424936'),
('df24a279-0c9d-4b0a-994e-4d08999bffa2', 'marilena.ferrari.cliente@gmail.com', 'Marilena', 'Ferrari', '1', '2025-01-21 11:34:10.753014'),
('e0bbe4bb-0efc-42a5-a842-58cbd64261b6', 'erica.alaska.cliente@gmail.com', 'Erica', 'Alaska', '4', '2025-01-21 00:02:59.124568'),
('e37be95d-ae3a-4227-b459-eb155720b36a', 'alessia.rosa.cliente@gmail.com', 'Alessia', 'Rosa', '1', '2025-01-21 11:19:35.018420'),
('e62e1c95-2eba-4283-8fd6-46286fbec560', 'terese.lombardi.cliente@gmail.com', 'Teresa', 'Lombardi', '1', '2025-01-20 23:21:50.857019'),
('e765ca06-4f4f-44dc-96f9-d95239455fad', 'bruno.brunetti.cliente@gmail.com', 'Bruno', 'Brunetti', '1', '2025-01-21 11:55:57.198813'),
('e7e2927e-50bd-41f9-95a2-bcd4ad694f73', 'claudio.corapi.cliente@gmail.com', 'Claudio', 'Corapi', '2', '2025-01-20 22:39:24.312878'),
('e95d8c0d-2c7a-41dc-8d62-4d31d39a0862', 'tommaso.barbieri.cliente@gmail.com', 'Tommaso', 'Barbieri', '2', '2025-01-20 20:36:57.385441'),
('ebde92a0-7d8c-4828-a509-f9f5866ecf8c', 'orlando.mancini.cliente@gmail.com', 'Orlando', 'Mancini', '4', '2025-01-20 22:49:27.810366'),
('ec03bb89-d5c6-4b11-a5f1-a5014f096236', 'ryota.miyagi.cliente@gmail.com', 'Ryota', 'Miyagi', '3', '2025-01-06 20:06:49.890422'),
('edc12667-268b-451d-ad8d-467664143c8d', 'carmen.pisa.cliente@gmail.com', 'Carmen', 'Pisa', '1', '2025-01-21 16:37:37.281709'),
('eed693c0-2977-49f2-a13b-ed6f620153c4', 'giacomo.gloria.cliente@gmail.com', 'Giacomo', 'Gloria', '4', '2025-01-15 10:24:12.955509'),
('eef74eeb-52c8-4d28-ac2b-957c255301a6', 'giorgia.pons.cliente@gmail.com', 'Giorgia', 'Pons', '2', '2025-01-21 12:02:21.205290'),
('ef412711-a2cf-4bc8-9821-afa52eaccc53', 'remolo.roma.cliente@gmail.com', 'Remolo', 'Roma', '1', '2025-01-21 10:23:36.338464'),
('ef5069b2-a242-46d6-aca5-174776137546', 'renata.ramo.cliente@gmail.com', 'Renata', 'Ramo', '1', '2025-01-22 21:01:02.102312'),
('f15cd46c-a341-45f8-82e9-8ccc53927e04', 'alessandra.furlan.cliente@gmail.com', 'Alessandra', 'Furlan', '3', '2025-01-21 19:04:34.156469'),
('f19d96a6-444b-4212-a439-9b38a189da12', 'alessandra.pedrotto@edu.itspiemonte.it', 'Sara', 'Pedrotto', '5', '2025-01-20 22:55:42.550609'),
('f1fef6c8-666d-45db-9e4a-b4d818eb0f3b', 'teresa.maria.cliente@gmail.com', 'Teresa', 'Maria', '1', '2025-01-21 16:33:30.058227'),
('f2fcf93c-24d6-42d1-be82-d8264f74f63f', 'vanda.visione.cliente@gmail.com', 'Vanda', 'Visione', '4', '2025-01-21 00:23:47.238384'),
('f48b0b25-50ef-48db-a2c7-f030aa5a1f79', 'isabella.nova.cliente@gmail.com', 'Isabella', 'Nova', '1', '2025-01-20 23:57:59.700171'),
('f5583bd1-07d5-44d1-9b53-ed2cbeb373d5', 'tanjiro.kamado.cliente@gmail.com', 'Tanjiro', 'Kamado', '4', '2025-01-12 16:34:00.428755'),
('f5f4afd9-e670-4600-8290-e05b2037b93d', 'vanessa.fiore.cliente@gmail.com', 'Vanessa', 'Fiore', '4', '2025-01-22 21:16:57.160085'),
('f83c238c-9f19-4806-97bc-c56d17d92c1c', 'giada.vera.cliente@gmail.com', 'Giada', 'Vera', '1', '2025-01-21 12:04:50.557171'),
('faaadfa4-c3fc-4a8f-ac54-d736cf5b2e89', 'annalisa.gala.cliente@gmail.com', 'Annalisa', 'Gala', '3', '2025-01-18 10:35:00.484211'),
('fc4fd4f6-ef30-4ddc-9f80-60e77bdfd2bf', 'paolo.rumiana.cliente@gmail.com', 'Paolo', 'Rumiana', '2', '2025-01-22 15:54:52.235293'),
('fcee204d-24ed-4d38-b729-ccfa20f3b1cc', 'levi.ackerman.cliente@gmail.com', 'Levi', 'Ackerman', '7', '2025-01-06 20:57:14.769935');

-- --------------------------------------------------------

--
-- Struttura della tabella `information_form`
--

CREATE TABLE `information_form` (
  `id` varchar(255) NOT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `generalform_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `general_form_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `information_form`
--

INSERT INTO `information_form` (`id`, `content`, `generalform_id`, `user_id`, `status`, `general_form_id`) VALUES
('010f9d3a-1756-48fe-a490-1d5f449a6e08', 'Quali attività offrite per sensibilizzare sul tema della mafia?', '21830915-a3fb-4063-8108-0925552dfb14', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'DONE', NULL),
('0234d0a3-606e-4489-afcb-3a5073eea002', 'È possibile organizzare soggiorni presso la cascina per gruppi scolastici?', '11b2c7e2-5faf-4225-97a9-8a26be2399be', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'IN PROGRESS', NULL),
('04381d3d-4e81-4661-907e-d901b5fe3bed', 'Quali sono i costi e i dettagli organizzativi per questo tipo di esperienza?', 'f15cd46c-a341-45f8-82e9-8ccc53927e04', 'dccf76d3-1a94-4076-88d3-e3d447441d30', 'TO DO', NULL),
('056c864e-e225-443d-a8bb-ebef7b222c85', 'È previsto un programma specifico per le scuole?', '30ea7992-544e-4122-832d-97a9ee84da2e', NULL, 'DONE', NULL),
('07483072-22de-42b0-aff4-613e619fe44c', 'Mi piacerebbe capire se offrite esperienze dedicate alle scuole, e se è possibile anche dormire da voi. Ci sono programmi o attività specifiche per ragazzi?', '7f553f62-0c55-41c4-ad20-9001e91db78c', NULL, 'IN PROGRESS', NULL),
('09e8430d-b8e6-4d51-b14e-1aa05268e0cb', 'È possibile soggiornare presso la cascina con gli studenti?', '5a0f6a3f-f509-4ab7-b6af-e4bcf00881e8', NULL, 'TO DO', NULL),
('0a42a67e-f53c-4772-88ec-339c00ed041b', 'Quali sono i periodi disponibili e le modalità di prenotazione?', '956912fb-1053-4876-b0a1-e625d2f39e85', NULL, 'DONE', NULL),
('0b562913-8b1a-4924-876a-ab41c2234399', 'Vorremmo conoscere i dettagli riguardanti:\r\n\r\n- Le attività proposte per i gruppi scolastici.\r\n- La disponibilità di strutture per il pernottamento.', '80b516cc-1fd8-4dfe-90af-517243c6c793', NULL, 'IN PROGRESS', NULL),
('0cdc8540-39a4-4886-ac0e-8766f9a0c6f4', 'Ci sono periodi consigliati per organizzare visite e attività educative?', '27e1aaac-e7f3-4758-875e-5f26d1ee9d06', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'TO DO', NULL),
('0d9dd9d7-83a3-48eb-93cf-d5703806665f', 'Vorrei sapere se offrite la possibilità di ospitare eventi privati e quali sono le condizioni.', 'bb7964f9-6476-45dd-91d1-9cf52fda57de', NULL, 'DONE', NULL),
('113c9b39-b736-4e8e-8cb3-cbc41ca11a02', 'Quali tipi di eventi privati potete ospitare (es. matrimoni, feste aziendali, anniversari, ecc.)?', 'fc4fd4f6-ef30-4ddc-9f80-60e77bdfd2bf', NULL, 'IN PROGRESS', NULL),
('14bd38c0-cc71-498d-9273-b8b2efd55c00', 'È possibile organizzare eventi in concomitanza con le vostre attività di sensibilizzazione sulla mafia?', '73c6da8e-47c6-47a8-9438-2a510ca78b09', NULL, 'TO DO', NULL),
('15a8bf00-07af-4cb4-b4b7-6f2808474dd7', 'Quali sono i costi per l’affitto della struttura e i servizi inclusi?', '9c9bae61-21db-49b2-9e9c-af0a8effc0aa', NULL, 'DONE', NULL),
('184018a3-abcd-48ac-9bc4-5ec7ec70425e', 'Quali sono le modalità di prenotazione e la disponibilità?', 'c27bdf59-b4a0-4bde-80fd-061589182cbe', NULL, 'IN PROGRESS', NULL),
('18403912-b269-4022-a679-12921ccc3e22', 'Vorrei chiedere:\r\n\r\n- Offrite spazi per eventi privati come matrimoni, cene, feste aziendali o altri eventi simili?\r\n- Quali sono le opzioni per un eventuale soggiorno per gli ospiti che partecipano all\'evento?\r\n- Potremmo anche abbinare l\'evento a una visita o attività di sensibilizzazione sulla mafia?\r\n- Come funziona la prenotazione e quali sono i costi?', '0499637b-8f00-47cb-a1e1-3fa7bbb47471', NULL, 'TO DO', NULL),
('1904cd18-218b-4adf-9ed1-bfd9a8efdebc', 'Quali tipi di eventi privati accogliete?', '07775b70-75b6-4936-8a9a-d4d283ddcac6', 'd058b959-86a6-4a90-afa3-81f5704d334a', 'DONE', NULL),
('1d0af41d-4ed0-43e9-a9e8-3907194950fd', 'Offrite pacchetti che combinano eventi privati con attività tematiche per sensibilizzare sulla mafia?', '59dc61da-3783-405c-8132-0f8e3be41ea6', NULL, 'IN PROGRESS', NULL),
('1ddbce0a-4124-4942-8d04-ea0c86eb6609', 'Potreste indicarmi i costi e i servizi inclusi (come catering, sistemazione, ecc.)?', 'abc08374-6a8a-4673-b499-97701da5f994', NULL, 'TO DO', NULL),
('21d2d748-2e37-4ad0-90e4-b123190247c3', 'Quali spazi sono disponibili per questi eventi?', '7a51f110-d2e9-474c-8af8-494653e06674', NULL, 'DONE', NULL),
('2581e76e-ae53-43a0-b891-458249c9e99c', 'Quali sono i costi e i servizi inclusi nel pacchetto per eventi privati?', 'b88231ea-cd9c-44af-89c7-f040a5802c6e', 'dccf76d3-1a94-4076-88d3-e3d447441d30', 'IN PROGRESS', NULL),
('27bcdb99-1609-4dcd-b2e6-4c4bed3cff0f', 'Ospitate eventi privati come matrimoni, feste o eventi aziendali?', '1c49cc4a-b73f-4758-9ea0-a447958ce613', NULL, 'TO DO', NULL),
('2880a8a7-e1f6-4e65-90af-ddfb1f9d156b', 'Come funziona la prenotazione per un evento privato e quali sono le tariffe?', 'b86898d4-2ab4-4451-bb2e-b4af2e124716', NULL, 'DONE', NULL),
('2893637d-09a4-443e-bf7e-1fbc3ba71c4c', 'Offrite pacchetti che includono anche il pernottamento per gli ospiti?', 'c02741d3-d652-412f-90de-9b1247195ac5', NULL, 'IN PROGRESS', NULL),
('28defea7-ec8b-4f6a-9c75-d786b4d3272c', 'Quali spazi sono disponibili per eventi di grandi dimensioni e per il pubblico?', 'a7b1f1b7-94da-4d37-92cc-fb995af37325', NULL, 'TO DO', NULL),
('29fb68e1-fb57-4fd6-baed-f09b3f894d4f', 'Qual è la disponibilità della struttura per i prossimi mesi e come funziona la prenotazione?', '0ebf9822-fd99-46da-9300-74b52563b68c', NULL, 'DONE', NULL),
('2ace5561-f46f-4060-a18e-4b8212e8c167', 'Potreste fornirci un preventivo indicativo per l\'affitto della struttura e per i servizi inclusi (ad esempio, assistenza logistica, attrezzature, ecc.)?', 'a21d9e85-d2ab-4970-8503-353c83066046', NULL, 'IN PROGRESS', NULL),
('2c8583bc-e3da-4785-a3f6-a0ab6362ad55', 'Sono disponibili parcheggi e strutture di accesso per il pubblico e i disabili?', '84af1193-2c82-43e3-b41b-ecccabc553bc', NULL, 'TO DO', NULL),
('2da20436-43db-4d36-bed3-c74b9e3cf025', 'La cascina dispone di servizi di ristorazione o possiamo prevedere un catering esterno?', '7e893f3e-d9eb-47ae-b0c3-26f9855a1306', NULL, 'DONE', NULL),
('300ba8ce-09e9-4b4b-bdaa-e2fbd2bf4a2f', 'Quali permessi o autorizzazioni sono necessari per l’organizzazione di eventi pubblici presso la vostra struttura?', '9cc8891f-ad98-404a-97d5-d06976a9366a', NULL, 'IN PROGRESS', NULL),
('35a87298-fa0b-4391-8689-d624f2f55fd5', 'Quali spazi all’interno della cascina sono adatti per eventi pubblici e conferenze?', '6bdc7d06-7e4a-4afe-936c-56d98e62d696', NULL, 'TO DO', NULL),
('38b32b26-d36f-49e0-8927-114f7c3dbfa6', 'Avete esperienze pregresse nell’organizzazione di eventi di sensibilizzazione, soprattutto legati al tema della mafia?\r\n', 'f48b0b25-50ef-48db-a2c7-f030aa5a1f79', NULL, 'DONE', NULL),
('3b3b4045-732b-4e3a-a5ee-bd4ca9ca7a6e', 'Offrite pacchetti che comprendano sia l’affitto degli spazi che i servizi di supporto logistico (es. audio, video, allestimenti)?', '228e5a77-0ba1-47ac-bc9e-d65f860a715a', NULL, 'IN PROGRESS', NULL),
('3ce7e511-605a-4a5a-ab6a-fe034ecba516', 'Come gestite l’afflusso di pubblico e quali misure sono adottate per la sicurezza durante eventi di grandi dimensioni?', 'f1fef6c8-666d-45db-9e4a-b4d818eb0f3b', NULL, 'TO DO', NULL),
('3f3ffb6c-e06a-43b6-b11b-2f8da25508cf', 'La struttura è accessibile a persone con disabilità?', 'e37be95d-ae3a-4227-b459-eb155720b36a', NULL, 'DONE', NULL),
('40ea9185-b5d3-454c-a112-6028e0ae413d', 'Quali sono le modalità di pagamento e le condizioni per la prenotazione?', '2ab95d62-2d97-4ff3-baba-0d0c2d98e0c1', NULL, 'IN PROGRESS', NULL),
('4219bf0c-fd2f-480f-bacb-1edea6940838', 'Qual è la capienza massima per un evento pubblico nella vostra struttura?', 'ade3a802-73fa-49d5-bc32-43a5ff6c8ffe', 'dccf76d3-1a94-4076-88d3-e3d447441d30', 'TO DO', NULL),
('44aacb0c-44b4-4ffb-ab72-9d3dd1a8ceba', 'Esistono specifici regolamenti da seguire per ospitare eventi pubblici nella vostra cascina?', '4c8f907e-4d1f-4c43-aa34-a18e7f226014', NULL, 'DONE', NULL),
('44e2cf07-c056-4ac3-93d0-7e7b597034e9', 'Do you offer tailored educational programs for foreign groups or individuals looking to work with local students?', 'c3d8b72c-df40-4c81-bf39-81b3ffbf2688', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'IN PROGRESS', NULL),
('465f4167-8902-4f5f-bf06-1a181c73142a', 'What kind of activities can be organized for students? Are there options for interactive workshops or lectures?', '352ea773-a1d1-485e-9a63-9db30560302e', NULL, 'TO DO', NULL),
('4950d911-cf66-40c0-aba7-dd6592c40053', 'Is it possible to arrange for our group to collaborate with local schools or universities during our stay?', '63e4d857-a318-4d13-b0ae-0965b29a27c3', NULL, 'DONE', NULL),
('4a232a1a-d528-4622-8fd5-8534fdf80b0e', 'What is the procedure for organizing such programs and what are the logistical details (e.g., transportation, accommodation)?', '498b4192-6d0f-40b6-87ce-36e257401042', NULL, 'IN PROGRESS', NULL),
('4c08e4e4-95a1-4906-9c6f-538d4b8549ac', 'Do you offer any translation or language support for foreign participants?', '131cac74-0cac-4167-a90f-c9f10532c4a4', NULL, 'TO DO', NULL),
('4c3309ad-8888-450c-a5f9-202416aa3a84', 'What is the typical duration of these educational activities?', 'ce8ac215-0a77-4c20-b965-dd165d04ffab', NULL, 'DONE', NULL),
('51048621-e3b2-4f76-8eb3-b94376a59ef8', 'What types of educational activities do you offer for foreign groups or organizations?', 'b310bb24-af3d-44d5-8e2d-e3e6a5c7300c', NULL, 'IN PROGRESS', NULL),
('524299cc-5e4b-43dd-ad45-ee31eeed543d', 'What is the process for arranging a program for a foreign group?', '2d528322-baf7-4086-850d-df72e437c7d7', NULL, 'TO DO', NULL),
('5495164b-4880-4c86-b7fa-2240f7475104', 'What are the accommodation options for foreign groups, and what are the costs involved?', '580819eb-03d0-487f-b56f-70ac0b9d7c8e', NULL, 'DONE', NULL),
('561eb4f9-eb05-4814-bff3-0fa72d4e8cf2', 'Sarebbe possibile organizzare una gita per 2 classi da 20 alunni e 2 insegnanti cadauna? ', 'b42ad8f4-da92-4171-8be8-5da71880284d', 'dccf76d3-1a94-4076-88d3-e3d447441d30', 'IN PROGRESS', NULL),
('59b876c8-bb91-468f-bdf6-06d4bbc2b182', 'Can we collaborate with local schools for joint educational sessions or workshops on mafia awareness?', '807c54d4-8aa4-48b0-8b84-c1e114bc346f', NULL, 'TO DO', NULL),
('59e12251-67b6-4c8e-a486-a98264448ba7', 'Can you help arrange transportation and accommodation for our group during our stay?', '89a7f9eb-ac65-48c5-913a-3dbcb75a0d3d', NULL, 'DONE', NULL),
('5c48582b-a827-4fd1-ad03-9a7511c92a1c', 'What are the costs for hosting foreign groups and providing educational materials or activities?', '3342248d-e47b-4ee8-a398-5875a083cc90', NULL, 'IN PROGRESS', NULL),
('5f964db3-7d41-49ca-a910-686a916c2d54', 'Che tipo di attività possono svolgere i gruppi scout presso la vostra cascina?', 'ebde92a0-7d8c-4828-a509-f9f5866ecf8c', NULL, 'TO DO', NULL),
('6060a1b3-bd67-4fec-b826-50c28d433222', 'La struttura è adatta per accogliere gruppi di scout, inclusi spazi per attività all\'aperto e per il pernottamento?', 'bc4ac247-e987-4fbe-b728-450c429ab01f', NULL, 'DONE', NULL),
('613954ce-8968-4104-836b-5a6b3224a4fb', 'Offrite pacchetti che includano attività educative, pasti e pernottamento per i gruppi?', 'e62e1c95-2eba-4283-8fd6-46286fbec560', NULL, 'IN PROGRESS', NULL),
('6236e154-41ea-4a86-acbc-6c4ad2bd58dd', 'Quali attività outdoor organizzate che possano essere adatte per un gruppo scout?', 'd2e0be53-3ba4-4a81-b864-bc7722ca7805', NULL, 'TO DO', NULL),
('66d8d7ba-8f30-4a64-8ee9-9e76e876cc51', 'La struttura è attrezzata per ospitare gruppi numerosi, sia per le attività che per il pernottamento?', 'e95d8c0d-2c7a-41dc-8d62-4d31d39a0862', NULL, 'DONE', NULL),
('67a9ada0-5ab5-477e-8600-3195b8420318', 'È possibile organizzare una visita guidata della cascina e delle sue attività, combinandola con una sessione educativa?', '87b5893a-3267-4d50-a49f-e853dbfd75eb', NULL, 'IN PROGRESS', NULL),
('6806b72b-eb36-4e19-8134-ae94e012ab17', 'Quali sono i costi per l\'affitto della struttura e per l\'organizzazione delle attività?', '2d8f7311-5dc7-49c3-8722-becead8a6404', NULL, 'TO DO', NULL),
('690fe370-4f97-4876-a3c2-ac6884478055', 'Offrite anche attività ludiche o esperienze immersive che possano rafforzare il messaggio educativo?', '524a73b8-d6f4-46f0-8290-acb0d1e7ed42', NULL, 'DONE', NULL),
('6a875f11-0cef-4094-bec0-d14d4561faa1', 'È possibile organizzare attività di gruppo che combinano l’educazione alla legalità e l’esplorazione della natura circostante?', 'a1c8b330-cef9-4b9c-b2a8-60cd221b4c55', NULL, 'IN PROGRESS', NULL),
('6bfaa397-eb94-426e-836a-0c6f6e43994d', 'Qual è il costo medio per un weekend di attività educativo per un gruppo di scout?', 'c3e35b8d-ace8-4f3f-9b8a-1618a98eb44e', NULL, 'TO DO', NULL),
('6d72af62-13db-4319-941e-6b424a9ab59c', 'La struttura è facilmente raggiungibile con i mezzi pubblici o offrite soluzioni di trasporto?', '78e482fe-3ade-43fd-96e0-dbefed4f7a8d', NULL, 'DONE', NULL),
('6f94da68-adad-4710-8117-279a4fe4d566', 'Stiamo conducendo una ricerca accademica sul tema della mafia e dei beni confiscati. Vorremmo chiedere se è possibile intervistare rappresentanti della vostra associazione per raccogliere informazioni sul processo di recupero e gestione dei beni confiscati.', '75fb05b8-84af-4257-b79e-e0e840ae8d2c', NULL, 'IN PROGRESS', NULL),
('73dfdf37-9c4a-442a-95b0-4255099006d8', 'È possibile celebrare un matrimonio civile o simbolico presso la vostra struttura?', 'c9ef0dbf-39af-4288-bb19-8bdc72bbf556', NULL, 'TO DO', NULL),
('75a536c0-216d-476f-8df3-b780f2baf0ea', 'Offrite pacchetti specifici per matrimoni, che includano l\'affitto della location, i servizi di catering, e altre necessità?', 'cbb901a0-5325-44c9-b2b4-94d94172946f', NULL, 'DONE', NULL),
('774a32e7-6eb5-4bf4-9bc4-0996e69f9ded', 'Quali spazi sono disponibili per la cerimonia e il ricevimento? La struttura ha una capienza sufficiente per un matrimonio di 60 persone?', 'a9b1a71f-40c6-463b-9595-084e16b3f0e7', NULL, 'IN PROGRESS', NULL),
('7adcaec4-20cd-4adb-af93-1ed9410856c3', 'Qual è la disponibilità della cascina per matrimoni durante la stagione estiva o in altri periodi dell’anno?', '2c353787-b99a-4ff5-8e7c-0b2f29996da4', NULL, 'TO DO', NULL),
('7afc2b76-ba5a-4a70-80f8-ce4c40a49cc8', 'Quali sono i costi e le condizioni per organizzare il matrimonio presso la vostra struttura?', '74a1e050-edeb-42bb-8ba8-22e1da97a159', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'DONE', NULL),
('7b67562f-10ae-4638-a2df-07805f1082b1', 'Se volessimo organizzare attività particolari, come musica dal vivo, giochi o intrattenimento, ci sono spazi adatti?', 'c43d2654-e930-4f74-b71c-9aaeb99d18c6', NULL, 'IN PROGRESS', NULL),
('7c501e26-9d5b-4a96-8d63-940e7bb7413d', 'È possibile affittare uno spazio presso la vostra struttura per ospitare un mercatino di beneficenza?', '5f740593-72d9-4de2-91f0-b64832c4333d', NULL, 'TO DO', NULL),
('82a63750-f41b-43ba-950f-f1abbd3f2eb4', 'Sono previsti costi aggiuntivi per la gestione di eventi pubblici o di beneficenza?', 'a9791400-23fd-4b60-8c64-1e03c119c8f9', NULL, 'DONE', NULL),
('82d84508-d637-4056-ae1f-045c22295a0b', 'Qual è la vostra politica riguardo alla vendita di prodotti di beneficenza? C’è un costo fisso per l’affitto dello spazio o una percentuale sui guadagni?', '0242fc49-f59b-4cf9-9bd3-c34663413328', NULL, 'IN PROGRESS', NULL),
('835ec86f-6b88-4f2f-a414-981d25076cac', 'Avete esperienze precedenti nell\'organizzare eventi di beneficenza, e sarebbe possibile coinvolgere volontari della vostra associazione?', '6f7caab3-01c6-4e4e-8499-d70940bd1a23', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'TO DO', NULL),
('844c845b-91e3-476b-97c3-fb7458adc33b', 'Quali sono i costi per l\'affitto della location, e se ci sono sconti o agevolazioni per eventi di beneficenza?', 'df24a279-0c9d-4b0a-994e-4d08999bffa2', NULL, 'DONE', NULL),
('85de6700-2087-4465-b063-30b50672edba', 'La vostra struttura dispone di parcheggi o altre facilitazioni per i visitatori?', 'd8206bc2-3bc1-4c2d-bbfb-c51e84b2fb2d', NULL, 'IN PROGRESS', NULL),
('8869a17b-a7ca-4292-ac50-3ba72f09b71d', 'Vorremmo sapere se la vostra struttura è accessibile e se sarebbe possibile organizzare attività per persone disabili presso di voi. In particolare, ci piacerebbe sapere:\r\n\r\nLa cascina è accessibile a persone con disabilità motorie? Ci sono rampe o ascensori per accedere facilmente agli spazi principali?', 'cbed07f0-ddae-4f05-8f0e-5a91c18f8263', NULL, 'TO DO', NULL),
('8d01f9dd-ec03-4e3d-9a9c-f59e4849f6e2', 'La cascina dispone di servizi di ristorazione o possiamo prevedere un catering esterno?', 'b97f2253-840b-497b-acf7-add9cd348f1a', NULL, 'TO DO', NULL),
('8d789752-bc9d-4050-b856-6d68b2f1ac1e', 'Quali permessi o autorizzazioni sono necessari per l’organizzazione di eventi pubblici presso la vostra struttura?', '55c11745-4af4-499b-870b-83aba5d476ed', NULL, 'TO DO', NULL),
('8e63f767-40bd-4b81-8617-cb5e151445fe', 'Quali spazi all’interno della cascina sono adatti per eventi pubblici e conferenze?', 'dcf7efa5-3584-4548-bf7e-7ce339b2c7d5', NULL, 'TO DO', NULL),
('8e64c331-bb95-4020-9450-1ddc097e33bf', 'Avete esperienze pregresse nell’organizzazione di eventi di sensibilizzazione, soprattutto legati al tema della mafia?', 'a79a6725-6e7a-43cd-9bd1-f6140229574c', NULL, 'TO DO', NULL),
('8f146774-11e2-46e1-8e93-0cd617e0df75', 'Offrite pacchetti che comprendano sia l’affitto degli spazi che i servizi di supporto logistico (es. audio, video, allestimenti)?', 'f5f4afd9-e670-4600-8290-e05b2037b93d', NULL, 'TO DO', NULL),
('90c7f4f2-2323-499a-9fac-da54f88f4e43', 'Come gestite l’afflusso di pubblico e quali misure sono adottate per la sicurezza durante eventi di grandi dimensioni?', 'ef5069b2-a242-46d6-aca5-174776137546', NULL, 'TO DO', NULL),
('9272ff08-3532-4def-9e97-5e1f9c6abdb9', 'La struttura è accessibile a persone con disabilità?', 'ef412711-a2cf-4bc8-9821-afa52eaccc53', NULL, 'TO DO', NULL),
('992e1240-8fb7-47b8-b757-734df7ef5391', 'Quali sono le modalità di pagamento e le condizioni per la prenotazione?', 'd5396f39-54a3-45d9-9155-4c94938c7591', NULL, 'TO DO', NULL),
('9961c09e-93b3-458b-95aa-d4bb14d09a72', 'Qual è la capienza massima per un evento pubblico nella vostra struttura?', 'b3fa1bdc-3cf5-4614-adb8-951b960f234e', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'TO DO', NULL),
('9c2ee327-7bb0-48f0-87b7-cc11de0a9cf9', 'Sarebbe possibile intervistare alcuni dipendenti per una ricerca?', 'f5583bd1-07d5-44d1-9b53-ed2cbeb373d5', '7436f7bc-f635-4a9c-8b4f-44c491dd16c4', 'IN PROGRESS', NULL),
('9cdb99d6-3772-4709-b84a-e9ef7b6230b9', 'Esistono specifici regolamenti da seguire per ospitare eventi pubblici nella vostra cascina?', '5d033811-8b19-4973-bd87-42266653d8ec', NULL, 'TO DO', NULL),
('9d54678d-a417-4eb6-8849-188c55243ed4', 'Do you offer tailored educational programs for foreign groups or individuals looking to work with local students?', '8205ae48-46f0-415b-861d-6e2b06ab61e7', NULL, 'TO DO', NULL),
('9d8cb4fc-8fc3-455b-8ba4-1f7f23384655', 'What kind of activities can be organized for students? Are there options for interactive workshops or lectures?', '840cf865-e45f-41f8-bce1-69d100bbdb00', NULL, 'TO DO', NULL),
('9d8fef85-b18b-46bb-88ef-832c5f3e252a', 'Is it possible to arrange for our group to collaborate with local schools or universities during our stay?', 'bb042605-1667-4653-a9af-2b2c0f106f4b', NULL, 'TO DO', NULL),
('9e3d1163-f237-4dbf-adbc-9dd4fe272b26', 'What is the procedure for organizing such programs and what are the logistical details (e.g., transportation, accommodation)?', 'b12dc4dc-ab41-4eea-ab34-21be14e2efa6', NULL, 'TO DO', NULL),
('a133658d-b633-41ab-a04f-72f7a0c616d9', 'Do you offer any translation or language support for foreign participants?', 'f83c238c-9f19-4806-97bc-c56d17d92c1c', NULL, 'TO DO', NULL),
('a2fbcfa3-5de6-45bf-a26c-8be4d7c0aeda', 'What is the typical duration of these educational activities?', '9f287db1-6454-4898-b0b6-8b820d837ec8', NULL, 'TO DO', NULL),
('a303b1cc-b11a-4ae0-9f40-264a0a360e60', 'What types of educational activities do you offer for foreign groups or organizations?', 'e7e2927e-50bd-41f9-95a2-bcd4ad694f73', NULL, 'TO DO', NULL),
('a4123860-89dc-4a96-8925-61726797ea65', 'What types of educational activities do you offer for foreign groups or organizations?', 'a12ac8d5-e7df-49e9-9293-df68d277362d', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'TO DO', NULL),
('a4250a4d-efc9-4779-a49c-7d049f6b1a22', 'What is the process for arranging a program for a foreign group?', '298b2a94-6041-4dd4-96db-4e8794205f65', NULL, 'IN PROGRESS', NULL),
('a490e429-8378-4196-8506-1b47337845be', 'What are the accommodation options for foreign groups, and what are the costs involved?', '5f4b7f9e-680b-4858-bd1e-9274feade2d1', NULL, 'TO DO', NULL),
('a5054bf6-507c-4f04-b98a-6a3d9a51b5f3', 'Sarebbe possibile organizzare una gita per 2 classi da 20 alunni e 2 insegnanti cadauna?', 'cde54fa4-35b4-4093-a028-ebe77e176d24', NULL, 'TO DO', NULL),
('a59d5a02-2087-458a-a245-99c65782e987', 'Can we collaborate with local schools for joint educational sessions or workshops on mafia awareness?', '3ca4e3a9-926e-4b21-9978-145ff628cc28', NULL, 'TO DO', NULL),
('a5a30c71-0860-4b9e-b563-c638ea46b078', 'Can you help arrange transportation and accommodation for our group during our stay?', '76653954-fd01-4d5c-806a-434fdf4bcb1d', 'dccf76d3-1a94-4076-88d3-e3d447441d30', 'TO DO', NULL),
('a6316d60-369b-48b6-a70a-65043311c9be', 'What are the costs for hosting foreign groups and providing educational materials or activities?', 'f2fcf93c-24d6-42d1-be82-d8264f74f63f', NULL, 'TO DO', NULL),
('a88ec355-6efa-4fe1-8714-f14bef589eb5', 'Che tipo di attività possono svolgere i gruppi scout presso la vostra cascina?', '7399e9fe-2d95-4a34-ad5e-32241aef5095', 'dccf76d3-1a94-4076-88d3-e3d447441d30', 'TO DO', NULL),
('a8fc49dc-8cf6-40d6-a57e-00ffadd55a5d', 'La struttura è adatta per accogliere gruppi di scout, inclusi spazi per attività all\'aperto e per il pernottamento?', '6391ad45-cb6f-4ecd-afed-6b5f0a2ba6c6', NULL, 'TO DO', NULL),
('aa43f416-3019-47fb-a745-bcd7f765fac6', 'Offrite pacchetti che includano attività educative, pasti e pernottamento per i gruppi?', '1bfcb592-a6d8-43d5-81ca-da17eb41599b', NULL, 'TO DO', NULL),
('ad6b5dd2-8a25-4657-a3f6-c41b6f3ab65c', 'Quali attività outdoor organizzate che possano essere adatte per un gruppo scout?', 'faaadfa4-c3fc-4a8f-ac54-d736cf5b2e89', NULL, 'TO DO', NULL),
('adf21696-15ca-4786-8275-97da6975d1ee', 'Quali laboratori o esperienze vengono proposte agli studenti durante la permanenza?', '86e707e2-dd12-4b7a-982c-a58762f6c7dd', 'dccf76d3-1a94-4076-88d3-e3d447441d30', 'DONE', NULL),
('b15eb03d-3647-486b-bb30-f5a8cce4624c', 'La struttura è attrezzata per ospitare gruppi numerosi, sia per le attività che per il pernottamento?', 'bb86270b-3866-4e47-a692-d3ad64d8f0d2', NULL, 'TO DO', NULL),
('b17db7d3-425e-4f68-8a25-62a7925494c6', 'È possibile organizzare una visita guidata della cascina e delle sue attività, combinandola con una sessione educativa?', 'a06cdaa0-1d9a-4a98-ae03-15635b1c4009', NULL, 'TO DO', NULL),
('b41843f3-1e2f-4013-88e7-93cec65a03f6', 'Quali sono i costi per l\'affitto della struttura e per l\'organizzazione delle attività?', '6b61cfc6-4943-48ff-baa8-40fc3b8c0fa1', NULL, 'TO DO', NULL),
('b424429f-5805-46cc-92d0-4bd1dbc8b252', 'Offrite anche attività ludiche o esperienze immersive che possano rafforzare il messaggio educativo?', 'bf0bba10-f150-4ce8-9f40-7c867f71c765', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'TO DO', NULL),
('b5b0fff6-d96f-47c0-a1aa-07ad745beb50', 'È possibile organizzare attività di gruppo che combinano l’educazione alla legalità e l’esplorazione della natura circostante?', '3b2db3cb-3055-4834-8420-955001aa057b', NULL, 'TO DO', NULL),
('b67076ed-da25-4272-9c53-0b50b79a742f', 'Offrite pacchetti specifici per matrimoni, che includano l\'affitto della location, i servizi di catering, e altre necessità?', 'd92d621d-41b4-4b0b-8d88-93b1fb688061', NULL, 'TO DO', NULL),
('b7e4302e-e35a-4957-aa8d-e541deab0460', 'Come si integra questa cascina con il territorio circostante?', 'ec03bb89-d5c6-4b11-a5f1-a5014f096236', NULL, 'TO DO', NULL),
('b95fbc6b-574f-44bd-83e9-2d824a1b8917', 'Quali spazi sono disponibili per la cerimonia e il ricevimento? La struttura ha una capienza sufficiente per un matrimonio di 60 persone?', '281ea734-c1e2-40a2-8f66-3cb951ac75de', NULL, 'TO DO', NULL),
('be035043-93bb-4219-b200-db9074d80b7e', 'Qual è la disponibilità della cascina per matrimoni durante la stagione estiva o in altri periodi dell’anno?', 'd9a43d5a-57f2-43d3-a8b7-071d1813468d', NULL, 'TO DO', NULL),
('bfa88687-cfc2-4771-b089-f9701ef0a600', 'Quali sono i costi e le condizioni per organizzare il matrimonio presso la vostra struttura?', '2f74e388-c55c-4aca-9f67-b33caeb43a80', NULL, 'TO DO', NULL),
('c05cab3e-152e-4943-a241-f5f50c2545f8', 'Se volessimo organizzare attività particolari, come musica dal vivo, giochi o intrattenimento, ci sono spazi adatti?', 'cad17271-fdd2-46d4-ba62-b8ddbebc9810', NULL, 'TO DO', NULL),
('c0a51a64-2a81-46b3-97aa-aa54b52ddee4', 'È possibile affittare uno spazio presso la vostra struttura per ospitare un mercatino di beneficenza?', '7c8ea4e3-79f0-402a-8b86-a1eec5855726', NULL, 'TO DO', NULL),
('c1fd0b0f-05b9-4646-b01b-3d9b6273d359', 'Le attività cosa prevedono?', 'a4bf66ef-b02a-4fa6-b788-f119495e85e1', NULL, 'TO DO', NULL),
('c26677c0-85f1-4317-96e1-d16520426535', 'È possibile organizzare soggiorni presso la cascina per gruppi scolastici?', '36dd87eb-7f8c-42d2-a90b-63cdd9bb9500', NULL, 'TO DO', NULL),
('c2b8d1dd-3262-418d-aba6-3ff7ca4d9eaa', 'Quali attività offrite per sensibilizzare sul tema della mafia?', '98827656-3bec-41c4-beaa-0a03f4e4d048', NULL, 'TO DO', NULL),
('c3cd221f-35cb-43d7-ba5b-bfd59c279e99', 'Qual è la vostra politica riguardo alla vendita di prodotti di beneficenza? C’è un costo fisso per l’affitto dello spazio o una percentuale sui guadagni?', 'eef74eeb-52c8-4d28-ac2b-957c255301a6', NULL, 'TO DO', NULL),
('c5b07df9-a6ee-4905-b891-196b7b46104e', 'Avete esperienze precedenti nell\'organizzare eventi di beneficenza, e sarebbe possibile coinvolgere volontari della vostra associazione?', '1f646219-1760-48ea-9c8c-e618ea6ff0ac', NULL, 'TO DO', NULL),
('c6b3552a-0d9b-4993-8850-dce9b7cf78b4', 'Quali sono i costi per l\'affitto della location, e se ci sono sconti o agevolazioni per eventi di beneficenza?', 'eed693c0-2977-49f2-a13b-ed6f620153c4', NULL, 'TO DO', NULL),
('c70b0e2e-41fc-43ea-a5a7-c10f0bcd4614', 'La vostra struttura dispone di parcheggi o altre facilitazioni per i visitatori?', '2271a3f4-71f7-4103-aadd-c43faf02892a', NULL, 'TO DO', NULL),
('c837427b-e191-4e84-b481-5f493c1f1c16', 'Vorremmo sapere se la vostra struttura è accessibile e se sarebbe possibile organizzare attività per persone disabili presso di voi. In particolare, ci piacerebbe sapere:\r\n\r\nLa cascina è accessibile a persone con disabilità motorie? Ci sono rampe o ascensori per accedere facilmente agli spazi principali?', 'abbced08-22c9-4ef4-8067-ce159ad4c0d3', NULL, 'TO DO', NULL),
('c95cf2dc-af2c-4ad4-b728-c3baa913b267', 'Mi piacerebbe capire se offrite esperienze dedicate alle scuole, e se è possibile anche dormire da voi. Ci sono programmi o attività specifiche per ragazzi?', '0f076167-a6fa-4c08-b2db-896e435c660d', NULL, 'TO DO', NULL),
('c9906d9c-ecaa-467e-aab7-a5b73d6d0d42', 'È possibile soggiornare presso la cascina con gli studenti?', '25007eb1-303c-4f15-b37d-e49b8628629b', NULL, 'TO DO', NULL),
('c9a3addb-1822-488d-b1f7-965582d2d7df', 'Quali sono i periodi disponibili e le modalità di prenotazione?', 'b844d18a-ae15-46c6-90e8-ff967dc95495', NULL, 'TO DO', NULL),
('ca636519-7217-4e83-9276-9c7f18ed0e7b', 'Vorremmo conoscere i dettagli riguardanti:\r\n\r\n- Le attività proposte per i gruppi scolastici.\r\n- La disponibilità di strutture per il pernottamento.', '8c2ff0be-0e57-45fa-890a-981afa05a477', NULL, 'TO DO', NULL),
('ccbae5d3-8e9b-4ce9-b8dc-1cb06851e231', 'Ci sono periodi consigliati per organizzare visite e attività educative?', 'd22bf4d1-a0e3-4562-8773-24e522ebdcc8', NULL, 'TO DO', NULL),
('cf3d1b87-efa3-4c31-921c-b2a5dd586dae', 'Sono disponibili parcheggi e strutture di accesso per il pubblico e i disabili?', '85a86409-864c-4d38-841f-5fc9f6746003', NULL, 'TO DO', NULL),
('cf8be4b1-2a6c-4b46-bb71-f677a57b34e5', 'Potreste fornirci un preventivo indicativo per l\'affitto della struttura e per i servizi inclusi (ad esempio, assistenza logistica, attrezzature, ecc.)?', 'edc12667-268b-451d-ad8d-467664143c8d', NULL, 'TO DO', NULL),
('d10fdaf6-b839-491f-b1e0-27f60d556516', 'Qual è l’impatto della mafia sulle comunità rurali come questa?', '86f4c92b-e1a2-49b2-a7c6-96b252b8f16d', '5bbb6b29-a009-4d49-a1d2-5b9b37564735', 'DONE', NULL),
('d3903ff9-e9dc-4102-a112-eebe1325934d', 'Vorrei sapere se offrite la possibilità di ospitare eventi privati e quali sono le condizioni.', '01831c65-e7f1-412b-908e-5619a6dc26a9', NULL, 'TO DO', NULL),
('d6991894-fc96-4806-87f4-d4987586937c', 'Quali tipi di eventi privati potete ospitare (es. matrimoni, feste aziendali, anniversari, ecc.)?', '9cf1b6f8-8269-4d85-8f9d-232e67ae7530', NULL, 'TO DO', NULL),
('d6e08705-2389-43cf-9002-209462c99d63', 'È possibile organizzare eventi in concomitanza con le vostre attività di sensibilizzazione sulla mafia?', '5d4664b1-797e-4f25-ab6f-416a2864a852', NULL, 'TO DO', NULL),
('d78f7e92-6bee-429b-aa2f-27d1d3749412', 'Quali sono i costi per l’affitto della struttura e i servizi inclusi?', 'b8f2bce4-660b-431d-a970-d65ed60dfd7c', NULL, 'TO DO', NULL),
('d793a6e3-e9b3-45a3-9b82-a57ee368f616', 'Quali sono le modalità di prenotazione e la disponibilità?', '54caaf43-1cbd-45da-89bf-03ebc4c2b7d6', NULL, 'TO DO', NULL),
('dc975357-fd36-4179-8ea5-dd8b7bd1f235', 'Vorrei chiedere:\r\n\r\n- Offrite spazi per eventi privati come matrimoni, cene, feste aziendali o altri eventi simili?\r\n- Quali sono le opzioni per un eventuale soggiorno per gli ospiti che partecipano all\'evento?\r\n- Potremmo anche abbinare l\'evento a una visita o attività di sensibilizzazione sulla mafia?\r\n- Come funziona la prenotazione e quali sono i costi?', '31ca6ed6-6999-4eaa-abeb-3157d1d155c5', NULL, 'IN PROGRESS', NULL),
('def6946c-ca25-49e9-9e6f-81b1a22795fd', 'Quali tipi di eventi privati accogliete?', '70b6a261-94a0-46a4-95b2-efc566e880f2', NULL, 'TO DO', NULL),
('e09e22cb-3c31-4d0c-bfbf-394597ff242a', 'Offrite pacchetti che combinano eventi privati con attività tematiche per sensibilizzare sulla mafia?', '4b7974c2-546e-4b82-89e6-56b09574acc9', NULL, 'TO DO', NULL),
('e1d3ef11-af0d-4706-8d5c-67e81441502b', 'Potreste indicarmi i costi e i servizi inclusi (come catering, sistemazione, ecc.)?', '76ee9ab0-3b07-40f9-bed6-fa983c964570', NULL, 'TO DO', NULL),
('e22fa7b1-a061-4194-ae66-2a3a4b6bcb39', 'Quali spazi sono disponibili per questi eventi?', 'e765ca06-4f4f-44dc-96f9-d95239455fad', NULL, 'TO DO', NULL),
('e8dd04c6-294d-457f-90f9-41af267d4cab', 'Quali sono i costi e i servizi inclusi nel pacchetto per eventi privati?', 'd209cde0-9708-4529-8c33-36274ea29cbc', 'dccf76d3-1a94-4076-88d3-e3d447441d30', 'TO DO', NULL),
('e94c8e90-6638-468f-bf5f-4d061259c32f', 'In che modo questa cascina è stata recuperata o trasformata in uno spazio per la sensibilizzazione sulla mafia?', '4730b91c-c207-437a-a5cd-c1e3c6383527', '2c5bce10-aa81-4cdb-884b-d608e3a98074', 'TO DO', NULL),
('e9c08f14-9421-4c70-ac94-279ad0476319', 'Ospitate eventi privati come matrimoni, feste o eventi aziendali?', 'e0bbe4bb-0efc-42a5-a842-58cbd64261b6', NULL, 'TO DO', NULL),
('ec039324-aa05-4faf-908b-190fd3057ff0', 'Come funziona la prenotazione per un evento privato e quali sono le tariffe?', '50c8499d-e265-4db5-87d7-9a3f2e7a8437', 'bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'TO DO', NULL),
('eddcb9fa-3929-4e93-a80b-0c0caf7d9962', 'Offrite pacchetti che includono anche il pernottamento per gli ospiti?', 'c0fc98a1-1b13-4aec-8ae4-8606c964529e', NULL, 'TO DO', NULL),
('ef424d05-f9de-4bb8-a6c8-5cc6a79db00c', 'Quali spazi sono disponibili per eventi di grandi dimensioni e per il pubblico?', '47ef28c4-f991-4937-b729-cd91447c79cb', NULL, 'TO DO', NULL),
('f1800eb6-cbcc-416a-9a1d-98ee803056ab', 'Qual è la disponibilità della struttura per i prossimi mesi e come funziona la prenotazione?', '298b2a94-6041-4dd4-96db-4e8794205f65', NULL, 'IN PROGRESS', NULL),
('f5fab536-7d2d-4fbd-9ff6-98d944cfeea1', 'Quali attività offrite per sensibilizzare sul tema della mafia?', '8d6c8d32-37b0-4a50-ba4d-33540424f8e8', 'dccf76d3-1a94-4076-88d3-e3d447441d30', 'TO DO', NULL),
('f780606b-acb1-4751-b6d4-be2daa6ff2ff', 'È possibile organizzare soggiorni presso la cascina per gruppi scolastici?', '90c59e44-816d-4e34-a59c-e3a85cf8e771', NULL, 'TO DO', NULL),
('f87565eb-5bce-4bef-84dd-f3e59c1b43df', 'Quali sono i costi e i dettagli organizzativi per questo tipo di esperienza?', '8613abf6-347f-4328-be8d-0a41584ebd6c', NULL, 'TO DO', NULL),
('fa83bfa4-6cf7-49ad-a834-3bc7f7e15988', 'È previsto un programma specifico per le scuole?', 'd28b13e8-2751-4724-b7fd-7dc9e87855ad', 'dccf76d3-1a94-4076-88d3-e3d447441d30', 'TO DO', NULL),
('fdc75181-8fcc-41cd-91f5-e9e40bd31cee', 'I ragazzi devono portarsi il pranzo?', '84af1193-2c82-43e3-b41b-ecccabc553bc', NULL, 'IN PROGRESS', NULL);

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
  `user_img_id` bigint(20) DEFAULT NULL,
  `last_access` datetime(6) DEFAULT NULL,
  `state` varchar(255) NOT NULL,
  `last_seen` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`id`, `name`, `surname`, `email`, `password`, `user_img_id`, `last_access`, `state`, `last_seen`, `last_seen_tasks`) VALUES
('02ae1c9e-c8c4-49b6-997f-2cff993033d0', 'Giorgia', 'Pons', 'giorgia.pons.emp@gmail.com', '$2a$10$OfzLMGEVvSZwrJmVKg70SemlKb1gwB2Vi.l43wgRI56iFQCOQng8K', 12, NULL, 'OFFLINE', NULL),
('180cb2d7-f269-48ff-833e-901f05f64dfb', 'Andrea', 'Daga', 'andrea.daga.emp@gmail.com', '$2a$10$swSNsVtB7VTJjC03gpFwru/HaSv9XuK3oLLkXU.s1zG0mtOSNSbFe', 12, NULL, 'OFFLINE', NULL),
('252259e8-b6e3-40c1-887e-e6c1524898f1', 'Beatrice', 'Ferrari', 'beatrice.ferrari.emp@gmail.com', '$2a$10$BFngrGLLqMuQNeGqDORDi.zLgzkgNjrCNPUbkWV26gTPJ40DLXmPG', 12, '2025-01-23 13:35:31.232498', 'OFFLINE', NULL),
('2c5bce10-aa81-4cdb-884b-d608e3a98074', 'Enrico', 'Romano', 'enrico.romano.emp@gmail.com', '$2a$10$DnXjILVIyQHZA9Hc1AAhO.lX/KurULS80K09eSS4LI1nKOnwz2BPG', 12, '2025-01-23 13:33:35.640529', 'OFFLINE', NULL),
('5bbb6b29-a009-4d49-a1d2-5b9b37564735', 'Carola', 'Esposito', 'carola.esposito.emp@gmail.com', '$2a$10$xF33z0sX2/IOWmg1B8/BmuprTi77aSmXkShwdoz8oMuVpX8WzqEvW', 12, '2025-01-23 13:32:34.721371', 'OFFLINE', NULL),
('5eadf6fb-14c7-4579-b0fa-403a51775fba', 'Giulia', 'Tapparo', 'giulia.tapparo.emp@gmail.com', '$2a$10$85tnOs7hkrGPCUDqgMI8AekeubHRCF8XrfkH.uL4CopWGTTbzr1/a', 12, NULL, 'OFFLINE', NULL),
('6a550ee7-3f33-41a7-9a49-4fcd9ea6794e', 'Luca', 'Esposito', 'luca.esposito.emp@gmail.com', '$2a$10$T3JBZe0LuXnQlxUlyGJu1.wcA8veDegmDCcBlz4JR.n.um/4nDYUa', 12, NULL, 'OFFLINE', NULL),
('6de81b9e-9c66-48d0-83f2-4cca0dc228d3', 'Simone', 'Musu', 'simone.musu.emp@gmail.com', '$2a$10$pfsQDAd6bOY/XBeFpYb/Zu37IV0EnPDExMdFA/OiRZmjpb7GtRueq', 12, NULL, 'OFFLINE', NULL),
('7436f7bc-f635-4a9c-8b4f-44c491dd16c4', 'Domenico', 'Bianchi', 'domenico.bianchi.emp@gmail.com', '$2a$10$Fd/yVVi2ylOfleuEfUgkUuFt8HWzIX/qmdmA9Qigp.EeANgY3YZRW', 12, '2025-01-23 13:37:04.092524', 'OFFLINE', NULL),
('827f5897-9970-4b2c-9a84-02594523813c', 'Elisa', 'Pozzo', 'elisa.pozzo.emp@gmail.com', '$2a$10$ThK5JTjRkfqwxYo/pQEyVOwIxbxSw0n36TL2qrvJlOKdb7G5FL5oC', 12, NULL, 'OFFLINE', NULL),
('b76bcf8d-4dcc-4635-9584-0720cc2a1ab2', 'Luigi', 'Zutta', 'luigi.zutta.emp@gmail.com', '$2a$10$OltIpYQwfAOU2DbtmuGWqe0fuR7aODAd/NaUOznMH.GAnB3Lr10Nm', 12, NULL, 'OFFLINE', NULL),
('bad3ad59-c90d-42a3-a12b-602ccfa2dc14', 'Alessandra', 'Pedrotto', 'alessandra.pedrotto@edu.itspiemonte.it', '$2a$10$thwotTgSIcykEdHtWUQrweh/S0Vy0//Q.DjoZfxCYls4hWLNTpTTe', 28, '2025-01-23 14:20:11.785665', 'OFFLINE', '2025-01-23 14:16:47.731457'),
('d058b959-86a6-4a90-afa3-81f5704d334a', 'Amelia', 'Russo', 'amelia.russo.emp@gmail.com', '$2a$10$0u26A5HguBeR2qD27xdhaOFXVXVpUSAOVr7mTw9EP4SCulNUlBlOG', 16, '2025-01-23 12:43:49.201220', 'OFFLINE', '2025-01-17 16:19:21.056889'),
('dccf76d3-1a94-4076-88d3-e3d447441d30', 'Logan', 'Paul', 'admin.employee@gmail.com', '$2a$10$3B5WxFfLQBl465KwyHj1HOAgEiAdBYgV2yUemN2luLxUwO4MY1BSm', 28, '2025-01-23 13:31:40.283380', 'OFFLINE', '2025-01-23 12:42:56.939180');

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
(1, 'https://cdn-icons-png.freepik.com/256/1396/1396242.png?ga=GA1.1.272991371.1726556643'),
(2, 'https://cdn-icons-png.freepik.com/256/3045/3045363.png?ga=GA1.1.1838490280.1706974849'),
(3, 'https://cdn-icons-png.freepik.com/256/7642/7642647.png?ga=GA1.1.1838490280.1706974849'),
(4, 'https://cdn-icons-png.freepik.com/256/1396/1396222.png?ga=GA1.1.1838490280.1706974849'),
(5, 'https://cdn-icons-png.freepik.com/256/2967/2967570.png?ga=GA1.1.272991371.1726556643'),
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
(23, 'https://cdn-icons-png.freepik.com/256/3316/3316707.png?ga=GA1.1.1703634448.1737638328&semt=sph'),
(24, 'https://cdn-icons-png.freepik.com/256/3597/3597742.png?ga=GA1.1.1838490280.1706974849'),
(25, 'https://cdn-icons-png.freepik.com/256/3605/3605703.png?ga=GA1.1.1838490280.1706974849'),
(26, 'https://cdn-icons-png.freepik.com/256/7417/7417530.png?ga=GA1.1.1838490280.1706974849'),
(27, 'https://cdn-icons-png.freepik.com/256/947/947668.png?ga=GA1.1.1838490280.1706974849'),
(28, 'https://cdn-icons-png.freepik.com/256/1462/1462009.png?ga=GA1.1.272991371.1726556643&semt=sph'),
(29, 'https://cdn-icons-png.freepik.com/256/7034/7034627.png?ga=GA1.1.272991371.1726556643&semt=sph'),
(30, 'https://cdn-icons-png.freepik.com/256/1462/1462020.png?ga=GA1.1.272991371.1726556643');

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
('827f5897-9970-4b2c-9a84-02594523813c', '1'),
('6a550ee7-3f33-41a7-9a49-4fcd9ea6794e', '1'),
('b76bcf8d-4dcc-4635-9584-0720cc2a1ab2', '1'),
('5eadf6fb-14c7-4579-b0fa-403a51775fba', '1'),
('02ae1c9e-c8c4-49b6-997f-2cff993033d0', '1'),
('180cb2d7-f269-48ff-833e-901f05f64dfb', '1'),
('6de81b9e-9c66-48d0-83f2-4cca0dc228d3', '1');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- AUTO_INCREMENT per la tabella `user_image`
--
ALTER TABLE `user_image`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

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
