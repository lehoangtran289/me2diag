CREATE DATABASE IF NOT EXISTS backend;

USE backend;

-- CREATE USER 'admin'@'%' IDENTIFIED BY 'admin';
-- GRANT ALL PRIVILEGES ON backend.* TO 'admin'@'%';
-- flush privileges;

DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user`
(
    `id`                  VARCHAR(50) PRIMARY KEY NOT NULL,
    `username`            VARCHAR(100) UNIQUE     NOT NULL,
    `email`               VARCHAR(100) UNIQUE     NOT NULL,
    `password`            VARCHAR(100)            NOT NULL,
    `password_token`      VARCHAR(255),
    `token_creation_date` DATETIME,
    `is_enable`           TINYINT(1)              NOT NULL,

    `first_name`          VARCHAR(80),
    `last_name`           VARCHAR(80),
    `avatar`              VARCHAR(100),
    `birth_date`          DATE DEFAULT NULL       NULL,
    `gender`              VARCHAR(50),
    `description`         TEXT,

    `created_at`          DATETIME                NOT NULL,
    `updated_at`          DATETIME                NOT NULL
);


CREATE TABLE `role`
(
    `id`          VARCHAR(50) PRIMARY KEY NOT NULL,
    `name`        VARCHAR(255)            NULL,
    `description` VARCHAR(255)            NULL,
    `created_at`  datetime                NOT NULL,
    `updated_at`  datetime                NOT NULL
);

CREATE TABLE `user_role`
(
    `user_id`    VARCHAR(50) NOT NULL,
    `role_id`    VARCHAR(50) NOT NULL,
    `created_at` datetime    NOT NULL,
    `updated_at` datetime    NOT NULL,
    CONSTRAINT PK_USER_ROLE PRIMARY KEY (`user_id`, `role_id`)
);

INSERT INTO backend.user (id, username, email, password, password_token, is_enable, first_name, last_name,
                          avatar, birth_date, gender, description, created_at, updated_at)
VALUES ('02FCZG2SXNZYGQXMPPBB8FV3ZV', 'hoangtl', 'hoangtl@gmail.com',
        '$2a$10$qbCJB7znYS/KD0sFR8f.C.o97a2PYFSGC8KyiKD.nYG7ZT2gaGm2y', null, 1, 'tran', 'hoang', null, null,
        'MALE', null, '2022-01-28 10:23:08', '2022-01-28 10:23:08');
INSERT INTO backend.role (id, name, description, created_at, updated_at)
VALUES ('01FCZG2SXNZYGQXMPPBB8FV3ZV', 'ADMIN', 'role admin', '2022-01-28 10:21:34', '2022-01-28 10:21:36');
INSERT INTO backend.role (id, name, description, created_at, updated_at)
VALUES ('01FCZG2SXNZYGQXMPPBB8FV4ZV', 'USER', 'role user', '2022-01-28 10:21:37', '2022-01-28 10:21:37');
INSERT INTO backend.user_role (user_id, role_id, created_at, updated_at)
VALUES ('02FCZG2SXNZYGQXMPPBB8FV3ZV', '01FCZG2SXNZYGQXMPPBB8FV3ZV', '2022-01-28 10:25:44',
        '2022-01-28 10:25:45');
INSERT INTO backend.user_role (user_id, role_id, created_at, updated_at)
VALUES ('02FCZG2SXNZYGQXMPPBB8FV3ZV', '01FCZG2SXNZYGQXMPPBB8FV4ZV', '2022-01-28 10:25:45',
        '2022-01-28 10:25:46');