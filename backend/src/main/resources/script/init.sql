CREATE TABLE permission
(
    id            VARCHAR(50)           NOT NULL,
    name          VARCHAR(255)          NULL,
    `description` VARCHAR(255)          NULL,
    app_id        VARCHAR(50)           NULL,
    created_at    datetime DEFAULT NULL NULL,
    updated_at    datetime DEFAULT NULL NULL,
    CONSTRAINT PK_PERMISSION PRIMARY KEY (id)
);

CREATE TABLE `role`
(
    id            VARCHAR(50)           NOT NULL,
    name          VARCHAR(255)          NULL,
    `description` VARCHAR(255)          NULL,
    app_id        VARCHAR(50)           NULL,
    created_at    datetime DEFAULT NULL NULL,
    updated_at    datetime DEFAULT NULL NULL,
    CONSTRAINT PK_ROLE PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         VARCHAR(50)           NOT NULL,
    user_name  VARCHAR(100)          NULL,
    mail       VARCHAR(100)          NULL,
    created_at datetime DEFAULT NULL NULL,
    updated_at datetime DEFAULT NULL NULL,
    CONSTRAINT PK_USER PRIMARY KEY (id)
);

CREATE TABLE role_permission
(
    role_id       VARCHAR(50)           NOT NULL,
    permission_id VARCHAR(50)           NOT NULL,
    created_at    datetime DEFAULT NULL NULL,
    updated_at    datetime DEFAULT NULL NULL,
    CONSTRAINT PK_ROLE_PERMISSION PRIMARY KEY (role_id, permission_id)
);


CREATE TABLE user_role
(
    user_id    VARCHAR(50)           NOT NULL,
    role_id    VARCHAR(50)           NOT NULL,
    created_at datetime DEFAULT NULL NULL,
    updated_at datetime DEFAULT NULL NULL,
    CONSTRAINT PK_USER_ROLE PRIMARY KEY (user_id, role_id)
);