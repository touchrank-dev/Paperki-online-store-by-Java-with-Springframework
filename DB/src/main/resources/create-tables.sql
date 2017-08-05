DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id_user                     INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login_user                  VARCHAR(50)     NOT NULL UNIQUE,
    name_user                   VARCHAR(150)    NOT NULL,
    password_user               VARCHAR(100)    NOT NULL
);

DROP TABLE IF EXISTS categories;
CREATE TABLE categories (
    id_category                 INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pap_id                      INT             UNIQUE,
    name                        VARCHAR(150)    NOT NULL,
    translit_name               VARCHAR(200)    NOT NULL UNIQUE,
    link                        VARCHAR(200)    UNIQUE,
    icon                        VARCHAR(100),
    create_date                 DATETIME        DEFAULT(NOW()) NOT NULL,
    edit_date                   DATETIME        DEFAULT(NOW()),
    is_published                TINYINT         DEFAULT(0),
    order_category              INT             NOT NULL
);

DROP TABLE IF EXISTS categories_description;
CREATE TABLE categories_description (
    id_category_desc            INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_category                 INT             NOT NULL UNIQUE,
    short_description           VARCHAR(500),
    full_description            VARCHAR(2000),
    FOREIGN KEY (id_category)                   REFERENCES categories(id_category)
);

DROP TABLE IF EXISTS categories_ref;
CREATE TABLE categories_ref (
    id_ref_category             INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_category                 INT             NOT NULL,
    parent_id_category          INT             DEFAULT(0)  NOT NULL,
    FOREIGN KEY (id_category)                   REFERENCES categories(id_category)
);

DROP TABLE IF EXISTS product;
CREATE TABLE product (
    id_product                  INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pap_id                      INT             UNIQUE,
    pnt                         INT             UNIQUE,
    name                        VARCHAR(150)    NOT NULL UNIQUE,
    translit_name               VARCHAR(200)    NOT NULL UNIQUE,
    link                        VARCHAR(200)    UNIQUE,
    create_date                 DATETIME        DEFAULT(NOW()) NOT NULL,
    edit_date                   DATETIME        DEFAULT(NOW()),
    is_published                TINYINT         DEFAULT(0)
);

DROP TABLE IF EXISTS product_description;
CREATE TABLE product_description (
    id_product_desc             INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_product                  INT             NOT NULL UNIQUE,
    short_description           VARCHAR(500),
    full_description            VARCHAR(2000),
    FOREIGN KEY (id_product)                    REFERENCES product(id_product)
);

DROP TABLE IF EXISTS product_attribute;
CREATE TABLE product_attribute (
    id_product_attribute        INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_product                  INT             NOT NULL,
    name                        VARCHAR(100),
    value                       VARCHAR(100),
    FOREIGN KEY (id_product)                    REFERENCES product(id_product)
);

DROP TABLE IF EXISTS product_category;
CREATE TABLE product_category (
    id_product                  INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_category                 INT             NOT NULL,
    order_product               INT             NOT NULL,
    FOREIGN KEY (id_product)                    REFERENCES product(id_product),
    FOREIGN KEY (id_category)                   REFERENCES categories(id_category),
    UNIQUE KEY `product_category` (id_product, id_category)
);

DROP TABLE IF EXISTS menu;
CREATE TABLE menu (
    id_menu                     INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(150)    NOT NULL UNIQUE,
    translit_name               VARCHAR(150)    NOT NULL UNIQUE
);

DROP TABLE IF EXISTS menu_items;
CREATE TABLE menu_items (
    id_menu_item                INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_menu                     INT             NOT NULL,
    name                        VARCHAR(150)    NOT NULL,
    translit_name               VARCHAR(150)    NOT NULL,
    link                        VARCHAR(100)    NOT NULL,
    create_date                 DATETIME        DEFAULT(NOW()) NOT NULL,
    edit_date                   DATETIME        DEFAULT(NOW()),
    is_published                TINYINT         DEFAULT(0),
    order_item                  INT             NOT NULL,
    FOREIGN KEY (id_menu)                       REFERENCES menu(id_menu),
    UNIQUE KEY `menu_name_trName_link` (id_menu, name, translit_name, link)
);

DROP TABLE IF EXISTS feedbacks;
CREATE TABLE feedbacks (
    id_feedback                 INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_user                     INT             NOT NULL,
    text                        VARCHAR(2000)   NOT NULL,
    create_date                 DATETIME        DEFAULT(NOW()) NOT NULL,
    edit_date                   DATETIME        DEFAULT(NOW()),
    aproove                     TINYINT         DEFAULT(0),
    is_published                TINYINT         DEFAULT(0),
    FOREIGN KEY (id_user)                       REFERENCES users(id_user)
);
