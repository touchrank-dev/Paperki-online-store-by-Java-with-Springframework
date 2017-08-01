DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id_user                     INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login_user                  VARCHAR(50)     NOT NULL UNIQUE,
    name_user                   VARCHAR(150)    NOT NULL,
    password_user               VARCHAR(8)      NOT NULL
);

DROP TABLE IF EXISTS categories;
CREATE TABLE categories (
    id_category                 INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pap_id_category             INT,
    name                        VARCHAR(150)    NOT NULL,
    translit_name_category      VARCHAR(200)    NOT NULL UNIQUE,
    link                        VARCHAR(200)    UNIQUE,
    icon                        VARCHAR(100),
    order_category              INT             NOT NULL
);

DROP TABLE IF EXISTS categories_descriptions;
CREATE TABLE categories_descriptions (
    id_category_desc            INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_category                 INT             NOT NULL UNIQUE,
    short_description           VARCHAR(300),
    full_description            VARCHAR(2000)
);

DROP TABLE IF EXISTS categories_ref;
CREATE TABLE categories_ref (
    id_ref_categories           INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_category                 INT             NOT NULL,
    parent_id_category          INT DEFAULT(0)  NOT NULL
);