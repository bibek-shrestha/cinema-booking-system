-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 14, 2016 at 05:39 PM
-- Server version: 5.5.47-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `cinema_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking_tbl`
--

CREATE TABLE IF NOT EXISTS `booking_tbl` (
  `booking_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `show_id` int(11) NOT NULL,
  `seats` int(11) NOT NULL,
  `status` enum('BOOKED','CANCELED') NOT NULL DEFAULT 'BOOKED',
  PRIMARY KEY (`booking_id`),
  KEY `user_id` (`user_id`),
  KEY `show_id` (`show_id`),
  KEY `show_id_2` (`show_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `booking_tbl`
--

INSERT INTO `booking_tbl` (`booking_id`, `user_id`, `show_id`, `seats`, `status`) VALUES
(1, 2, 1, 5, 'BOOKED'),
(2, 2, 2, 4, 'CANCELED'),
(3, 2, 2, 5, 'BOOKED'),
(4, 1, 1, 5, 'CANCELED'),
(5, 1, 2, 4, 'BOOKED'),
(6, 2, 5, 5, 'CANCELED');

-- --------------------------------------------------------

--
-- Table structure for table `show_tbl`
--

CREATE TABLE IF NOT EXISTS `show_tbl` (
  `show_id` int(11) NOT NULL AUTO_INCREMENT,
  `showname` varchar(100) NOT NULL,
  `showtime` time DEFAULT NULL,
  `showdate` date DEFAULT NULL,
  `totalseats` int(11) DEFAULT NULL,
  `bookedseats` int(11) NOT NULL,
  `bookingstatus` enum('OPEN','CLOSED') NOT NULL,
  PRIMARY KEY (`show_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `show_tbl`
--

INSERT INTO `show_tbl` (`show_id`, `showname`, `showtime`, `showdate`, `totalseats`, `bookedseats`, `bookingstatus`) VALUES
(1, 'Iceage', '10:00:00', '2016-10-10', 100, 5, 'OPEN'),
(2, 'Terminator', '12:00:00', '2016-11-11', 60, 4, 'OPEN'),
(3, 'Deadpool', '12:00:00', '2016-02-14', 60, 0, 'OPEN'),
(4, 'X-men', '12:00:00', '2016-02-14', 60, 0, 'CLOSED'),
(5, 'Superman', '12:00:00', '2016-02-14', 60, 0, 'OPEN'),
(6, 'Carrie', '10:00:00', '2016-10-10', 100, 0, 'CLOSED');

-- --------------------------------------------------------

--
-- Table structure for table `user_tbl`
--

CREATE TABLE IF NOT EXISTS `user_tbl` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(500) NOT NULL,
  `email` varchar(200) NOT NULL,
  `role` enum('ROLE_ADMIN','ROLE_USER') NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `user_tbl`
--

INSERT INTO `user_tbl` (`user_id`, `username`, `password`, `email`, `role`) VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin@example.com', 'ROLE_ADMIN'),
(2, 'user', 'ee11cbb19052e40b07aac0ca060c23ee', 'user@example.com', 'ROLE_USER');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
