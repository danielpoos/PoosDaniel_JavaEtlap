-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2022. Jan 18. 13:22
-- Kiszolgáló verziója: 10.4.21-MariaDB
-- PHP verzió: 7.4.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `etlapdb`
--
CREATE DATABASE IF NOT EXISTS `etlapdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_hungarian_ci;
USE `etlapdb`;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `etlap`
--

CREATE TABLE `etlap` (
  `id` int(11) NOT NULL,
  `nev` varchar(100) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `leiras` text COLLATE utf8mb4_hungarian_ci NOT NULL,
  `ar` int(11) NOT NULL,
  `kategoria` enum('előétel','főétel','desszert') COLLATE utf8mb4_hungarian_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `etlap`
--

INSERT INTO `etlap` (`id`, `nev`, `leiras`, `ar`, `kategoria`) VALUES
(1, 'Húsleves', 'Tyúkhússal, Cérnametélttel', 1390, 'előétel'),
(2, 'Bableves', 'Füstölt csülökkel', 1590, 'előétel'),
(3, 'Gyros tál', 'Gyroshússal, hasábburgonyával, 5 féle salátával ', 1890, 'főétel'),
(4, 'Velővel töltött borda', 'Kirántva, hasábburgonyával', 1890, 'főétel'),
(5, 'Rib-Eye Steak', 'BBQ zöldbabbal', 4950, 'főétel'),
(6, 'Tiramisu', '', 890, 'desszert'),
(7, 'Palacsinta', 'Kakaós, Nutellás, Lekváros', 490, 'desszert');

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `etlap`
--
ALTER TABLE `etlap`
  ADD PRIMARY KEY (`id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `etlap`
--
ALTER TABLE `etlap`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
