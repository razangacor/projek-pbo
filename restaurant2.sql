-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 14, 2025 at 05:27 AM
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
-- Database: `restaurant2`
--

-- --------------------------------------------------------

--
-- Table structure for table `bahan_makanan`
--

CREATE TABLE `bahan_makanan` (
  `id_bahan` int(11) NOT NULL,
  `nama_bahan` varchar(100) DEFAULT NULL,
  `stok` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bahan_makanan`
--

INSERT INTO `bahan_makanan` (`id_bahan`, `nama_bahan`, `stok`) VALUES
(1, 'nasi(g)', 2000),
(2, 'telur(butir)', 42),
(3, 'minyak goreng(ml)', 49760),
(4, 'kecap manis(ml)', 1920),
(5, 'bawang merah(siung)', 45);

-- --------------------------------------------------------

--
-- Table structure for table `menu_bahan`
--

CREATE TABLE `menu_bahan` (
  `id_menu` int(11) NOT NULL,
  `id_bahan` int(11) NOT NULL,
  `jumlah` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `menu_bahan`
--

INSERT INTO `menu_bahan` (`id_menu`, `id_bahan`, `jumlah`) VALUES
(1, 1, 250),
(1, 2, 1),
(1, 3, 30),
(1, 4, 10);

-- --------------------------------------------------------

--
-- Table structure for table `menu_makanan`
--

CREATE TABLE `menu_makanan` (
  `id_menu` int(11) NOT NULL,
  `nama_menu` varchar(100) DEFAULT NULL,
  `harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `menu_makanan`
--

INSERT INTO `menu_makanan` (`id_menu`, `nama_menu`, `harga`) VALUES
(1, 'Nasi Goreng', 20000);

-- --------------------------------------------------------

--
-- Table structure for table `pesanan`
--

CREATE TABLE `pesanan` (
  `id_pesanan` int(11) NOT NULL,
  `id_menu` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `total_harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pesanan`
--

INSERT INTO `pesanan` (`id_pesanan`, `id_menu`, `jumlah`, `total_harga`) VALUES
(1, 1, 1, 20000),
(2, 1, 1, 20000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bahan_makanan`
--
ALTER TABLE `bahan_makanan`
  ADD PRIMARY KEY (`id_bahan`);

--
-- Indexes for table `menu_bahan`
--
ALTER TABLE `menu_bahan`
  ADD PRIMARY KEY (`id_menu`,`id_bahan`),
  ADD KEY `id_bahan` (`id_bahan`);

--
-- Indexes for table `menu_makanan`
--
ALTER TABLE `menu_makanan`
  ADD PRIMARY KEY (`id_menu`);

--
-- Indexes for table `pesanan`
--
ALTER TABLE `pesanan`
  ADD PRIMARY KEY (`id_pesanan`),
  ADD KEY `id_menu` (`id_menu`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bahan_makanan`
--
ALTER TABLE `bahan_makanan`
  MODIFY `id_bahan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `menu_makanan`
--
ALTER TABLE `menu_makanan`
  MODIFY `id_menu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `pesanan`
--
ALTER TABLE `pesanan`
  MODIFY `id_pesanan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `menu_bahan`
--
ALTER TABLE `menu_bahan`
  ADD CONSTRAINT `menu_bahan_ibfk_1` FOREIGN KEY (`id_menu`) REFERENCES `menu_makanan` (`id_menu`),
  ADD CONSTRAINT `menu_bahan_ibfk_2` FOREIGN KEY (`id_bahan`) REFERENCES `bahan_makanan` (`id_bahan`);

--
-- Constraints for table `pesanan`
--
ALTER TABLE `pesanan`
  ADD CONSTRAINT `pesanan_ibfk_1` FOREIGN KEY (`id_menu`) REFERENCES `menu_bahan` (`id_menu`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
