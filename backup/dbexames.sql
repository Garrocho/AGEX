-- phpMyAdmin SQL Dump
-- version 3.5.6
-- http://www.phpmyadmin.net
--
-- Máquina: localhost
-- Data de Criação: 01-Mar-2013 às 18:07
-- Versão do servidor: 5.1.45-community-log
-- versão do PHP: 5.3.2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de Dados: `dbexames`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `agenda`
--

CREATE TABLE IF NOT EXISTS `agenda` (
  `dataHora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idMedico` int(11) NOT NULL,
  `idExame` int(11) NOT NULL,
  `idPaciente` int(11) NOT NULL,
  `obs` text,
  `resultado` text,
  PRIMARY KEY (`dataHora`,`idMedico`,`idExame`,`idPaciente`),
  KEY `FK_MEDICO_AGENDA` (`idMedico`),
  KEY `FK_EXAME_AGENDA` (`idExame`),
  KEY `FK_PAC_AGENDA` (`idPaciente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `agenda`
--

INSERT INTO `agenda` (`dataHora`, `idMedico`, `idExame`, `idPaciente`, `obs`, `resultado`) VALUES
('1993-04-30 03:00:00', 1, 1, 1, '1', '1'),
('2013-02-28 03:00:00', 1, 4, 1, '', ''),
('2013-02-28 03:00:00', 4, 3, 1, '', ''),
('2013-03-01 16:25:09', 1, 1, 1, '', '');

-- --------------------------------------------------------

--
-- Estrutura da tabela `exame`
--

CREATE TABLE IF NOT EXISTS `exame` (
  `idExame` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) NOT NULL DEFAULT '',
  `valor` float(9,3) NOT NULL,
  PRIMARY KEY (`idExame`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Extraindo dados da tabela `exame`
--

INSERT INTO `exame` (`idExame`, `nome`, `valor`) VALUES
(1, 'Tarde', 150.000),
(3, 'exames de sangue', 100.000),
(4, 'exame de fezes', 100.000);

-- --------------------------------------------------------

--
-- Estrutura da tabela `medico`
--

CREATE TABLE IF NOT EXISTS `medico` (
  `idMedico` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL DEFAULT '',
  `crm` varchar(15) NOT NULL DEFAULT '',
  PRIMARY KEY (`idMedico`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Extraindo dados da tabela `medico`
--

INSERT INTO `medico` (`idMedico`, `nome`, `crm`) VALUES
(1, 'Doutor Garrocho', '1545444'),
(4, 'fabricio', '123');

-- --------------------------------------------------------

--
-- Estrutura da tabela `paciente`
--

CREATE TABLE IF NOT EXISTS `paciente` (
  `idPaciente` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL DEFAULT '',
  `dataNasc` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `logradouro` varchar(60) DEFAULT NULL,
  `numero` varchar(10) DEFAULT NULL,
  `bairro` varchar(60) DEFAULT NULL,
  `cidade` varchar(60) DEFAULT NULL,
  `uf` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`idPaciente`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Extraindo dados da tabela `paciente`
--

INSERT INTO `paciente` (`idPaciente`, `nome`, `dataNasc`, `logradouro`, `numero`, `bairro`, `cidade`, `uf`) VALUES
(1, 'Charles', '1991-05-01 00:00:00', 'teste', '3', '2', '1', 'AC'),
(3, 'Thiago', '1993-05-05 00:00:00', 'Rua d', '33', 'Pinheiro Grosso', 'Barbacena', 'MS'),
(4, 'Fabricio2', '2012-05-30 00:00:00', 'sdf', 'df', 'sdf', 'sdf', 'AC');

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `agenda`
--
ALTER TABLE `agenda`
  ADD CONSTRAINT `FK_EXAME_AGENDA` FOREIGN KEY (`idExame`) REFERENCES `exame` (`idExame`),
  ADD CONSTRAINT `FK_MEDICO_AGENDA` FOREIGN KEY (`idMedico`) REFERENCES `medico` (`idMedico`),
  ADD CONSTRAINT `FK_PAC_AGENDA` FOREIGN KEY (`idPaciente`) REFERENCES `paciente` (`idPaciente`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
