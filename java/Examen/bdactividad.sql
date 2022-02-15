-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 31, 2022 at 12:25 PM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bdactividad`
--

-- --------------------------------------------------------

--
-- Table structure for table `actividad`
--

DROP TABLE IF EXISTS `actividad`;
CREATE TABLE IF NOT EXISTS `actividad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `impartidor_id` int(11) DEFAULT NULL,
  `nombre` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `coste_mensual` float NOT NULL DEFAULT '0',
  `capacidad` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `impartidor_id` (`impartidor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Dumping data for table `actividad`
--

INSERT INTO `actividad` (`id`, `impartidor_id`, `nombre`, `coste_mensual`, `capacidad`) VALUES
(1, 2, 'Restauración de muebles', 15, 4),
(2, 3, 'Ganchillo', 10, 8),
(3, 1, 'Corte y confección', 4, 2),
(4, 1, 'Calceta', 6, 8),
(5, 1, 'Punto de cruz', 25, 6),
(6, 2, 'Cestería', 22, 12),
(7, 2, 'Corte y confección', 20, 4),
(8, 2, 'Ganchillo', 25, 4),
(9, 1, 'Ganchillo', 30, 5),
(10, 2, 'Punto de cruz', 18, 10),
(11, 4, 'Crochet', 30, 3),
(12, 4, 'Diseño de mascarillas', 20, 10),
(13, 5, 'Crochet', 27, 7),
(14, 5, 'Diseño de mascarillas', 19, 6),
(15, 5, 'Corte y confección', 28, 6);

-- --------------------------------------------------------

--
-- Table structure for table `alumno`
--

DROP TABLE IF EXISTS `alumno`;
CREATE TABLE IF NOT EXISTS `alumno` (
  `dni` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `apellidos` varchar(40) COLLATE utf8_spanish2_ci NOT NULL,
  `nombre` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `telefono` varchar(9) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Dumping data for table `alumno`
--

INSERT INTO `alumno` (`dni`, `apellidos`, `nombre`, `telefono`, `email`) VALUES
('12125651M', 'Armentia Santos', 'Rafael', '956080041', 'arment@hotmail.com'),
('12345678Z', 'Blanco Gil', 'Francisco Javier', '934002333', 'blancogil@hotmail.com'),
('16003888J', 'Carrera Gomez', 'Josu', '666333789', 'carrjosu@gmail.com'),
('16200324A', 'Garrido Quintana', 'Gema', '676545890', 'gema1975@ms.es'),
('16546788D', 'Estrada Garcia', 'Ana', '654123456', 'estrada@gmail.com'),
('18100234P', 'Arranz Garcia', 'Maite', '911345689', 'maitearranz@gmail.com'),
('18234290H', 'Guridi Roman', 'Rafael', '689230097', 'rafaguridi@euskalnet.net'),
('18320558G', 'Hernando Ibarrola', 'Paula', '675880448', 'hibarr@gmail.com'),
('18438062H', 'Romero Barrio', 'Juan', '958100238', 'jrombarrio@gmail.com'),
('20812344N', 'Rezola Casado', 'Ander', '628123456', 'ander89@gmail.com'),
('25036543P', 'Gaspar Platas', 'Raúl', '956122112', 'raulgas@hotmail.com'),
('31071234M', 'Osorno Martin', 'David', '676767676', 'davidos@euskaltel.eus'),
('31123456V', 'Moreda Alava', 'Alazne', '650908766', 'alaznema@gmail.com'),
('31812304S', 'Nueno Sanchez', 'Lierni', '956333444', 'liernu@hotmail.com'),
('31839938A', 'Ruiz Palacios', 'Patxi', '696555666', 'rpalacios@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `impartidor`
--

DROP TABLE IF EXISTS `impartidor`;
CREATE TABLE IF NOT EXISTS `impartidor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apellido` varchar(40) COLLATE utf8_spanish2_ci NOT NULL,
  `nombre` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Dumping data for table `impartidor`
--

INSERT INTO `impartidor` (`id`, `apellido`, `nombre`) VALUES
(1, 'Varani Lusar', 'Claudio'),
(2, 'Garriga Rodriguez', 'Marc'),
(3, 'Gomez Aguirre', 'Marta'),
(4, 'Sanz Mardones', 'Lidia'),
(5, 'Smith Vals', 'Andrew');

-- --------------------------------------------------------

--
-- Table structure for table `participa`
--

DROP TABLE IF EXISTS `participa`;
CREATE TABLE IF NOT EXISTS `participa` (
  `actividad_id` int(11) NOT NULL,
  `alumno_dni` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `ultima_asistencia` date DEFAULT NULL,
  PRIMARY KEY (`actividad_id`,`alumno_dni`),
  KEY `alumno_dni` (`alumno_dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Dumping data for table `participa`
--

INSERT INTO `participa` (`actividad_id`, `alumno_dni`, `ultima_asistencia`) VALUES
(1, '12125651M', '2021-10-25'),
(1, '12345678Z', '2021-10-11'),
(1, '25036543P', '2021-12-18'),
(1, '31123456V', '2021-09-03'),
(2, '25036543P', '2022-01-03'),
(2, '31123456V', '2021-07-22'),
(3, '20812344N', '2021-12-08'),
(3, '31812304S', '2021-10-25'),
(4, '12125651M', '2021-11-26'),
(4, '16003888J', '2021-12-06'),
(4, '16200324A', '2021-12-06'),
(4, '16546788D', '2021-10-03'),
(4, '20812344N', '2021-12-06'),
(4, '25036543P', '2021-12-21'),
(4, '31071234M', '2021-12-14'),
(4, '31839938A', '2021-12-06'),
(5, '12125651M', '2022-01-08'),
(5, '12345678Z', '2021-08-13'),
(5, '25036543P', '2022-01-03'),
(5, '31123456V', '2021-04-12'),
(6, '20812344N', '2021-10-09'),
(6, '25036543P', '2021-11-16'),
(6, '31123456V', '2021-09-20'),
(7, '16546788D', '2021-12-05'),
(7, '18320558G', '2021-12-15'),
(7, '18438062H', '2021-10-19'),
(7, '20812344N', '2021-12-16'),
(8, '16003888J', '2021-12-02'),
(11, '12345678Z', '2021-11-08'),
(11, '18100234P', '2021-09-11'),
(11, '18320558G', '2021-12-02'),
(14, '18100234P', '2021-12-02'),
(14, '18234290H', '2021-12-12'),
(14, '18438062H', '2021-08-13'),
(14, '20812344N', '2021-12-13'),
(14, '25036543P', '2021-12-07'),
(14, '31839938A', '2021-08-07'),
(15, '16200324A', '2021-12-06'),
(15, '18234290H', '2021-12-06');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `actividad`
--
ALTER TABLE `actividad`
  ADD CONSTRAINT `actividad_ibfk_1` FOREIGN KEY (`impartidor_id`) REFERENCES `impartidor` (`id`);

--
-- Constraints for table `participa`
--
ALTER TABLE `participa`
  ADD CONSTRAINT `participa_ibfk_1` FOREIGN KEY (`actividad_id`) REFERENCES `actividad` (`id`),
  ADD CONSTRAINT `participa_ibfk_2` FOREIGN KEY (`alumno_dni`) REFERENCES `alumno` (`dni`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
