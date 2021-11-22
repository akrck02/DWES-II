-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-10-2020 a las 23:24:38
-- Versión del servidor: 5.7.14
-- Versión de PHP: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdsubastas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `id` int(11) NOT NULL,
  `categoria` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`id`, `categoria`) VALUES
(1, 'Joyas'),
(2, 'Libros'),
(3, 'Música'),
(4, 'Vestimentas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `imagenes`
--

CREATE TABLE `imagenes` (
  `id` int(11) NOT NULL,
  `id_item` int(11) NOT NULL,
  `imagen` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `imagenes`
--

INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(1, 1, 'anillomar1.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(2, 1, 'anillomar2.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(3, 2, 'collar1.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(4, 2, 'collar2.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(5, 2, 'collar3.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(6, 3, 'david1.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(7, 4, 'milestrump1.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(8, 4, 'milestrump2.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(9, 5, 'organ1.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(10, 5, 'organ2.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(11, 6, 'mozart1.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(12, 8, 'oscarwilde.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(13, 8, 'oscarwilde2.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(14, 8, 'oscarwilde3.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(15, 8, 'oscarwilde4.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(16, 8, 'oscarwilde5.jpg');
INSERT INTO `imagenes` (`id`, `id_item`, `imagen`) VALUES
(17, 10, 'jackson1.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `items`
--

CREATE TABLE `items` (
  `id` int(11) NOT NULL,
  `id_cat` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `preciopartida` float NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  `fechafin` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `items`
--

INSERT INTO `items` (`id`, `id_cat`, `id_user`, `nombre`, `preciopartida`, `descripcion`, `fechafin`) VALUES
(1, 1, 1, 'Anillo de Marilyn', 20000, 'Anillo de la eternidad de Marilyn Monroe', '2021-10-27 14:00:00'),
(2, 1, 2, 'Collar de María Antonieta', 31000, 'Collar de la reina María Antonieta, origen del escándalo en la Corte de Versalles', '2021-12-24 00:00:00'),
(3, 2, 1, 'Libro de David', 201000, 'Portada del manuscrito de David', '2022-06-09 18:30:00'),
(4, 3, 4, 'Trompeta de Miles Davis', 61000, 'Trompeta número de serie LM 713071 con grabado frontal del virtuoso Miles Davis', '2021-09-02 12:00:00'),
(5, 3, 4, 'Organo abadía de Leicester', 50000, 'Enorme órgano de la escuela Hinckley. Oportunidad', '2021-11-13 11:00:00'),
(6, 3, 3, 'Manuscrito Mozart', 8900, 'Manuscrito original de la Fantasía para piano número 4 de Wolfgang Amadeus Mozart', '2021-08-16 11:00:00'),
(7, 1, 3, 'Diamante de Sierra Leona', 9800000, 'Enorme diamante de 709 kilates', '2021-09-29 00:00:00'),
(8, 2, 4, 'Libro de Oscar Wilde', 4800, 'Edición de La importancia de llamarse Ernesto, entregada por Oscar Wilde al gobernador de su prisión', '2021-07-10 00:00:00'),
(9, 1, 2, 'Esmeralda Rockefeller', 55000000, 'Anillo de esmeralda de 18,4 quilates del amigo de la familia Rockefeller  Raymond Carter Yard', '2022-03-24 00:00:00'),
(10, 4, 3, 'Guante Michael Jackson', 43000, 'Guante de lentejuelas blanco de Michael Jackson', '2021-11-23 15:30:00'),
(11, 4, 1, 'Vestido Marilyn', 19000, 'Icónico vestido de Marilyn Monroe en Happy Birthday Mr President', '2022-07-14 19:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pujas`
--

CREATE TABLE `pujas` (
  `id` int(11) NOT NULL,
  `id_item` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `cantidad` float NOT NULL,
  `fecha` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `pujas`
--

INSERT INTO `pujas` (`id`, `id_item`, `id_user`, `cantidad`, `fecha`) VALUES
(1, 1, 2, 21100, '2021-10-03'),
(2, 1, 3, 22000, '2021-10-05'),
(3, 2, 1, 32500, '2021-10-30'),
(4, 3, 2, 400000, '2021-10-30'),
(5, 3, 2, 405000, '2021-10-31'),
(6, 3, 4, 500010, '2021-11-02'),
(7, 4, 1, 80100, '2021-08-29'),
(8, 4, 5, 90500, '2021-08-30'),
(9, 4, 2, 100000, '2021-09-02'),
(10, 1, 5, 25000, '2021-10-20'),
(11, 9, 5, 56900000, '2021-11-01'),
(12, 9, 4, 58000000, '2021-11-02'),
(13, 11, 2, 30000, '2021-10-30'),
(14, 11, 4, 32000, '2021-10-31'),
(15, 11, 5, 35000, '2021-11-01');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `username` varchar(40) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `password` varchar(40) NOT NULL,
  `email` varchar(100) NOT NULL,
  `cadenaverificacion` varchar(100) NOT NULL,
  `activo` tinyint(4) NOT NULL,
  `falso` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `username`, `nombre`, `password`, `email`, `cadenaverificacion`, `activo`, `falso`) VALUES
(1, 'petraru', 'Petra Rupérez', '123456', 'petra@subastas.com', '123698123123123tyu', 1, 0),
(2, 'patxiku', 'Francisco Aguirre', '123456', 'josegran@subastas.com', '123123123123123tyu', 1, 0),
(3, 'anneru', 'Anne Richardson', '123456', 'annric@annric.com', '178123123123177o4u', 1, 0),
(4, 'sandriuska', 'Sandra Armendáriz', '123456', 'sandrarm@subastas.com', '555123123123187hh1', 1, 0),
(5, 'bliskthunder', 'Sergei Pruschenko', '123456', 'sergipru@subastas.com', 'ejq423tty123187hh1', 1, 0),
(8, '', 'Nadia Romeu', '', 'nadiarom@adsfdf.com', '', 1, 1),
(9, '', 'John Roberton', '', 'roberts@global.com', '', 1, 1),
(10, '', 'Fernando Parsim', '', 'ferparsim@internat.com', '', 1, 1),
(11, '', 'Linda Globe', '', 'lindagg@maxwell.com', '', 1, 1),
(12, '', 'Rasha Marlo2', '', 'rasharr@marl.com', '', 1, 1),
(13, '', 'María Ortiz', '', 'mariaort@mar.com', '', 1, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `imagenes`
--
ALTER TABLE `imagenes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_item` (`id_item`);

--
-- Indices de la tabla `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_cat` (`id_cat`),
  ADD KEY `id_user` (`id_user`);

--
-- Indices de la tabla `pujas`
--
ALTER TABLE `pujas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_item` (`id_item`),
  ADD KEY `id_user` (`id_user`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `imagenes`
--
ALTER TABLE `imagenes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT de la tabla `items`
--
ALTER TABLE `items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT de la tabla `pujas`
--
ALTER TABLE `pujas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `imagenes`
--
ALTER TABLE `imagenes`
  ADD CONSTRAINT `imagenes_ibfk_1` FOREIGN KEY (`id_item`) REFERENCES `items` (`id`);

--
-- Filtros para la tabla `items`
--
ALTER TABLE `items`
  ADD CONSTRAINT `items_ibfk_1` FOREIGN KEY (`id_cat`) REFERENCES `categorias` (`id`),
  ADD CONSTRAINT `items_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `pujas`
--
ALTER TABLE `pujas`
  ADD CONSTRAINT `pujas_ibfk_1` FOREIGN KEY (`id_item`) REFERENCES `items` (`id`),
  ADD CONSTRAINT `pujas_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `usuarios` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
