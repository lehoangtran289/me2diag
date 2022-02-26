DROP TABLE IF EXISTS `examination`;
DROP TABLE IF EXISTS `examination_result`;
DROP TABLE IF EXISTS `diagnose`;
DROP TABLE IF EXISTS `symptom`;
DROP TABLE IF EXISTS `patient`;
DROP TABLE IF EXISTS `patient_symptom`;
DROP TABLE IF EXISTS `symptom_diagnose`;

CREATE TABLE `patient`
(
    `id`         VARCHAR(50) PRIMARY KEY NOT NULL,
    `name`       varchar(100)            NOT NULL,
    `birth_date` DATE DEFAULT NULL       NULL,
    `gender`     VARCHAR(50),
    `avatar`     VARCHAR(100),
    `created_at` DATETIME,
    `updated_at` DATETIME
);

CREATE TABLE `symptom`
(
    `name`        VARCHAR(50) PRIMARY KEY NOT NULL,
    `description` TEXT,
    `created_at`  DATETIME,
    `updated_at`  DATETIME
);

CREATE TABLE `diagnose`
(
    `name`        VARCHAR(50) PRIMARY KEY NOT NULL,
    `description` TEXT,
    `created_at`  DATETIME,
    `updated_at`  DATETIME
);

CREATE TABLE `patient_symptom`
(
    `examination_id` VARCHAR(50) NOT NULL,
    `patient_id`     VARCHAR(50) NOT NULL,
    `symptom`        VARCHAR(50),
    `positive`       DOUBLE,
    `neutral`        DOUBLE,
    `negative`       DOUBLE,
    `created_at`     DATETIME,
    PRIMARY KEY (examination_id, patient_id, symptom)
);

CREATE TABLE `symptom_diagnose`
(
    `symptom`    VARCHAR(50),
    `diagnose`   VARCHAR(50),
    `positive`   DOUBLE,
    `neutral`    DOUBLE,
    `negative`   DOUBLE,
    `created_at` DATETIME,
    PRIMARY KEY (symptom, diagnose)
);

CREATE TABLE `examination`
(
    `id`         VARCHAR(50) PRIMARY KEY NOT NULL,
    `user_id`    VARCHAR(50),
    `patient_id` VARCHAR(50),
    `created_at` DATETIME
);

CREATE TABLE `examination_result`
(
    `examination_id` VARCHAR(50) PRIMARY KEY NOT NULL,
    `diagnose`       VARCHAR(50),
    `probability`    DOUBLE,
    `created_at`     DATETIME
);

