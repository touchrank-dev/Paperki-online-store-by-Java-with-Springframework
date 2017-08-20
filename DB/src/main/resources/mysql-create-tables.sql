-- FIRST DROP ALL TABLES WITH FK
DROP TABLE IF EXISTS payment_accounts;
DROP TABLE IF EXISTS enterprise;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS catalog_description;
DROP TABLE IF EXISTS catalog_ref;
DROP TABLE IF EXISTS product_description;
DROP TABLE IF EXISTS product_attribute;
DROP TABLE IF EXISTS product_catalog;
DROP TABLE IF EXISTS product_prices;
DROP TABLE IF EXISTS menu_item_ref;
DROP TABLE IF EXISTS feedbacks;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS enterprise_users;
DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS products;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id_user                     INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login_user                  VARCHAR(50)     CHARACTER SET utf8 NOT NULL UNIQUE ,
    name_user                   VARCHAR(150)    CHARACTER SET utf8 NOT NULL,
    password                    VARCHAR(100)    CHARACTER SET utf8 NOT NULL,
    email                       VARCHAR(50)     CHARACTER SET utf8 NOT NULL,
    subscribe                   TINYINT         DEFAULT 1,
    phone                       VARCHAR(20)     CHARACTER SET utf8,
    birth_day                   DATE,
    create_date                 DATETIME        DEFAULT CURRENT_TIMESTAMP,
    edit_date                   DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_enabled                  TINYINT         DEFAULT 0
);

CREATE TABLE enterprise (
    id_enterprise               INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_user                     INT             NOT NULL UNIQUE,
    unp                         VARCHAR(10)     NOT NULL UNIQUE,
    name                        VARCHAR(400)    NOT NULL,
    FOREIGN KEY (id_user)                       REFERENCES users(id_user)
);

CREATE TABLE payment_accounts(
    id_payment_account          INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_enterprise               INT             NOT NULL,
    bank_name                   VARCHAR(150)    NOT NULL,
    bank_code                   INT             NOT NULL,
    account_number              VARCHAR(20)     NOT NULL,
    is_primary                  TINYINT         DEFAULT 1,
    FOREIGN KEY (id_enterprise)                 REFERENCES enterprise(id_enterprise),
    UNIQUE KEY `e_a` (id_enterprise, account_number)
);

DROP TABLE IF EXISTS addresses_types;
CREATE TABLE addresses_types (
    id_address_type             INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(30)     CHARACTER SET utf8 NOT NULL UNIQUE
);

CREATE TABLE addresses (
    id_address                  INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_address_type             INT             NOT NULL,
    id_user                     INT             NOT NULL,
    value                       VARCHAR(50)     CHARACTER SET utf8 NOT NULL,
    FOREIGN KEY (id_address_type)               REFERENCES addresses_types(id_address_type)
);

DROP TABLE IF EXISTS catalog;
CREATE TABLE catalog (
    id_catalog                  INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pap_id                      INT             UNIQUE,
    name                        VARCHAR(180)    CHARACTER SET utf8 NOT NULL,
    translit_name               VARCHAR(200)    CHARACTER SET utf8 NOT NULL UNIQUE,
    link                        VARCHAR(200)    CHARACTER SET utf8 NOT NULL UNIQUE,
    icon                        VARCHAR(30)     CHARACTER SET utf8 NOT NULL,
    svg_icon                    VARCHAR(50)     CHARACTER SET utf8,
    metadesk                    VARCHAR(400)    CHARACTER SET utf8,
    metakey                     VARCHAR(400)    CHARACTER SET utf8,
    customtitle                 VARCHAR(255)    CHARACTER SET utf8,
    create_date                 DATETIME        DEFAULT CURRENT_TIMESTAMP,
    edit_date                   DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_published                TINYINT         DEFAULT 1,
    is_visible                  TINYINT         DEFAULT 1,
    order_catalog               INT             NOT NULL
);

CREATE TABLE catalog_description (
    id_catalog_desc             INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_catalog                  INT             NOT NULL UNIQUE,
    short_description           VARCHAR(1000)   CHARACTER SET utf8,
    full_description            VARCHAR(5000)   CHARACTER SET utf8,
    FOREIGN KEY (id_catalog)                    REFERENCES catalog(id_catalog)
);

CREATE TABLE catalog_ref (
    id_ref_catalog              INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_catalog                  INT             NOT NULL,
    parent_id_catalog           INT             DEFAULT 0,
    FOREIGN KEY (id_catalog)                    REFERENCES catalog(id_catalog)
);

DROP TABLE IF EXISTS brands;
CREATE TABLE brands (
    id_brand                    INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pap_id                      INT             UNIQUE,
    name                        VARCHAR(100)    CHARACTER SET utf8 NOT NULL,
    translit_name               VARCHAR(120)    CHARACTER SET utf8 NOT NULL UNIQUE,
    icon                        VARCHAR(30)     CHARACTER SET utf8 NOT NULL,
    short_description           VARCHAR(2000)   CHARACTER SET utf8,
    full_description            VARCHAR(7000)   CHARACTER SET utf8
);

CREATE TABLE products (
    id_product                  INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pap_id                      INT             UNIQUE,
    pnt                         INT             NOT NULL UNIQUE,
    full_name                   VARCHAR(250)    CHARACTER SET utf8 NOT NULL UNIQUE,
    short_name                  VARCHAR(250)    CHARACTER SET utf8 NOT NULL,
    translit_name               VARCHAR(200)    CHARACTER SET utf8 NOT NULL UNIQUE,
    link                        VARCHAR(200)    CHARACTER SET utf8 UNIQUE,
    id_brand                    INT,
    country_from                VARCHAR(30)     CHARACTER SET utf8,
    country_made                VARCHAR(30)     CHARACTER SET utf8 NOT NULL,
    bar_code                    VARCHAR(30)     CHARACTER SET utf8,
    measure                     VARCHAR(10)     CHARACTER SET utf8 NOT NULL,
    available_day               INT             DEFAULT 0 NOT NULL,
    vat                         INT             DEFAULT 0 NOT NULL,
    metadesk                    VARCHAR(400)    CHARACTER SET utf8,
    metakey                     VARCHAR(400)    CHARACTER SET utf8,
    customtitle                 VARCHAR(255)    CHARACTER SET utf8,
    create_date                 DATETIME        DEFAULT CURRENT_TIMESTAMP,
    edit_date                   DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_published                TINYINT         DEFAULT 0,
    is_visible                  TINYINT         DEFAULT 0,
    FOREIGN KEY (id_brand)                      REFERENCES brands(id_brand)
);

DROP TABLE IF EXISTS prices_types;
CREATE TABLE prices_types (
    id_price_type               INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(20)     NOT NULL
);

CREATE TABLE product_prices (
    id_price                    INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_price_type               INT             NOT NULL,
    id_product					INT				NOT NULL,
    quatity_start               INT             DEFAULT 0 NOT NULL,
    quatity_end                 INT             DEFAULT 0 NOT NULL,
    value                       DOUBLE          NOT NULL,
    FOREIGN KEY (id_price_type)                 REFERENCES prices_types(id_price_type),
    FOREIGN KEY (id_product)                 	REFERENCES products(id_product)
);

DROP TABLE IF EXISTS coupons;
CREATE TABLE coupons (
    id_coupon                   INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code                        VARCHAR(100)    NOT NULL,
    token                       VARCHAR(100)    NOT NULL,
    start_date                  DATETIME        DEFAULT CURRENT_TIMESTAMP,
    expire_day                  DATETIME,
    used                        TINYINT         DEFAULT 0,
    is_active                   TINYINT         DEFAULT 1,
    value                       INT             DEFAULT 0 NOT NULL
);

CREATE TABLE product_description (
    id_product_desc             INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_product                  INT             NOT NULL UNIQUE,
    short_description           VARCHAR(2000)   CHARACTER SET utf8,
    full_description            VARCHAR(7000)   CHARACTER SET utf8,
    FOREIGN KEY (id_product)                    REFERENCES products(id_product)
);

CREATE TABLE product_attribute (
    id_product_attribute        INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_product                  INT             NOT NULL,
    name                        VARCHAR(100)    CHARACTER SET utf8,
    value                       VARCHAR(100)    CHARACTER SET utf8,
    FOREIGN KEY (id_product)                    REFERENCES products(id_product)
);

CREATE TABLE product_catalog (
    id_product                  INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_catalog                  INT             NOT NULL,
    order_product               INT             NOT NULL,
    FOREIGN KEY (id_product)                    REFERENCES products(id_product),
    FOREIGN KEY (id_catalog)                    REFERENCES catalog(id_catalog),
    UNIQUE KEY `product_catalog` (id_product, id_catalog)
);

DROP TABLE IF EXISTS menu;
CREATE TABLE menu (
    id_menu                     INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(150)    CHARACTER SET utf8 NOT NULL UNIQUE,
    translit_name               VARCHAR(150)    CHARACTER SET utf8 NOT NULL UNIQUE,
    metadesk                    VARCHAR(400)    CHARACTER SET utf8,
    metakey                     VARCHAR(400)    CHARACTER SET utf8,
    customtitle                 VARCHAR(255)    CHARACTER SET utf8,
    create_date                 DATETIME        DEFAULT CURRENT_TIMESTAMP,
    edit_date                   DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS menu_items;
CREATE TABLE menu_items (
    id_menu_item                INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(150)    CHARACTER SET utf8 NOT NULL,
    translit_name               VARCHAR(150)    CHARACTER SET utf8 NOT NULL,
    link                        VARCHAR(100)    CHARACTER SET utf8 NOT NULL,
    metadesk                    VARCHAR(400)    CHARACTER SET utf8,
    metakey                     VARCHAR(400)    CHARACTER SET utf8,
    customtitle                 VARCHAR(255)    CHARACTER SET utf8,
    create_date                 DATETIME        DEFAULT CURRENT_TIMESTAMP,
    edit_date                   DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_published                TINYINT         DEFAULT 0,
    order_item                  INT             NOT NULL,
    UNIQUE KEY `name_trName_link` (name, translit_name, link)
);

CREATE TABLE menu_item_ref (
    id_menu_item_ref            INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_menu                     INT             NOT NULL,
    id_menu_item                INT             NOT NULL,
    FOREIGN KEY (id_menu)                       REFERENCES menu(id_menu),
    FOREIGN KEY (id_menu_item)                  REFERENCES menu_items(id_menu_item),
    UNIQUE KEY `menu_item` (id_menu, id_menu_item)
);

CREATE TABLE feedbacks (
    id_feedback                 INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_user                     INT             NOT NULL,
    text                        VARCHAR(2000)   CHARACTER SET utf8 NOT NULL,
    create_date                 DATETIME        DEFAULT CURRENT_TIMESTAMP,
    edit_date                   DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    aproove                     TINYINT         DEFAULT 0,
    is_published                TINYINT         DEFAULT 0,
    FOREIGN KEY (id_user)                       REFERENCES users(id_user)
);

CREATE TABLE orders (
    id_order                    INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    token_order                 INT             NOT NULL UNIQUE,
    order_number                INT             NOT NULL UNIQUE,
    pap_order_number            INT             NOT NULL UNIQUE,
    id_user                     INT             NOT NULL,
    id_order_status             INT             NOT NULL,
    create_date                 DATETIME        DEFAULT CURRENT_TIMESTAMP,
    edit_date                   DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_user)                       REFERENCES users(id_user)
);

DROP TABLE IF EXISTS order_status;
CREATE TABLE order_status (
    id_order_status             INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    status_code                 CHAR(1)         CHARACTER SET utf8 NOT NULL,
    status_name                 VARCHAR(100)    CHARACTER SET utf8 NOT NULL,
    description                 VARCHAR(1000)   CHARACTER SET utf8
);

CREATE TABLE stock (
    id_stock                    INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(50)     NOT NULL,
    id_product                  INT             NOT NULL,
    quantity                    INT             DEFAULT 0 NOT NULL,
    FOREIGN KEY (id_product)                    REFERENCES products(id_product)
);
