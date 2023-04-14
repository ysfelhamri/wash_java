-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 13, 2023 at 07:30 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lavage`
--

-- --------------------------------------------------------

--
-- Table structure for table `auth`
--

CREATE TABLE `auth` (
  `id` int(11) NOT NULL,
  `pass` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `auth`
--

INSERT INTO `auth` (`id`, `pass`) VALUES
(1, 'admin1234');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `ID` int(11) NOT NULL,
  `Num_voi` varchar(100) DEFAULT NULL,
  `Nom` varchar(100) DEFAULT NULL,
  `Prenom` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`ID`, `Num_voi`, `Nom`, `Prenom`) VALUES
(1, 'supprimee', 'supprimee', 'supprimee'),
(3, '999WEWE', 'EL BAKASSI', 'Ismail');

-- --------------------------------------------------------

--
-- Table structure for table `employe`
--

CREATE TABLE `employe` (
  `ID` int(11) NOT NULL,
  `Nom` varchar(100) DEFAULT NULL,
  `Prenom` varchar(100) DEFAULT NULL,
  `Adresse` varchar(100) DEFAULT NULL,
  `Fonction` varchar(100) DEFAULT NULL,
  `Salaire` double DEFAULT NULL,
  `Date_rec` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employe`
--

INSERT INTO `employe` (`ID`, `Nom`, `Prenom`, `Adresse`, `Fonction`, `Salaire`, `Date_rec`) VALUES
(1, 'supprimee', 'supprimee', 'supprimee', 'supprimee', 0, NULL),
(2, 'Youssef', 'EL HAMRI', 'AGADIR', 'Lavage', 1500, '2023-01-12'),
(4, 'EL BAKASSI', 'Ismail', 'Agadir', 'Lavage', 1500, '2023-01-13');

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `ID` int(11) NOT NULL,
  `Nom_ser` varchar(100) DEFAULT NULL,
  `Prix` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`ID`, `Nom_ser`, `Prix`) VALUES
(1, 'supprimee', 0),
(3, 'Lavage des tapis', 150),
(7, 'Lavage des pneus', 200),
(8, 'Lavage complet', 500),
(10, 'Lavage de vert', 100),
(11, 'Lavage des portes', 200);

-- --------------------------------------------------------

--
-- Table structure for table `servit`
--

CREATE TABLE `servit` (
  `ID` int(11) NOT NULL,
  `ID_tra` int(11) DEFAULT NULL,
  `ID_em` int(11) DEFAULT NULL,
  `ID_ser` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `servit`
--

INSERT INTO `servit` (`ID`, `ID_tra`, `ID_em`, `ID_ser`) VALUES
(3, 3, 4, 3),
(4, 3, 4, 7),
(5, 4, 2, 3),
(6, 4, 2, 7),
(7, 4, 2, 8);

-- --------------------------------------------------------

--
-- Table structure for table `transact`
--

CREATE TABLE `transact` (
  `ID` int(11) NOT NULL,
  `ID_cl` int(11) DEFAULT NULL,
  `Prix_total` double DEFAULT NULL,
  `Date_tra` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transact`
--

INSERT INTO `transact` (`ID`, `ID_cl`, `Prix_total`, `Date_tra`) VALUES
(3, 1, 350, '2023-01-13'),
(4, 3, 850, '2023-01-13');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `auth`
--
ALTER TABLE `auth`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `employe`
--
ALTER TABLE `employe`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `servit`
--
ALTER TABLE `servit`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_tra` (`ID_tra`),
  ADD KEY `ID_em` (`ID_em`),
  ADD KEY `ID_ser` (`ID_ser`);

--
-- Indexes for table `transact`
--
ALTER TABLE `transact`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_cl` (`ID_cl`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `auth`
--
ALTER TABLE `auth`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `employe`
--
ALTER TABLE `employe`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `servit`
--
ALTER TABLE `servit`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `transact`
--
ALTER TABLE `transact`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `servit`
--
ALTER TABLE `servit`
  ADD CONSTRAINT `servit_ibfk_1` FOREIGN KEY (`ID_tra`) REFERENCES `transact` (`ID`),
  ADD CONSTRAINT `servit_ibfk_2` FOREIGN KEY (`ID_em`) REFERENCES `employe` (`ID`),
  ADD CONSTRAINT `servit_ibfk_3` FOREIGN KEY (`ID_ser`) REFERENCES `service` (`ID`);

--
-- Constraints for table `transact`
--
ALTER TABLE `transact`
  ADD CONSTRAINT `transact_ibfk_1` FOREIGN KEY (`ID_cl`) REFERENCES `client` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
