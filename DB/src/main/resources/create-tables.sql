DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id_user                     INT             NOT NULL AUTO_INCREMENT,
    login_user                  VARCHAR(50)     NOT NULL UNIQUE,
    name_user                   VARCHAR(150)    NOT NULL,
    password_user               VARCHAR(8)      NOT NULL,
    PRIMARY KEY (id_user)
);

DROP TABLE IF EXISTS categories;
CREATE TABLE categories (
    id_category                 INT            NOT NULL AUTO_INCREMENT PRIMARY,
    pap_id_category             INT,
    name_category               VARCHAR(150)   NOT NULL,
    translit_name_category      VARCHAR(200)   NOT NULL UNIQUE,
    link_category               VARCHAR(200),
    child_id_category           INT,
    parent_id_category          INT,
    icon                        VARCHAR(100),
    order                       INT            NOT NULL
);