-- FIRST DROP ALL TABLES WITH FK
DROP TABLE IF EXISTS payment_order_type;
DROP TABLE IF EXISTS delivery_order_type;
DROP TABLE IF EXISTS payment_accounts;
DROP TABLE IF EXISTS enterprise;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS catalog_description;
DROP TABLE IF EXISTS catalog_ref;
DROP TABLE IF EXISTS discounts;
DROP TABLE IF EXISTS product_description;
DROP TABLE IF EXISTS product_attributes;
DROP TABLE IF EXISTS product_feedback;
DROP TABLE IF EXISTS product_catalog;
DROP TABLE IF EXISTS product_prices;
DROP TABLE IF EXISTS menu_item_ref;
DROP TABLE IF EXISTS feedbacks;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS order_attributes;
DROP TABLE IF EXISTS order_info;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS enterprise_users;
DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS subscribes;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id_user                     INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login_user                  VARCHAR(50)     NOT NULL UNIQUE ,
    name_user                   VARCHAR(150)    NOT NULL,
    password                    VARCHAR(100)    NOT NULL,
    email                       VARCHAR(50)     NOT NULL,
    subscribe                   TINYINT         DEFAULT 1,
    phone                       VARCHAR(20)     ,
    birth_day                   DATE            ,
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
    name                        VARCHAR(30)     NOT NULL UNIQUE
);

CREATE TABLE addresses (
    id_address                  INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_address_type             INT             NOT NULL,
    id_user                     INT             NOT NULL,
    value                       VARCHAR(50)     NOT NULL,
    FOREIGN KEY (id_address_type)               REFERENCES addresses_types(id_address_type)
);

DROP TABLE IF EXISTS catalog;
CREATE TABLE catalog (
    id_catalog                  INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pap_id                      INT             ,
    name                        VARCHAR(180)    NOT NULL,
    translit_name               VARCHAR(200)    NOT NULL UNIQUE,
    link                        VARCHAR(200)    NOT NULL UNIQUE,
    icon                        VARCHAR(30)     ,
    svg_icon                    VARCHAR(50)     ,
    metadesk                    VARCHAR(400)    ,
    metakey                     VARCHAR(400)    ,
    customtitle                 VARCHAR(255)    ,
    create_date                 DATETIME        DEFAULT CURRENT_TIMESTAMP,
    edit_date                   DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_published                TINYINT         DEFAULT 1,
    is_visible                  TINYINT         DEFAULT 1,
    order_catalog               INT             DEFAULT 0
);

CREATE TABLE catalog_description (
    id_catalog_desc             INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_catalog                  INT             NOT NULL UNIQUE,
    short_description           VARCHAR(1000)   ,
    full_description            VARCHAR(5000)   ,
    FOREIGN KEY (id_catalog)                    REFERENCES catalog(id_catalog)
);

CREATE TABLE catalog_ref (
    id_ref_catalog              INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_catalog                  INT             NOT NULL,
    parent_id_catalog           INT             NOT NULL,
    FOREIGN KEY (id_catalog)                    REFERENCES catalog(id_catalog),
    UNIQUE KEY `c_pc` (id_catalog, parent_id_catalog)
);

DROP TABLE IF EXISTS brands;
CREATE TABLE brands (
    id_brand                    INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pap_id                      INT             UNIQUE,
    name                        VARCHAR(100)    NOT NULL,
    translit_name               VARCHAR(120)    NOT NULL UNIQUE,
    icon                        VARCHAR(30)     NOT NULL,
    short_description           VARCHAR(2000)   ,
    full_description            VARCHAR(7000)
);

DROP TABLE IF EXISTS extra_types;
CREATE TABLE extra_types (
    id_extra                    INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(40)     NOT NULL
);

CREATE TABLE products (
    id_product                  INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pap_id                      INT             UNIQUE,
    pnt                         INT             NOT NULL UNIQUE,
    personal_group_name         VARCHAR(150)    ,   
    full_name                   VARCHAR(300)    NOT NULL,
    short_name                  VARCHAR(250)    NOT NULL,
    translit_name               VARCHAR(200)    NOT NULL UNIQUE,
    link                        VARCHAR(200)    UNIQUE,
    id_brand                    INT             ,
    country_from                VARCHAR(30)     ,
    country_made                VARCHAR(30)     ,
    bar_code                    VARCHAR(30)     ,
    measure                     VARCHAR(10)     NOT NULL,
    available_day               INT             DEFAULT 0 NOT NULL,
    base_price                  DOUBLE          NOT NULL,
    vat                         INT             NOT NULL,
    metadesk                    VARCHAR(400)    ,
    metakey                     VARCHAR(400)    ,
    customtitle                 VARCHAR(255)    ,
    create_date                 DATETIME        DEFAULT CURRENT_TIMESTAMP,
    edit_date                   DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_published                TINYINT         DEFAULT 1,
    is_visible                  TINYINT         DEFAULT 0,
    FOREIGN KEY (id_brand)                      REFERENCES brands(id_brand)
);

CREATE TABLE product_description (
    id_product_desc             INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_product                  INT             NOT NULL UNIQUE,
    short_description           VARCHAR(2000)   ,
    full_description            VARCHAR(7000)   ,
    FOREIGN KEY (id_product)                    REFERENCES products(id_product)
);

CREATE TABLE product_attributes (
    id_product_attributes       INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pnt                         INT             NOT NULL,
    name                        VARCHAR(100)    NOT NULL,
    value                       VARCHAR(120)    ,
    order_attr                  INT             NOT NULL,
    FOREIGN KEY (pnt)                           REFERENCES products(pnt)
);

CREATE TABLE product_feedback (
    id_product_feedback         INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pnt                         INT             NOT NULL,
    id_user                     INT,
    user_name                   VARCHAR(100)    ,
    email                       VARCHAR(80)     ,
    ip_address                  VARCHAR(20)     ,    
    rate                        INT             DEFAULT 0 NOT NULL,
    text                        VARCHAR(5000)   ,
    is_published                TINYINT         DEFAULT 0,
    approve                     TINYINT         DEFAULT 0,
    create_date                 DATETIME        DEFAULT CURRENT_TIMESTAMP,
    edit_date                   DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (pnt)                           REFERENCES products(pnt)
);

CREATE TABLE product_catalog (
    id_product                  INT             NOT NULL,
    id_catalog                  INT             NOT NULL,
    order_product               INT             ,
    FOREIGN KEY (id_product)                    REFERENCES products(id_product),
    FOREIGN KEY (id_catalog)                    REFERENCES catalog(id_catalog),
    UNIQUE KEY `product_catalog` (id_product, id_catalog)
);

DROP TABLE IF EXISTS prices_types;
CREATE TABLE prices_types (
    id_price_type               INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(50)     NOT NULL
);

CREATE TABLE product_prices (
    id_price                    INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_price_type               INT             DEFAULT 1 NOT NULL,
    id_product					INT				NOT NULL,
    quantity_start              INT             DEFAULT 1 NOT NULL,
    value                       DOUBLE          NOT NULL,
    FOREIGN KEY (id_price_type)                 REFERENCES prices_types(id_price_type),
    FOREIGN KEY (id_product)                 	REFERENCES products(id_product),
    UNIQUE KEY `p_qs` (id_product, quantity_start)
);

DROP TABLE IF EXISTS discount_types;
CREATE TABLE discount_types (
    id_discount_type            INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type                        VARCHAR(30)
);

CREATE TABLE discounts (
    id_discount                 INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_discount_type            INT             NOT NULL,
    id_product                  INT             NOT NULL UNIQUE,
    value_double                DOUBLE          DEFAULT 0.0,
    value_int                   INT             DEFAULT 0,
    FOREIGN KEY (id_discount_type)              REFERENCES discount_types(id_discount_type),
    FOREIGN KEY (id_product)                    REFERENCES products(id_product)
); 

DROP TABLE IF EXISTS coupons;
CREATE TABLE coupons (
    id_coupon                   INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code                        VARCHAR(100)    NOT NULL,
    token                       VARCHAR(100)    NOT NULL,
    start_date                  DATETIME        DEFAULT CURRENT_TIMESTAMP,
    expire_day                  DATETIME        ,
    used                        TINYINT         DEFAULT 0,
    is_active                   TINYINT         DEFAULT 1,
    value                       INT             DEFAULT 0 NOT NULL
);

DROP TABLE IF EXISTS menu;
CREATE TABLE menu (
    id_menu                     INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(150)    NOT NULL UNIQUE,
    translit_name               VARCHAR(150)    NOT NULL UNIQUE,
    metadesk                    VARCHAR(400)    ,
    metakey                     VARCHAR(400)    ,
    customtitle                 VARCHAR(255)    ,
    create_date                 DATETIME        DEFAULT CURRENT_TIMESTAMP,
    edit_date                   DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS menu_items;
CREATE TABLE menu_items (
    id_menu_item                INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(150)    NOT NULL,
    translit_name               VARCHAR(150)    NOT NULL,
    link                        VARCHAR(100)    NOT NULL,
    metadesk                    VARCHAR(400)    ,
    metakey                     VARCHAR(400)    ,
    customtitle                 VARCHAR(255)    ,
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
    id_user                     INT,
    user_name                   VARCHAR(100)    NOT NULL,
    email                       VARCHAR(80)     NOT NULL,
    ip_address                  VARCHAR(20),    
    rate                        INT,
    text                        VARCHAR(2000)   NOT NULL,
    create_date                 DATETIME        DEFAULT CURRENT_TIMESTAMP,
    edit_date                   DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    approve                     TINYINT         DEFAULT 0,
    is_published                TINYINT         DEFAULT 0
);

DROP TABLE IF EXISTS order_status;
CREATE TABLE order_status (
    id_order_status             INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    status_code                 CHAR(1)         NOT NULL,
    status_name                 VARCHAR(100)    NOT NULL,
    description                 VARCHAR(1000)
);

DROP TABLE IF EXISTS order_types;
CREATE TABLE order_types (
    id_order_type               INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(30)     NOT NULL
);

CREATE TABLE orders (
    id_order                    INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_order_type               INT             NOT NULL,
    token_order                 VARCHAR(100)    NOT NULL UNIQUE,
    order_number                VARCHAR(18)     NOT NULL UNIQUE,
    pap_order_number            VARCHAR(20)     UNIQUE,
    id_user                     INT             DEFAULT 0 NOT NULL,
    id_order_status             INT             DEFAULT 1 NOT NULL,
    total                       DOUBLE          NOT NULL,
    total_with_vat              DOUBLE          NOT NULL,
    vat_total                   DOUBLE          NOT NULL,
    total_discount              DOUBLE          ,
    coupon_id                   INT             ,
    payment_cost                DOUBLE          DEFAULT 0.0 NOT NULL,
    shipmentcost                DOUBLE          DEFAULT 0.0 NOT NULL,
    final_total                 DOUBLE          NOT NULL,
    final_total_with_vat        DOUBLE          NOT NULL,
    create_date                 DATETIME        DEFAULT CURRENT_TIMESTAMP,
    edit_date                   DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_order_type)                 REFERENCES order_types(id_order_type),
    FOREIGN KEY (id_order_status)               REFERENCES order_status(id_order_status)
);

CREATE TABLE order_items (
    id_order                    INT             NOT NULL,
    id_product                  INT             NOT NULL,
    pnt                         INT             NOT NULL,
    product_full_name           VARCHAR(350)    NOT NULL,
    VAT                         INT             NOT NULL,
    quantity                    INT             NOT NULL,
    base_price                  DOUBLE          NOT NULL,
    base_price_with_vat         DOUBLE          NOT NULL,
    discount_amount             DOUBLE          NOT NULL,
    final_price                 DOUBLE          NOT NULL,
    final_price_with_vat        DOUBLE          NOT NULL,
    total                       DOUBLE          NOT NULL,
    total_with_vat              DOUBLE          NOT NULL,
    total_discount              DOUBLE          NOT NULL,
    total_vat                   DOUBLE          NOT NULL,
    FOREIGN KEY (id_order)      REFERENCES orders(id_order),
    FOREIGN KEY (id_product)    REFERENCES products(id_product),
    UNIQUE KEY `o_p` (id_order, id_product)
);

CREATE TABLE order_attributes (
    id_order                    INT             NOT NULL,
    name                        VARCHAR(250)    NOT NULL,
    value                       VARCHAR(1000)   ,
    order_attribute             INT             ,
    FOREIGN KEY (id_order)      REFERENCES orders(id_order),
    UNIQUE KEY `o_n` (id_order, name)
);

CREATE TABLE order_info (
    id_order                    INT             NOT NULL,
    customer_name               VARCHAR(250)    ,
    enterprise_name             VARCHAR(450)    ,
    unp                         VARCHAR(20)     ,
    email                       VARCHAR(100)    NOT NULL,
    phone                       VARCHAR(20)     ,
    payment_name                VARCHAR(250)    ,
    payment_account             VARCHAR(30)     ,
    payment_bank_name           VARCHAR(450)    ,
    payment_bank_code           VARCHAR(8)      ,
    shipment_name               VARCHAR(250)    ,
    shipment_address            VARCHAR(250)    ,
    user_notes                  VARCHAR(2000)   ,
    FOREIGN KEY (id_order)      REFERENCES orders(id_order)
);

DROP TABLE IF EXISTS stock_place;
CREATE TABLE stock_place (
    id_stock_place              INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(50)     NOT NULL,
    address                     VARCHAR(200)    NOT NULL,
    phone                       VARCHAR(150)    ,
    email                       VARCHAR(50)     ,
    description                 VARCHAR(500)    ,
    order_places                INT
);

CREATE TABLE stock (
    id_stock                    INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_stock_place              INT             DEFAULT 1 NOT NULL,
    id_product                  INT             NOT NULL,
    quantity_available          INT             DEFAULT 0 NOT NULL,
    FOREIGN KEY (id_stock_place)                REFERENCES stock_place(id_stock_place),
    UNIQUE KEY `s_p` (id_stock_place, id_product)
);

DROP TABLE IF EXISTS mail_lists;
CREATE TABLE mail_lists (
    id_mail_list                INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(100)    NOT NULL
);

CREATE TABLE subscribes (
    id_subscribe                INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_mail_list                INT             NOT NULL,
    email                       VARCHAR(100)    NOT NULL,
    create_date                 DATETIME        DEFAULT CURRENT_TIMESTAMP,
    updated_date                DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_mail_list)                  REFERENCES mail_lists(id_mail_list),
    UNIQUE KEY `e_l` (id_mail_list, email)
);

DROP TABLE IF EXISTS callbacks;
CREATE TABLE callbacks (
    id_callback                 INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(100)    NOT NULL,
    phone                       VARCHAR(30)     NOT NULL,
    comment                     VARCHAR(2000)   ,
    responded                   TINYINT         DEFAULT 0,
    create_date                 DATETIME        DEFAULT CURRENT_TIMESTAMP,
    responded_date              DATETIME        ON UPDATE CURRENT_TIMESTAMP
);




DROP TABLE IF EXISTS delivery;
CREATE TABLE delivery (
    id_delivery                 INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(50)     NOT NULL,
    short_description           VARCHAR(1000)   ,
    full_description            VARCHAR(5000)   ,
    link                        VARCHAR(200)    ,
    icon                        VARCHAR(150)
);

CREATE TABLE delivery_order_type (
    id_delivery                 INT             NOT NULL,
    id_order_type               INT             NOT NULL,
    min_cart_total              DOUBLE          DEFAULT 0.0,
    price                       DOUBLE          DEFAULT 0.0,
    FOREIGN KEY (id_delivery)                   REFERENCES delivery(id_delivery),
    FOREIGN KEY (id_order_type)                 REFERENCES order_types(id_order_type),
    UNIQUE KEY `d_o_c_p` (id_delivery, id_order_type, min_cart_total, price)
);

DROP TABLE IF EXISTS payment;
CREATE TABLE payment (
    id_payment                  INT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(50)     NOT NULL,
    short_description           VARCHAR(1000)   ,
    full_description            VARCHAR(5000)   ,
    link                        VARCHAR(200)    ,
    icon                        VARCHAR(150)
);

CREATE TABLE payment_order_type (
    id_payment                  INT             NOT NULL,
    id_order_type               INT             NOT NULL,
    min_cart_total              DOUBLE          DEFAULT 0.0,
    price                       DOUBLE          DEFAULT 0.0,
    FOREIGN KEY (id_payment)                    REFERENCES payment(id_payment),
    FOREIGN KEY (id_order_type)                 REFERENCES order_types(id_order_type),
    UNIQUE KEY `p_o_c_p` (id_payment, id_order_type, min_cart_total, price)
);