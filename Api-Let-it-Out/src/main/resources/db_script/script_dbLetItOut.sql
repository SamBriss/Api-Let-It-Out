-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 11-03-2024 a las 00:19:12
-- Versión del servidor: 10.4.25-MariaDB
-- Versión de PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_letitout`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activitiesfromtherapist`
--

CREATE TABLE `activitiesfromtherapist` (
  `activityTId` int(11) NOT NULL,
  `userTAGId` int(11) DEFAULT NULL,
  `userTherapistId` int(11) DEFAULT NULL,
  `label` varchar(200) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `dateAsign` date DEFAULT NULL,
  `dateMax` date DEFAULT NULL,
  `completed` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activitytherapistcalendar`
--

CREATE TABLE `activitytherapistcalendar` (
  `activityId` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `startHour` time DEFAULT NULL,
  `endHour` time DEFAULT NULL,
  `duration` time DEFAULT NULL,
  `motive` varchar(200) DEFAULT NULL,
  `appointment` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `appointmentcalendar`
--

CREATE TABLE `appointmentcalendar` (
  `appointmentId` int(11) NOT NULL,
  `userTAGId` int(11) DEFAULT NULL,
  `userTherapistId` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `startHour` time DEFAULT NULL,
  `endHour` time DEFAULT NULL,
  `therapistAcceptance` tinyint(1) DEFAULT NULL,
  `TAGacceptance` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `attackcopingmethods`
--

CREATE TABLE `attackcopingmethods` (
  `attackMethodsId` int(11) NOT NULL,
  `attackId` int(11) DEFAULT NULL,
  `copingMethodId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `attackregisterdetails`
--

CREATE TABLE `attackregisterdetails` (
  `attackId` int(11) NOT NULL,
  `attackRegisterId` int(11) DEFAULT NULL,
  `place` varchar(60) DEFAULT NULL,
  `motive` varchar(200) DEFAULT NULL,
  `explanationResume` varchar(300) DEFAULT NULL,
  `intensity` int(11) DEFAULT NULL,
  `emotions` varchar(300) DEFAULT NULL,
  `physicalSensations` varchar(300) DEFAULT NULL,
  `thoughts` varchar(300) DEFAULT NULL,
  `typeOfThought` char(1) DEFAULT NULL,
  `attackMethodsId` int(11) DEFAULT NULL,
  `reportURL` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `attackregisters`
--

CREATE TABLE `attackregisters` (
  `attackRegisterId` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `startHour` time DEFAULT NULL,
  `endHour` time DEFAULT NULL,
  `duration` varchar(6) DEFAULT NULL,
  `completed` tinyint(1) DEFAULT NULL,
  `typeId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `attackregistertypes`
--

CREATE TABLE `attackregistertypes` (
  `typeId` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `calendarconfigurationusers`
--

CREATE TABLE `calendarconfigurationusers` (
  `configurationId` int(11) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `startWorkDay` time DEFAULT NULL,
  `endWorkDay` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `calendartagactivity`
--

CREATE TABLE `calendartagactivity` (
  `activityId` int(11) NOT NULL,
  `userTAGId` int(11) DEFAULT NULL,
  `label` varchar(40) DEFAULT NULL,
  `location` varchar(70) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `startHour` time DEFAULT NULL,
  `endHour` time DEFAULT NULL,
  `duration` time DEFAULT NULL,
  `dateRegister` date DEFAULT NULL,
  `comments` varchar(200) DEFAULT NULL,
  `reminders` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `copingmethods`
--

CREATE TABLE `copingmethods` (
  `copingMethodId` int(11) NOT NULL,
  `method` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `diaryentries`
--

CREATE TABLE `diaryentries` (
  `diaryId` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `hour` time DEFAULT NULL,
  `text` text DEFAULT NULL,
  `userTAGId` int(11) DEFAULT NULL,
  `emotionId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `diccionarycategories`
--

CREATE TABLE `diccionarycategories` (
  `categoryId` int(11) NOT NULL,
  `name` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dictionarycount`
--

CREATE TABLE `dictionarycount` (
  `dictionaryCountId` int(11) NOT NULL,
  `userTAGId` int(11) DEFAULT NULL,
  `dictionaryWordId` int(11) DEFAULT NULL,
  `repetitions` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dictionarywords`
--

CREATE TABLE `dictionarywords` (
  `dictionaryWordId` int(11) NOT NULL,
  `word` varchar(80) DEFAULT NULL,
  `categoryId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `directions`
--

CREATE TABLE `directions` (
  `directionId` int(11) NOT NULL,
  `street` varchar(50) DEFAULT NULL,
  `numExt` int(11) DEFAULT NULL,
  `numInt` int(11) DEFAULT NULL,
  `colony` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `documents`
--

CREATE TABLE `documents` (
  `documentId` int(11) NOT NULL,
  `activityTId` int(11) DEFAULT NULL,
  `referenceURL` varchar(300) DEFAULT NULL,
  `status` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `emergencycontacts`
--

CREATE TABLE `emergencycontacts` (
  `emergencyContactId` int(11) NOT NULL,
  `userTAGId` int(11) DEFAULT NULL,
  `nameContact` varchar(100) DEFAULT NULL,
  `numberContact` varchar(14) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `feedbackdate`
--

CREATE TABLE `feedbackdate` (
  `feedbackDateId` int(11) NOT NULL,
  `listenedId` int(11) DEFAULT NULL,
  `feedbackDate` date DEFAULT NULL,
  `attackRegisterId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `feedbackemotions`
--

CREATE TABLE `feedbackemotions` (
  `emotionsId` int(11) NOT NULL,
  `name` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `frecuencygraphics`
--

CREATE TABLE `frecuencygraphics` (
  `frecuencyGraphicId` int(11) NOT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `elapsedTime` varchar(30) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `count` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `informativearticles`
--

CREATE TABLE `informativearticles` (
  `articleId` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `topicClassification` varchar(150) DEFAULT NULL,
  `sinopsis` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `levelstag`
--

CREATE TABLE `levelstag` (
  `levelTAGId` int(11) NOT NULL,
  `level` varchar(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `levelstag`
--

INSERT INTO `levelstag` (`levelTAGId`, `level`) VALUES
(1, 'leve');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `listenedaudiosfeedback`
--

CREATE TABLE `listenedaudiosfeedback` (
  `listenedId` int(11) NOT NULL,
  `userTAGId` int(11) DEFAULT NULL,
  `audioId` int(11) DEFAULT NULL,
  `progress` varchar(6) DEFAULT NULL,
  `score` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `multiplepreferences`
--

CREATE TABLE `multiplepreferences` (
  `multiplePreferenceId` int(11) NOT NULL,
  `preferenceId` int(11) DEFAULT NULL,
  `audioId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `preferencedays`
--

CREATE TABLE `preferencedays` (
  `preferenceDayId` int(11) NOT NULL,
  `configurationId` int(11) DEFAULT NULL,
  `weekDayId` int(11) DEFAULT NULL,
  `StartHour` time DEFAULT NULL,
  `EndHour` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `preferencescategories`
--

CREATE TABLE `preferencescategories` (
  `categoryId` int(11) NOT NULL,
  `name` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `preferencestaguser`
--

CREATE TABLE `preferencestaguser` (
  `preferenceId` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `userTAGId` int(11) DEFAULT NULL,
  `categoryId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `psychiatricdomains`
--

CREATE TABLE `psychiatricdomains` (
  `domainId` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `psychiatricdomainsquestionaire`
--

CREATE TABLE `psychiatricdomainsquestionaire` (
  `psychiatricDomainQuestionaireId` int(11) NOT NULL,
  `userTAGId` int(11) DEFAULT NULL,
  `domainId` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `executionDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registrylist`
--

CREATE TABLE `registrylist` (
  `listId` int(11) NOT NULL,
  `dictionaryWordId` int(11) DEFAULT NULL,
  `userTAGId` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `relationusers`
--

CREATE TABLE `relationusers` (
  `relationUsersId` int(11) NOT NULL,
  `userTAGId` int(11) DEFAULT NULL,
  `userTherapistId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `relaxationtechniqueaudios`
--

CREATE TABLE `relaxationtechniqueaudios` (
  `audioId` int(11) NOT NULL,
  `name` varchar(70) DEFAULT NULL,
  `duration` varchar(6) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `schedule` char(1) DEFAULT NULL,
  `auditory` char(1) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `levelTAGId` int(11) DEFAULT NULL,
  `techniqueLevel` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reminders`
--

CREATE TABLE `reminders` (
  `remindersId` int(11) NOT NULL,
  `userTAGId` int(11) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `time` time DEFAULT NULL,
  `reminderTypeId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `remindertypes`
--

CREATE TABLE `remindertypes` (
  `reminderTypeId` int(11) NOT NULL,
  `name` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reminderweekdays`
--

CREATE TABLE `reminderweekdays` (
  `reminderWeekdayId` int(11) NOT NULL,
  `reminderId` int(11) DEFAULT NULL,
  `weekdayId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `responsableadult`
--

CREATE TABLE `responsableadult` (
  `adultId` int(11) NOT NULL,
  `userTAGId` int(11) DEFAULT NULL,
  `nameResponsable` varchar(120) DEFAULT NULL,
  `parentesco` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `subdictionaries`
--

CREATE TABLE `subdictionaries` (
  `subDiccionaryId` int(11) NOT NULL,
  `dictionaryWordId` int(11) DEFAULT NULL,
  `userTAGId` int(11) DEFAULT NULL,
  `repetition` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `techniquesranking`
--

CREATE TABLE `techniquesranking` (
  `rankingId` int(11) NOT NULL,
  `preferenceId` int(11) DEFAULT NULL,
  `audioId` int(11) DEFAULT NULL,
  `finalScore` int(11) DEFAULT NULL,
  `countUses` int(11) DEFAULT NULL,
  `usersScore` int(11) DEFAULT NULL,
  `executionDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `triggerelements`
--

CREATE TABLE `triggerelements` (
  `triggerElementId` int(11) NOT NULL,
  `triggerPatternId` int(11) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `rPearson` int(11) DEFAULT NULL,
  `individualProbability` int(11) DEFAULT NULL,
  `categoryDictionaryId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `triggerpatterns`
--

CREATE TABLE `triggerpatterns` (
  `triggerPatternId` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `totalAttacks` int(11) DEFAULT NULL,
  `userTAGId` int(11) DEFAULT NULL,
  `graphicImg` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `userId` int(11) NOT NULL,
  `username` varchar(60) DEFAULT NULL,
  `password` varchar(300) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `lastnameP` varchar(40) DEFAULT NULL,
  `lastnameM` varchar(40) DEFAULT NULL,
  `tel` varchar(14) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `token` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`userId`, `username`, `password`, `email`, `name`, `lastnameP`, `lastnameM`, `tel`, `age`, `gender`, `token`) VALUES
(1, 'SamanthaBriss', 'Sam200905', 'samantha@gmail.com', 'Samantha', 'Briseno', 'Valadez', '3335960281', 18, 'M', 1234);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `userstag`
--

CREATE TABLE `userstag` (
  `userTAGId` int(11) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `diagnosticExistence` tinyint(1) DEFAULT NULL,
  `levelTAGId` int(11) DEFAULT NULL,
  `medsExistence` tinyint(1) DEFAULT NULL,
  `registerDate` date DEFAULT NULL,
  `levelTAGQuestionaireDate` date DEFAULT NULL,
  `umbral` int(11) DEFAULT NULL,
  `levelTechiniques` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `userstag`
--

INSERT INTO `userstag` (`userTAGId`, `userId`, `diagnosticExistence`, `levelTAGId`, `medsExistence`, `registerDate`, `levelTAGQuestionaireDate`, `umbral`, `levelTechiniques`) VALUES
(1, 1, 0, 1, 0, '2024-03-10', '2024-03-10', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `userstherapists`
--

CREATE TABLE `userstherapists` (
  `userTherapistId` int(11) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `licence` varchar(255) DEFAULT NULL,
  `contract` tinyint(1) DEFAULT NULL,
  `vinculationCode` int(11) DEFAULT NULL,
  `directionId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `weekdays`
--

CREATE TABLE `weekdays` (
  `weekDayId` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `activitiesfromtherapist`
--
ALTER TABLE `activitiesfromtherapist`
  ADD PRIMARY KEY (`activityTId`),
  ADD KEY `userTAGId` (`userTAGId`),
  ADD KEY `userTherapistId` (`userTherapistId`);

--
-- Indices de la tabla `activitytherapistcalendar`
--
ALTER TABLE `activitytherapistcalendar`
  ADD PRIMARY KEY (`activityId`);

--
-- Indices de la tabla `appointmentcalendar`
--
ALTER TABLE `appointmentcalendar`
  ADD PRIMARY KEY (`appointmentId`),
  ADD KEY `userTAGId` (`userTAGId`),
  ADD KEY `userTherapistId` (`userTherapistId`);

--
-- Indices de la tabla `attackcopingmethods`
--
ALTER TABLE `attackcopingmethods`
  ADD PRIMARY KEY (`attackMethodsId`),
  ADD KEY `attackId` (`attackId`),
  ADD KEY `copingMethodId` (`copingMethodId`);

--
-- Indices de la tabla `attackregisterdetails`
--
ALTER TABLE `attackregisterdetails`
  ADD PRIMARY KEY (`attackId`),
  ADD KEY `attackRegisterId` (`attackRegisterId`);

--
-- Indices de la tabla `attackregisters`
--
ALTER TABLE `attackregisters`
  ADD PRIMARY KEY (`attackRegisterId`),
  ADD KEY `typeId` (`typeId`);

--
-- Indices de la tabla `attackregistertypes`
--
ALTER TABLE `attackregistertypes`
  ADD PRIMARY KEY (`typeId`);

--
-- Indices de la tabla `calendarconfigurationusers`
--
ALTER TABLE `calendarconfigurationusers`
  ADD PRIMARY KEY (`configurationId`),
  ADD KEY `userId` (`userId`);

--
-- Indices de la tabla `calendartagactivity`
--
ALTER TABLE `calendartagactivity`
  ADD PRIMARY KEY (`activityId`),
  ADD KEY `userTAGId` (`userTAGId`);

--
-- Indices de la tabla `copingmethods`
--
ALTER TABLE `copingmethods`
  ADD PRIMARY KEY (`copingMethodId`);

--
-- Indices de la tabla `diaryentries`
--
ALTER TABLE `diaryentries`
  ADD PRIMARY KEY (`diaryId`),
  ADD KEY `userTAGId` (`userTAGId`),
  ADD KEY `emotionId` (`emotionId`);

--
-- Indices de la tabla `diccionarycategories`
--
ALTER TABLE `diccionarycategories`
  ADD PRIMARY KEY (`categoryId`);

--
-- Indices de la tabla `dictionarycount`
--
ALTER TABLE `dictionarycount`
  ADD PRIMARY KEY (`dictionaryCountId`),
  ADD KEY `userTAGId` (`userTAGId`),
  ADD KEY `dictionaryWordId` (`dictionaryWordId`);

--
-- Indices de la tabla `dictionarywords`
--
ALTER TABLE `dictionarywords`
  ADD PRIMARY KEY (`dictionaryWordId`),
  ADD KEY `categoryId` (`categoryId`);

--
-- Indices de la tabla `directions`
--
ALTER TABLE `directions`
  ADD PRIMARY KEY (`directionId`);

--
-- Indices de la tabla `documents`
--
ALTER TABLE `documents`
  ADD PRIMARY KEY (`documentId`),
  ADD KEY `activityTId` (`activityTId`);

--
-- Indices de la tabla `emergencycontacts`
--
ALTER TABLE `emergencycontacts`
  ADD PRIMARY KEY (`emergencyContactId`),
  ADD KEY `userTAGId` (`userTAGId`);

--
-- Indices de la tabla `feedbackdate`
--
ALTER TABLE `feedbackdate`
  ADD PRIMARY KEY (`feedbackDateId`),
  ADD KEY `listenedId` (`listenedId`),
  ADD KEY `attackRegisterId` (`attackRegisterId`);

--
-- Indices de la tabla `feedbackemotions`
--
ALTER TABLE `feedbackemotions`
  ADD PRIMARY KEY (`emotionsId`);

--
-- Indices de la tabla `frecuencygraphics`
--
ALTER TABLE `frecuencygraphics`
  ADD PRIMARY KEY (`frecuencyGraphicId`);

--
-- Indices de la tabla `informativearticles`
--
ALTER TABLE `informativearticles`
  ADD PRIMARY KEY (`articleId`);

--
-- Indices de la tabla `levelstag`
--
ALTER TABLE `levelstag`
  ADD PRIMARY KEY (`levelTAGId`);

--
-- Indices de la tabla `listenedaudiosfeedback`
--
ALTER TABLE `listenedaudiosfeedback`
  ADD PRIMARY KEY (`listenedId`),
  ADD KEY `userTAGId` (`userTAGId`),
  ADD KEY `audioId` (`audioId`);

--
-- Indices de la tabla `multiplepreferences`
--
ALTER TABLE `multiplepreferences`
  ADD PRIMARY KEY (`multiplePreferenceId`),
  ADD KEY `preferenceId` (`preferenceId`),
  ADD KEY `audioId` (`audioId`);

--
-- Indices de la tabla `preferencedays`
--
ALTER TABLE `preferencedays`
  ADD PRIMARY KEY (`preferenceDayId`),
  ADD KEY `configurationId` (`configurationId`),
  ADD KEY `weekDayId` (`weekDayId`);

--
-- Indices de la tabla `preferencescategories`
--
ALTER TABLE `preferencescategories`
  ADD PRIMARY KEY (`categoryId`);

--
-- Indices de la tabla `preferencestaguser`
--
ALTER TABLE `preferencestaguser`
  ADD PRIMARY KEY (`preferenceId`),
  ADD KEY `userTAGId` (`userTAGId`),
  ADD KEY `categoryId` (`categoryId`);

--
-- Indices de la tabla `psychiatricdomains`
--
ALTER TABLE `psychiatricdomains`
  ADD PRIMARY KEY (`domainId`);

--
-- Indices de la tabla `psychiatricdomainsquestionaire`
--
ALTER TABLE `psychiatricdomainsquestionaire`
  ADD PRIMARY KEY (`psychiatricDomainQuestionaireId`),
  ADD KEY `userTAGId` (`userTAGId`),
  ADD KEY `domainId` (`domainId`);

--
-- Indices de la tabla `registrylist`
--
ALTER TABLE `registrylist`
  ADD PRIMARY KEY (`listId`),
  ADD KEY `dictionaryWordId` (`dictionaryWordId`),
  ADD KEY `userTAGId` (`userTAGId`);

--
-- Indices de la tabla `relationusers`
--
ALTER TABLE `relationusers`
  ADD PRIMARY KEY (`relationUsersId`),
  ADD KEY `userTAGId` (`userTAGId`),
  ADD KEY `userTherapistId` (`userTherapistId`);

--
-- Indices de la tabla `relaxationtechniqueaudios`
--
ALTER TABLE `relaxationtechniqueaudios`
  ADD PRIMARY KEY (`audioId`),
  ADD KEY `levelTAGId` (`levelTAGId`);

--
-- Indices de la tabla `reminders`
--
ALTER TABLE `reminders`
  ADD PRIMARY KEY (`remindersId`),
  ADD KEY `userTAGId` (`userTAGId`),
  ADD KEY `reminderTypeId` (`reminderTypeId`);

--
-- Indices de la tabla `remindertypes`
--
ALTER TABLE `remindertypes`
  ADD PRIMARY KEY (`reminderTypeId`);

--
-- Indices de la tabla `reminderweekdays`
--
ALTER TABLE `reminderweekdays`
  ADD PRIMARY KEY (`reminderWeekdayId`),
  ADD KEY `reminderId` (`reminderId`),
  ADD KEY `weekdayId` (`weekdayId`);

--
-- Indices de la tabla `responsableadult`
--
ALTER TABLE `responsableadult`
  ADD PRIMARY KEY (`adultId`),
  ADD KEY `userTAGId` (`userTAGId`);

--
-- Indices de la tabla `subdictionaries`
--
ALTER TABLE `subdictionaries`
  ADD PRIMARY KEY (`subDiccionaryId`),
  ADD KEY `dictionaryWordId` (`dictionaryWordId`),
  ADD KEY `userTAGId` (`userTAGId`);

--
-- Indices de la tabla `techniquesranking`
--
ALTER TABLE `techniquesranking`
  ADD PRIMARY KEY (`rankingId`),
  ADD KEY `preferenceId` (`preferenceId`),
  ADD KEY `audioId` (`audioId`);

--
-- Indices de la tabla `triggerelements`
--
ALTER TABLE `triggerelements`
  ADD PRIMARY KEY (`triggerElementId`),
  ADD KEY `triggerPatternId` (`triggerPatternId`),
  ADD KEY `categoryDictionaryId` (`categoryDictionaryId`);

--
-- Indices de la tabla `triggerpatterns`
--
ALTER TABLE `triggerpatterns`
  ADD PRIMARY KEY (`triggerPatternId`),
  ADD KEY `userTAGId` (`userTAGId`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userId`);

--
-- Indices de la tabla `userstag`
--
ALTER TABLE `userstag`
  ADD PRIMARY KEY (`userTAGId`),
  ADD KEY `userId` (`userId`),
  ADD KEY `levelTAGId` (`levelTAGId`);

--
-- Indices de la tabla `userstherapists`
--
ALTER TABLE `userstherapists`
  ADD PRIMARY KEY (`userTherapistId`),
  ADD KEY `userId` (`userId`),
  ADD KEY `directionId` (`directionId`);

--
-- Indices de la tabla `weekdays`
--
ALTER TABLE `weekdays`
  ADD PRIMARY KEY (`weekDayId`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `activitiesfromtherapist`
--
ALTER TABLE `activitiesfromtherapist`
  MODIFY `activityTId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `activitytherapistcalendar`
--
ALTER TABLE `activitytherapistcalendar`
  MODIFY `activityId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `appointmentcalendar`
--
ALTER TABLE `appointmentcalendar`
  MODIFY `appointmentId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `attackcopingmethods`
--
ALTER TABLE `attackcopingmethods`
  MODIFY `attackMethodsId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `attackregisterdetails`
--
ALTER TABLE `attackregisterdetails`
  MODIFY `attackId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `attackregisters`
--
ALTER TABLE `attackregisters`
  MODIFY `attackRegisterId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `attackregistertypes`
--
ALTER TABLE `attackregistertypes`
  MODIFY `typeId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `calendarconfigurationusers`
--
ALTER TABLE `calendarconfigurationusers`
  MODIFY `configurationId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `calendartagactivity`
--
ALTER TABLE `calendartagactivity`
  MODIFY `activityId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `copingmethods`
--
ALTER TABLE `copingmethods`
  MODIFY `copingMethodId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `diaryentries`
--
ALTER TABLE `diaryentries`
  MODIFY `diaryId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `diccionarycategories`
--
ALTER TABLE `diccionarycategories`
  MODIFY `categoryId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `dictionarycount`
--
ALTER TABLE `dictionarycount`
  MODIFY `dictionaryCountId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `dictionarywords`
--
ALTER TABLE `dictionarywords`
  MODIFY `dictionaryWordId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `directions`
--
ALTER TABLE `directions`
  MODIFY `directionId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `documents`
--
ALTER TABLE `documents`
  MODIFY `documentId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `emergencycontacts`
--
ALTER TABLE `emergencycontacts`
  MODIFY `emergencyContactId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `feedbackdate`
--
ALTER TABLE `feedbackdate`
  MODIFY `feedbackDateId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `feedbackemotions`
--
ALTER TABLE `feedbackemotions`
  MODIFY `emotionsId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `frecuencygraphics`
--
ALTER TABLE `frecuencygraphics`
  MODIFY `frecuencyGraphicId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `informativearticles`
--
ALTER TABLE `informativearticles`
  MODIFY `articleId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `levelstag`
--
ALTER TABLE `levelstag`
  MODIFY `levelTAGId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `listenedaudiosfeedback`
--
ALTER TABLE `listenedaudiosfeedback`
  MODIFY `listenedId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `multiplepreferences`
--
ALTER TABLE `multiplepreferences`
  MODIFY `multiplePreferenceId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `preferencedays`
--
ALTER TABLE `preferencedays`
  MODIFY `preferenceDayId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `preferencescategories`
--
ALTER TABLE `preferencescategories`
  MODIFY `categoryId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `preferencestaguser`
--
ALTER TABLE `preferencestaguser`
  MODIFY `preferenceId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `psychiatricdomains`
--
ALTER TABLE `psychiatricdomains`
  MODIFY `domainId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `psychiatricdomainsquestionaire`
--
ALTER TABLE `psychiatricdomainsquestionaire`
  MODIFY `psychiatricDomainQuestionaireId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `registrylist`
--
ALTER TABLE `registrylist`
  MODIFY `listId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `relationusers`
--
ALTER TABLE `relationusers`
  MODIFY `relationUsersId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `relaxationtechniqueaudios`
--
ALTER TABLE `relaxationtechniqueaudios`
  MODIFY `audioId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `reminders`
--
ALTER TABLE `reminders`
  MODIFY `remindersId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `remindertypes`
--
ALTER TABLE `remindertypes`
  MODIFY `reminderTypeId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `reminderweekdays`
--
ALTER TABLE `reminderweekdays`
  MODIFY `reminderWeekdayId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `responsableadult`
--
ALTER TABLE `responsableadult`
  MODIFY `adultId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `subdictionaries`
--
ALTER TABLE `subdictionaries`
  MODIFY `subDiccionaryId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `techniquesranking`
--
ALTER TABLE `techniquesranking`
  MODIFY `rankingId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `triggerelements`
--
ALTER TABLE `triggerelements`
  MODIFY `triggerElementId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `triggerpatterns`
--
ALTER TABLE `triggerpatterns`
  MODIFY `triggerPatternId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `userstag`
--
ALTER TABLE `userstag`
  MODIFY `userTAGId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `userstherapists`
--
ALTER TABLE `userstherapists`
  MODIFY `userTherapistId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `weekdays`
--
ALTER TABLE `weekdays`
  MODIFY `weekDayId` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `activitiesfromtherapist`
--
ALTER TABLE `activitiesfromtherapist`
  ADD CONSTRAINT `activitiesfromtherapist_ibfk_1` FOREIGN KEY (`userTAGId`) REFERENCES `userstag` (`userTAGId`),
  ADD CONSTRAINT `activitiesfromtherapist_ibfk_2` FOREIGN KEY (`userTherapistId`) REFERENCES `userstherapists` (`userTherapistId`);

--
-- Filtros para la tabla `appointmentcalendar`
--
ALTER TABLE `appointmentcalendar`
  ADD CONSTRAINT `appointmentcalendar_ibfk_1` FOREIGN KEY (`userTAGId`) REFERENCES `userstag` (`userTAGId`),
  ADD CONSTRAINT `appointmentcalendar_ibfk_2` FOREIGN KEY (`userTherapistId`) REFERENCES `userstherapists` (`userTherapistId`);

--
-- Filtros para la tabla `attackcopingmethods`
--
ALTER TABLE `attackcopingmethods`
  ADD CONSTRAINT `attackcopingmethods_ibfk_1` FOREIGN KEY (`attackId`) REFERENCES `attackregisterdetails` (`attackId`),
  ADD CONSTRAINT `attackcopingmethods_ibfk_2` FOREIGN KEY (`copingMethodId`) REFERENCES `copingmethods` (`copingMethodId`);

--
-- Filtros para la tabla `attackregisterdetails`
--
ALTER TABLE `attackregisterdetails`
  ADD CONSTRAINT `attackregisterdetails_ibfk_1` FOREIGN KEY (`attackRegisterId`) REFERENCES `attackregisters` (`attackRegisterId`);

--
-- Filtros para la tabla `attackregisters`
--
ALTER TABLE `attackregisters`
  ADD CONSTRAINT `attackregisters_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `attackregistertypes` (`typeId`);

--
-- Filtros para la tabla `calendarconfigurationusers`
--
ALTER TABLE `calendarconfigurationusers`
  ADD CONSTRAINT `calendarconfigurationusers_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`);

--
-- Filtros para la tabla `calendartagactivity`
--
ALTER TABLE `calendartagactivity`
  ADD CONSTRAINT `calendartagactivity_ibfk_1` FOREIGN KEY (`userTAGId`) REFERENCES `userstag` (`userTAGId`);

--
-- Filtros para la tabla `diaryentries`
--
ALTER TABLE `diaryentries`
  ADD CONSTRAINT `diaryentries_ibfk_1` FOREIGN KEY (`userTAGId`) REFERENCES `userstag` (`userTAGId`),
  ADD CONSTRAINT `diaryentries_ibfk_2` FOREIGN KEY (`emotionId`) REFERENCES `feedbackemotions` (`emotionsId`);

--
-- Filtros para la tabla `dictionarycount`
--
ALTER TABLE `dictionarycount`
  ADD CONSTRAINT `dictionarycount_ibfk_1` FOREIGN KEY (`userTAGId`) REFERENCES `userstag` (`userTAGId`),
  ADD CONSTRAINT `dictionarycount_ibfk_2` FOREIGN KEY (`dictionaryWordId`) REFERENCES `dictionarywords` (`dictionaryWordId`);

--
-- Filtros para la tabla `dictionarywords`
--
ALTER TABLE `dictionarywords`
  ADD CONSTRAINT `dictionarywords_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `diccionarycategories` (`categoryId`);

--
-- Filtros para la tabla `documents`
--
ALTER TABLE `documents`
  ADD CONSTRAINT `documents_ibfk_1` FOREIGN KEY (`activityTId`) REFERENCES `activitiesfromtherapist` (`activityTId`);

--
-- Filtros para la tabla `emergencycontacts`
--
ALTER TABLE `emergencycontacts`
  ADD CONSTRAINT `emergencycontacts_ibfk_1` FOREIGN KEY (`userTAGId`) REFERENCES `userstag` (`userTAGId`);

--
-- Filtros para la tabla `feedbackdate`
--
ALTER TABLE `feedbackdate`
  ADD CONSTRAINT `feedbackdate_ibfk_1` FOREIGN KEY (`listenedId`) REFERENCES `listenedaudiosfeedback` (`listenedId`),
  ADD CONSTRAINT `feedbackdate_ibfk_2` FOREIGN KEY (`attackRegisterId`) REFERENCES `attackregisters` (`attackRegisterId`);

--
-- Filtros para la tabla `listenedaudiosfeedback`
--
ALTER TABLE `listenedaudiosfeedback`
  ADD CONSTRAINT `listenedaudiosfeedback_ibfk_1` FOREIGN KEY (`userTAGId`) REFERENCES `userstag` (`userTAGId`),
  ADD CONSTRAINT `listenedaudiosfeedback_ibfk_2` FOREIGN KEY (`audioId`) REFERENCES `relaxationtechniqueaudios` (`audioId`);

--
-- Filtros para la tabla `multiplepreferences`
--
ALTER TABLE `multiplepreferences`
  ADD CONSTRAINT `multiplepreferences_ibfk_1` FOREIGN KEY (`preferenceId`) REFERENCES `preferencestaguser` (`preferenceId`),
  ADD CONSTRAINT `multiplepreferences_ibfk_2` FOREIGN KEY (`audioId`) REFERENCES `relaxationtechniqueaudios` (`audioId`);

--
-- Filtros para la tabla `preferencedays`
--
ALTER TABLE `preferencedays`
  ADD CONSTRAINT `preferencedays_ibfk_1` FOREIGN KEY (`configurationId`) REFERENCES `calendarconfigurationusers` (`configurationId`),
  ADD CONSTRAINT `preferencedays_ibfk_2` FOREIGN KEY (`weekDayId`) REFERENCES `weekdays` (`weekDayId`);

--
-- Filtros para la tabla `preferencestaguser`
--
ALTER TABLE `preferencestaguser`
  ADD CONSTRAINT `preferencestaguser_ibfk_1` FOREIGN KEY (`userTAGId`) REFERENCES `userstag` (`userTAGId`),
  ADD CONSTRAINT `preferencestaguser_ibfk_2` FOREIGN KEY (`categoryId`) REFERENCES `preferencescategories` (`categoryId`);

--
-- Filtros para la tabla `psychiatricdomainsquestionaire`
--
ALTER TABLE `psychiatricdomainsquestionaire`
  ADD CONSTRAINT `psychiatricdomainsquestionaire_ibfk_1` FOREIGN KEY (`userTAGId`) REFERENCES `userstag` (`userTAGId`),
  ADD CONSTRAINT `psychiatricdomainsquestionaire_ibfk_2` FOREIGN KEY (`domainId`) REFERENCES `psychiatricdomains` (`domainId`);

--
-- Filtros para la tabla `registrylist`
--
ALTER TABLE `registrylist`
  ADD CONSTRAINT `registrylist_ibfk_1` FOREIGN KEY (`dictionaryWordId`) REFERENCES `dictionarywords` (`dictionaryWordId`),
  ADD CONSTRAINT `registrylist_ibfk_2` FOREIGN KEY (`userTAGId`) REFERENCES `userstag` (`userTAGId`);

--
-- Filtros para la tabla `relationusers`
--
ALTER TABLE `relationusers`
  ADD CONSTRAINT `relationusers_ibfk_1` FOREIGN KEY (`userTAGId`) REFERENCES `userstag` (`userTAGId`),
  ADD CONSTRAINT `relationusers_ibfk_2` FOREIGN KEY (`userTherapistId`) REFERENCES `userstherapists` (`userTherapistId`);

--
-- Filtros para la tabla `relaxationtechniqueaudios`
--
ALTER TABLE `relaxationtechniqueaudios`
  ADD CONSTRAINT `relaxationtechniqueaudios_ibfk_1` FOREIGN KEY (`levelTAGId`) REFERENCES `levelstag` (`levelTAGId`);

--
-- Filtros para la tabla `reminders`
--
ALTER TABLE `reminders`
  ADD CONSTRAINT `reminders_ibfk_1` FOREIGN KEY (`userTAGId`) REFERENCES `userstag` (`userTAGId`),
  ADD CONSTRAINT `reminders_ibfk_2` FOREIGN KEY (`reminderTypeId`) REFERENCES `remindertypes` (`reminderTypeId`);

--
-- Filtros para la tabla `reminderweekdays`
--
ALTER TABLE `reminderweekdays`
  ADD CONSTRAINT `reminderweekdays_ibfk_1` FOREIGN KEY (`reminderId`) REFERENCES `reminders` (`remindersId`),
  ADD CONSTRAINT `reminderweekdays_ibfk_2` FOREIGN KEY (`weekdayId`) REFERENCES `weekdays` (`weekDayId`);

--
-- Filtros para la tabla `responsableadult`
--
ALTER TABLE `responsableadult`
  ADD CONSTRAINT `responsableadult_ibfk_1` FOREIGN KEY (`userTAGId`) REFERENCES `userstag` (`userTAGId`);

--
-- Filtros para la tabla `subdictionaries`
--
ALTER TABLE `subdictionaries`
  ADD CONSTRAINT `subdictionaries_ibfk_1` FOREIGN KEY (`dictionaryWordId`) REFERENCES `dictionarywords` (`dictionaryWordId`),
  ADD CONSTRAINT `subdictionaries_ibfk_2` FOREIGN KEY (`userTAGId`) REFERENCES `userstag` (`userTAGId`);

--
-- Filtros para la tabla `techniquesranking`
--
ALTER TABLE `techniquesranking`
  ADD CONSTRAINT `techniquesranking_ibfk_1` FOREIGN KEY (`preferenceId`) REFERENCES `preferencestaguser` (`preferenceId`),
  ADD CONSTRAINT `techniquesranking_ibfk_2` FOREIGN KEY (`audioId`) REFERENCES `relaxationtechniqueaudios` (`audioId`);

--
-- Filtros para la tabla `triggerelements`
--
ALTER TABLE `triggerelements`
  ADD CONSTRAINT `triggerelements_ibfk_1` FOREIGN KEY (`triggerPatternId`) REFERENCES `triggerpatterns` (`triggerPatternId`),
  ADD CONSTRAINT `triggerelements_ibfk_2` FOREIGN KEY (`categoryDictionaryId`) REFERENCES `diccionarycategories` (`categoryId`);

--
-- Filtros para la tabla `triggerpatterns`
--
ALTER TABLE `triggerpatterns`
  ADD CONSTRAINT `triggerpatterns_ibfk_1` FOREIGN KEY (`userTAGId`) REFERENCES `userstag` (`userTAGId`);

--
-- Filtros para la tabla `userstag`
--
ALTER TABLE `userstag`
  ADD CONSTRAINT `userstag_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`),
  ADD CONSTRAINT `userstag_ibfk_2` FOREIGN KEY (`levelTAGId`) REFERENCES `levelstag` (`levelTAGId`);

--
-- Filtros para la tabla `userstherapists`
--
ALTER TABLE `userstherapists`
  ADD CONSTRAINT `userstherapists_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`),
  ADD CONSTRAINT `userstherapists_ibfk_2` FOREIGN KEY (`directionId`) REFERENCES `directions` (`directionId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
