-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.24-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.0.0.6468
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for ecashier
CREATE DATABASE IF NOT EXISTS `ecashier` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `ecashier`;

-- Dumping structure for table ecashier.barang
CREATE TABLE IF NOT EXISTS `barang` (
  `kode_barang` varchar(50) NOT NULL DEFAULT '',
  `nama_barang` varchar(50) DEFAULT NULL,
  `harga` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`kode_barang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table ecashier.barang: ~3 rows (approximately)
INSERT INTO `barang` (`kode_barang`, `nama_barang`, `harga`) VALUES
	('B0001', 'smartwatch', '500'),
	('B0002', 'tablet', '5500'),
	('B0003', 'smartphone', '5000');

-- Dumping structure for table ecashier.faktur
CREATE TABLE IF NOT EXISTS `faktur` (
  `kode_faktur` varchar(50) NOT NULL DEFAULT '',
  `tanggal` varchar(50) DEFAULT NULL,
  `total` varchar(50) DEFAULT NULL,
  `jumlah_bayar` varchar(50) DEFAULT NULL,
  `kembalian` varchar(50) DEFAULT NULL,
  `layanan` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`kode_faktur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table ecashier.faktur: ~5 rows (approximately)
INSERT INTO `faktur` (`kode_faktur`, `tanggal`, `total`, `jumlah_bayar`, `kembalian`, `layanan`) VALUES
	('F0001', '07/07/2022', '28000', '30000', '2000', 'Sudah'),
	('F0002', '07/07/2022', '2000', '2000', '0', 'Sudah'),
	('F0003', '07/07/2022', '6000', '10000', '4000', 'Belum'),
	('F0004', '07/07/2022', '23000', '25000', '2000', 'Belum'),
	('F0005', '07/07/2022', '10000', '20000', '10000', 'Belum');

-- Dumping structure for table ecashier.login
CREATE TABLE IF NOT EXISTS `login` (
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table ecashier.login: ~0 rows (approximately)
INSERT INTO `login` (`username`, `password`) VALUES
	('admin', 'admin');

-- Dumping structure for table ecashier.transaksi
CREATE TABLE IF NOT EXISTS `transaksi` (
  `no_antrian` varchar(50) DEFAULT NULL,
  `kuantitas` varchar(50) DEFAULT NULL,
  `kode_faktur` varchar(50) DEFAULT NULL,
  `kode_barang` varchar(50) DEFAULT NULL,
  KEY `FK_transaksi_faktur` (`kode_faktur`),
  KEY `FK_transaksi_barang` (`kode_barang`),
  CONSTRAINT `FK_transaksi_barang` FOREIGN KEY (`kode_barang`) REFERENCES `barang` (`kode_barang`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_transaksi_faktur` FOREIGN KEY (`kode_faktur`) REFERENCES `faktur` (`kode_faktur`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table ecashier.transaksi: ~8 rows (approximately)
INSERT INTO `transaksi` (`no_antrian`, `kuantitas`, `kode_faktur`, `kode_barang`) VALUES
	('0001', '2', 'F0001', 'B0001'),
	('0001', '3', 'F0001', 'B0002'),
	('0001', '4', 'F0001', 'B0003'),
	('0002', '4', 'F0002', 'B0001'),
	('0003', '6', 'F0003', 'B0001'),
	('0004', '5', 'F0004', 'B0001'),
	('0004', '2', 'F0004', 'B0002'),
	('0004', '', 'F0004', 'B0002'),
	('0005', '5', 'F0005', 'B0001'),
	('0005', '1', 'F0005', 'B0003');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
