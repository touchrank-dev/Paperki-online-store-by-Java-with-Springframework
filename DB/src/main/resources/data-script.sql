SET NAMES utf8;

/*INSERT INTO catalog (name, translit_name, link, icon, order_catalog)
VALUES  ('Бумага и бумажная продукция', 'bumaga-i-bymazhnaz produkcia', '/catalog/bumaga-i-bymazhnaz produkcia', 'hm1.png', 1),
        ('Канцелярские товары', 'kanceliarskie-tovary', '/catalog/kanceliarskie-tovary', 'hm2.png', 2),
        ('Бумага офисная белая', 'bumaga-ofisnaja-belaja', '/catalog/bumaga-ofisnaja-belaja', 'hcm1.png', 1),
        ('Бумага офисная цветная', 'bumaga-ofisnaja-cvetnaja', '/catalog/bumaga-ofisnaja-cvetnaja', 'hcm2.png', 2),
        ('Бумага писчая, газетная', 'bumaga-pischaja-gazetnaja', '/catalog/bumaga-pischaja-gazetnaja', 'hcm3.png', 3),
        ('Бумага для факса', 'bumaga-dlya-faksa', '/catalog/bumaga-dlya-faksa', 'hcm4.png', 4),
        ('Клей', 'klej', '/catalog/klej', 'hcm1.png', 1),
        ('Корректоры', 'korrektory', '/catalog/korrektory', 'hcm2.png', 2),
        ('Дыроколы', 'dyrokoly', '/catalog/dyrokoly', 'hcm4.png', 4);

INSERT INTO catalog_ref (id_catalog, parent_id_catalog)
VALUES  (1, 0),(2, 0),
        (3, 1),(4, 1),(5, 1),(6, 1),
        (7, 2),(8, 2),(9, 2);*/

INSERT INTO menu (name, translit_name)
VALUES  ('Главное', 'root'),
        ('Помощь', 'help'),
        ('Новости', 'blog');

INSERT INTO menu_items (name, translit_name, link, order_item)
VALUES  ('О компании', 'about', '/about', 1),
        ('Помощь', 'help', '/help', 2),
        ('Каталог товаров', 'catalog', '/catalog', 3),
        ('Новости', 'blog', '/blog', 4),
        ('База знаний', 'knowledgebase', '/knowledgebase', 5),
        ('Доставка и оплата', 'delivery-and-payment', '/delivery-and-payment', 6),
        ('Отзывы', 'feedback', '/feedback', 7),
        ('Контакты', 'contacts', '/contacts', 8),
        ('PAP-бонус', 'bonus', '/bonus', 3);

INSERT INTO menu_item_ref (id_menu, id_menu_item)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8);

/*INSERT INTO brands (name, translit_name, icon)
VALUES ('Бренд не определен', 'none', 'none.png'),
       ('Ксерокс', 'xerox', 'xerox.png'),
       ('InternationPaper', 'internation-paper', 'internation-paper.png');*/

INSERT INTO extra_types (name)
VALUES ('Хиты продаж'),('Супер цена'),('Новинки'),('Специальное предложение');

/*
INSERT INTO products (pnt, full_name, short_name, translit_name, id_brand, country_made, measure, base_price, vat)
VALUES (9491, 'Бумага офисная Ксерокс Перформер А4, 80 г/м², 500 л.', 'А4, 80 г/м², 500 л.', 'bumaga-ofisnaja-xerox-performer-A4-80-gm2-500l', 2, 'РФ', 'пач.', 5.56, 20),
       (9496, 'Бумага офисная Ксерокс Перформер А3, 80 г/м², 500 л.', 'А3, 80 г/м², 500 л.', 'bumaga-ofisnaja-xerox-performer-A3-80-gm2-500l', 2, 'РФ', 'пач.', 11.12, 20),
       (17465, 'Бумага офисная SVETOCOPY А4, 80 г/м², 500 л.', 'А4, 80 г/м², 500 л.', 'bumaga-ofisnaya-svetocopy-A4', 3, 'РФ', 'пач.', 5.56, 20),
       (36180, 'Бумага офисная SVETOCOPY А3, 80 г/м², 500 л.', 'А3, 80 г/м², 500 л.', 'bumaga-ofisnaya-svetocopy-A3', 3, 'РФ', 'пач.', 11.12, 20),
       (6402, 'Бумага цветная Радуга А4, 80г/м², 250 л., 5 цветов', 'А4, 80г/м², 250л., 5 цветов', 'bumaga-tsvetnaya-raduga-a4-80g-m2-250l-5-tsvetov', 1, 'РБ', 'пач.', 6.12, 20);

INSERT INTO product_description (id_product, short_description, full_description)
VALUES (1, 'Бумага класса С. Надежная и экономичная - для повседневных работ. Совместима со всеми видами копиров и принтеров. Произведена в соответствии с международными требованиями XEROX. Не содержит древесных смол и газообразного хлора. Белизна - 146 CIE. Толщина - 104 мкм. Непрозрачность - 89%. Срок архивного хранения > 150 лет. Копир *** Лазерный принтер *** Струйный принтер **', 'Бумага класса С+. Гладкие листы практически беспыльны, отличаются низкой электростатичностью. Используется для печати на высокоскоростных копирах и принтерах и совместима с любым видом офисной техники. Бумага может применяться для печати на офсетных машинах.'),
       (2, 'Бумага класса С. Надежная и экономичная - для повседневных работ. Совместима со всеми видами копиров и принтеров. Произведена в соответствии с международными требованиями XEROX. Не содержит древесных смол и газообразного хлора. Белизна - 146 CIE. Толщина - 104 мкм. Непрозрачность - 89%. Срок архивного хранения > 150 лет. Копир *** Лазерный принтер *** Струйный принтер **', 'Бумага класса С+. Гладкие листы практически беспыльны, отличаются низкой электростатичностью. Используется для печати на высокоскоростных копирах и принтерах и совместима с любым видом офисной техники. Бумага может применяться для печати на офсетных машинах.'),
       (3, '', ''),
       (4, '', ''),
       (5, '', 'Плотность 80 г/м2, 5 цветов в пачке (желтый, розовый, голубой, оранжевый, зеленый). Предназначена для печати на любых видах принтеров, копирования на ксероксах, для письма. Упаковка в коробку по 8 пачек.');
*/

/*
INSERT INTO product_attributes (pnt, name, value, order_attr)
VALUES  (9491, 'Формат', 'A4', 1),
        (9491, 'Плотность', '80г/м²', 2),
        (9491, 'Кол-во листов в пачке', '500 л.', 3);
*/

INSERT INTO prices_types (name)
VALUES ('Стандартная');

/*
INSERT INTO product_prices (id_product, quantity_start, value)
VALUES (1, 1, 5.56),
       (1, 20, 5.35),
       (1, 60, 5.26),
       (1, 120, 5.18),
       (1, 240, 5.13);
*/

INSERT INTO discount_types (type)
VALUES ('OVERRIDE'),('PROCENT'),('SUBSTRACT');

/*
INSERT INTO discounts (id_discount_type, id_product, value_double, value_int)
VALUES (1, 1, 3.3, 0);

INSERT INTO product_catalog (id_product, id_catalog, order_product)
VALUES (1, 3, 1),(2, 3, 2),
       (3, 3, 3),(4, 3, 4),
       (5, 4, 1);
*/

INSERT INTO stock_place (name, address, phone, email, description, order_places)
VALUES ('ООО "Паперки"', 'г. Брест, ул. Янки Купалы, 88 В', '8-0162 54-33-54, 8-0162 54-99-54', 'paperki@paperki.by', 'Пн-Пт с 8.30 до 17.00', 1),
       ('Магазин "Канцелярские штучки" на Карбышева', 'г. Брест, ул. Карбышева, 37', 'мтс. +375 (29) 835-87-95, гор. 8 (0162) 20-87-95', 'paperki2@paperki.by', 'ПН-ПТ: 10.00 - 19.00,</br> СБ: 10.00 - 17.00,</br> ВС: Выходной', 2);

/*
INSERT INTO stock (id_product, quantity_available)
VALUES (1, 3086),(2, 54),(3, 1054),(4, 234),(5, 45);
*/

INSERT INTO mail_lists(name)
VALUES ('Новостная рассылка');

INSERT INTO order_types (name)
VALUES ('Физ лиц'), ('Юр лиц');

INSERT INTO delivery (name, short_description, full_description, link, icon)
VALUES ('САМОВЫВОЗ СО СКЛАДА В БРЕСТЕ', 'Адрес пункта самовывоза: Брест Я.Купалы 88 в', '', '', ''),
       ('ДОСТАВКА "Паперки"', 'Пн - Пт с 8.30 - 16.00', '', '', '');

INSERT INTO delivery_order_type (id_delivery, id_order_type, min_cart_total, price)
VALUES (1, 1, 0.0, 0.0), (1, 2, 0.0, 0.0),
       (2, 1, 30.0, 0.0), (2, 1, 0.0, 5.0), (2, 2, 10.0, 0.0);

INSERT INTO payment (name, short_description, full_description, link, icon)
VALUES ('Безналичный расчет', '', '', '', ''),
       ('Наличный расчет', '', '', '', ''), 
       ('через систему ”Расчет“ (ЕРИП)', '', '', '', '');

INSERT INTO payment_order_type (id_payment, id_order_type, min_cart_total, price)
VALUES (1, 2, 0.0, 0.0), 
       (2, 1, 0.0, 0.0), (3, 1, 0.0, 0.0);

INSERT INTO order_status (status_code, status_name)
VALUES ('P', 'Ожидает подтверждения'),
       ('C', 'Подтвержден'),
       ('D', 'Отменен'),
       ('S', 'Отправлен'),
       ('X', 'Доставлен');