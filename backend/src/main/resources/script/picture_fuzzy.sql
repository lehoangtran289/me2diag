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
    `symptom`        VARCHAR(50),
    `positive`       DOUBLE,
    `neutral`        DOUBLE,
    `negative`       DOUBLE,
    `created_at`     DATETIME,
    PRIMARY KEY (examination_id, symptom)
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
    `examination_id` VARCHAR(50) NOT NULL,
    `diagnose`       VARCHAR(50),
    `probability`    DOUBLE,
    PRIMARY KEY (examination_id, diagnose)
);

CREATE TABLE `hedge_algebra_config`
(
    `name`  VARCHAR(50) PRIMARY KEY,
    `fm` DOUBLE,
    `type`  VARCHAR(50),
    `order` INTEGER,
    `created_at`  DATETIME,
    `updated_at`  DATETIME
);

CREATE TABLE `linguistic_domain`
(
    `name` VARCHAR(50) PRIMARY KEY,
    `fm` DOUBLE,
    `v` DOUBLE,
    `order` INTEGER,
    `created_at`  DATETIME,
    `updated_at`  DATETIME
);

INSERT INTO backend.diagnose (name, description, created_at, updated_at) VALUES ('CHEST_PROBLEM', null, '2022-02-26 18:04:40', '2022-02-26 18:04:46');
INSERT INTO backend.diagnose (name, description, created_at, updated_at) VALUES ('FEVER', null, '2022-02-26 18:04:38', '2022-02-26 18:04:44');
INSERT INTO backend.diagnose (name, description, created_at, updated_at) VALUES ('MALARIA', null, '2022-02-26 18:04:39', '2022-02-26 18:04:44');
INSERT INTO backend.diagnose (name, description, created_at, updated_at) VALUES ('STOMACH', null, '2022-02-26 18:04:40', '2022-02-26 18:04:45');
INSERT INTO backend.diagnose (name, description, created_at, updated_at) VALUES ('TYPHOID', null, '2022-02-26 18:04:39', '2022-02-26 18:04:45');

INSERT INTO backend.symptom (name, description, created_at, updated_at) VALUES ('CHEST_PAIN', null, '2022-02-26 18:03:48', '2022-02-26 18:03:50');
INSERT INTO backend.symptom (name, description, created_at, updated_at) VALUES ('COUGH', null, '2022-02-26 18:03:43', '2022-02-26 18:03:47');
INSERT INTO backend.symptom (name, description, created_at, updated_at) VALUES ('HEADACHE', null, '2022-02-26 18:02:12', '2022-02-26 18:02:17');
INSERT INTO backend.symptom (name, description, created_at, updated_at) VALUES ('STOMACH_PAIN', null, '2022-02-26 18:02:50', '2022-02-26 18:03:47');
INSERT INTO backend.symptom (name, description, created_at, updated_at) VALUES ('TEMPERATURE', null, '2022-02-26 18:01:55', '2022-02-26 18:01:57');

INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('CHEST_PAIN', 'CHEST_PROBLEM', 0.9, 0.02, 0.05, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('CHEST_PAIN', 'FEVER', 0.05, 0.25, 0.6, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('CHEST_PAIN', 'MALARIA', 0.03, 0.07, 0.8, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('CHEST_PAIN', 'STOMACH', 0.1, 0.1, 0.7, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('CHEST_PAIN', 'TYPHOID', 0.01, 0.01, 0.85, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('COUGH', 'CHEST_PROBLEM', 0.15, 0.2, 0.7, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('COUGH', 'FEVER', 0.45, 0.2, 0.1, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('COUGH', 'MALARIA', 0.65, 0.5, 0.05, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('COUGH', 'STOMACH', 0.25, 0.25, 0.5, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('COUGH', 'TYPHOID', 0.2, 0.15, 0.6, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('HEADACHE', 'CHEST_PROBLEM', 0.01, 0.1, 0.8, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('HEADACHE', 'FEVER', 0.4, 0.25, 0.3, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('HEADACHE', 'MALARIA', 0.1, 0.2, 0.6, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('HEADACHE', 'STOMACH', 0.3, 0.05, 0.05, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('HEADACHE', 'TYPHOID', 0.75, 0.05, 0.03, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('STOMACH_PAIN', 'CHEST_PROBLEM', 0.1, 0.15, 0.75, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('STOMACH_PAIN', 'FEVER', 0.1, 0.25, 0.6, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('STOMACH_PAIN', 'MALARIA', 0.01, 0.03, 0.9, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('STOMACH_PAIN', 'STOMACH', 0.8, 0.1, 0.01, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('STOMACH_PAIN', 'TYPHOID', 0.1, 0.2, 0.7, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('TEMPERATURE', 'CHEST_PROBLEM', 0.05, 0.15, 0.7, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('TEMPERATURE', 'FEVER', 0.4, 0.4, 0.05, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('TEMPERATURE', 'MALARIA', 0.8, 0.1, 0.1, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('TEMPERATURE', 'STOMACH', 0.15, 0.05, 0.6, '2022-02-26 18:07:59');
INSERT INTO backend.symptom_diagnose (symptom, diagnose, positive, neutral, negative, created_at) VALUES ('TEMPERATURE', 'TYPHOID', 0.3, 0.3, 0.3, '2022-02-26 18:07:59');

INSERT INTO backend.linguistic_domain (name, fm, v, `order`, created_at, updated_at) VALUES ('COMPLETELY', 1, 1, 99, '2022-04-18 22:54:51', null);
INSERT INTO backend.linguistic_domain (name, fm, v, `order`, created_at, updated_at) VALUES ('HIGH', 0.5, 0.75, 3, '2022-04-18 22:54:49', null);
INSERT INTO backend.linguistic_domain (name, fm, v, `order`, created_at, updated_at) VALUES ('LOW', 0.5, 0.25, -1, '2022-04-18 22:54:47', null);
INSERT INTO backend.linguistic_domain (name, fm, v, `order`, created_at, updated_at) VALUES ('MEDIUM', 0.5, 0.5, 0, '2022-04-18 22:54:48', null);
INSERT INTO backend.linguistic_domain (name, fm, v, `order`, created_at, updated_at) VALUES ('NONE', 0, 0, -99, '2022-04-18 22:54:44', null);
INSERT INTO backend.linguistic_domain (name, fm, v, `order`, created_at, updated_at) VALUES ('SLIGHTLY HIGH', 0.25, 0.625, 1, '2022-04-18 22:54:49', null);
INSERT INTO backend.linguistic_domain (name, fm, v, `order`, created_at, updated_at) VALUES ('SLIGHTLY LOW', 0.25, 0.375, -2, '2022-04-18 22:54:46', null);
INSERT INTO backend.linguistic_domain (name, fm, v, `order`, created_at, updated_at) VALUES ('VERY HIGH', 0.25, 0.875, 2, '2022-04-18 22:54:50', null);
INSERT INTO backend.linguistic_domain (name, fm, v, `order`, created_at, updated_at) VALUES ('VERY LOW', 0.25, 0.125, -3, '2022-04-18 22:54:48', null);

INSERT INTO backend.hedge_algebra_config (name, fm, type, `order`, created_at, updated_at) VALUES ('COMPLETELY', 99, 'GENERATOR', 99, '2022-04-18 22:30:27', null);
INSERT INTO backend.hedge_algebra_config (name, fm, type, `order`, created_at, updated_at) VALUES ('HIGH', 0.5, 'GENERATOR', 1, '2022-04-18 22:30:25', null);
INSERT INTO backend.hedge_algebra_config (name, fm, type, `order`, created_at, updated_at) VALUES ('LOW', 0.5, 'GENERATOR', -1, '2022-04-18 22:30:23', null);
INSERT INTO backend.hedge_algebra_config (name, fm, type, `order`, created_at, updated_at) VALUES ('MEDIUM', 0.5, 'GENERATOR', 0, '2022-04-18 22:30:24', null);
INSERT INTO backend.hedge_algebra_config (name, fm, type, `order`, created_at, updated_at) VALUES ('NONE', 0, 'GENERATOR', -99, '2022-04-18 22:30:20', null);
INSERT INTO backend.hedge_algebra_config (name, fm, type, `order`, created_at, updated_at) VALUES ('SLIGHTLY', 0.5, 'HEDGE', -1, '2022-04-18 22:30:23', null);
INSERT INTO backend.hedge_algebra_config (name, fm, type, `order`, created_at, updated_at) VALUES ('VERY', 0.5, 'HEDGE', 1, '2022-04-18 22:30:22', null);
