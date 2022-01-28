DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user`
(
    `id`             VARCHAR(50) PRIMARY KEY NOT NULL,
    `username`       VARCHAR(100) UNIQUE     NOT NULL,
    `email`          VARCHAR(100) UNIQUE     NOT NULL,
    `password`       VARCHAR(100)            NOT NULL,
    `password_token` VARCHAR(255),
    `is_enable`      TINYINT(1)              NOT NULL,

    `first_name`     VARCHAR(80),
    `last_name`      VARCHAR(80),
    `avatar`         VARCHAR(100),
    `birth_date`     DATE DEFAULT NULL       NULL,
    `gender`         VARCHAR(50),
    `description`    TEXT,

    `created_at`     DATETIME                NOT NULL,
    `updated_at`     DATETIME                NOT NULL
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