SET NAMES utf8;

INSERT INTO catalog (name, translit_name, link, icon, order_catalog)
VALUES  ('Бумага и бумажная продукция', 'bumaga-i-bymazhnaz produkcia', '/catalog/bumaga-i-bymazhnaz produkcia', 'hm1.png', 1),
        ('Канцелярские товары', 'kanceliarskie-tovary', '/catalog/kanceliarskie-tovary', 'hm2.png', 2),
        ('Пишущие и рисовальные принадлежности', 'pishushie-i-risovalnye-prinadlezhnosti', '/catalog/pishushie-i-risovalnye-prinadlezhnosti', 'hm3.png', 3),
        ('Файлы, папки, портфели', 'fajly-papki-portfeli', '/catalog/fajly-papki-portfeli', 'hm4.png', 2),
        ('Подставки, лотки, настольные наборы', 'podstavki-lotki-nastolnye-nabory', '/catalog/podstavki-lotki-nastolnye-nabory', 'hm5.png', 1),
        ('Демонстрационное оборудование', 'demonstracionnoe-oborudovanie', '/catalog/demonstracionnoe-oborudovanie', 'hm6.png', 2),
        ('Бумага офисная белая', 'bumaga-ofisnaja-belaja', '/catalog/bumaga-ofisnaja-belaja', 'hcm1.png', 1),
        ('Бумага офисная цветная', 'bumaga-ofisnaja-cvetnaja', '/catalog/bumaga-ofisnaja-cvetnaja', 'hcm2.png', 2),
        ('Бумага писчая, газетная', 'bumaga-pischaja-gazetnaja', '/catalog/bumaga-pischaja-gazetnaja', 'hcm3.png', 3),
        ('Бумага для факса', 'bumaga-dlya-faksa', '/catalog/bumaga-dlya-faksa', 'hcm4.png', 4),
        ('Клей', 'klej', '/catalog/klej', 'hcm1.png', 1),
        ('Корректоры', 'korrektory', '/catalog/korrektory', 'hcm2.png', 2),
        ('Дыроколы', 'dyrokoly', '/catalog/dyrokoly', 'hcm4.png', 4);

INSERT INTO catalog_ref (id_catalog, parent_id_catalog)
VALUES  (1, 0),
        (2, 0),
        (3, 0),
        (4, 0),
        (5, 0),
        (6, 0),
        (7, 1),(8, 1),(9, 1),(10, 1),
        (11, 2),(12, 2),(13, 2);

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
VALUES  (1, 1),
        (1, 2),
        (1, 3),
        (1, 4),
        (1, 5),
        (1, 6),
        (1, 7),
        (1, 8);

INSERT INTO brands (name, translit_name, icon)
VALUES ('Ксерокс', 'xerox', 'xerox.png');

INSERT INTO extra_types (name)
VALUES ('Хиты продаж'),('Супер цена'),('Новинки');

INSERT INTO products (pnt, full_name, short_name, translit_name, id_brand, country_made, measure, vat)
VALUES  (9491, 'Бумага офисная Ксерокс Перформер А4, 80 г/м2, 500 л.', 'А4, 80 г/м2, 500 л.', 'bumaga-ofisnaja-xerox-performer-A4-80-gm2-500l', 1, 'РФ', 'пач.', 20),
        (9496, 'Бумага офисная Ксерокс Перформер А3, 80 г/м2, 500 л.', 'А3, 80 г/м2, 500 л.', 'bumaga-ofisnaja-xerox-performer-A3-80-gm2-500l', 1, 'РФ', 'пач.', 10);

INSERT INTO product_description (id_product, short_description, full_description)
VALUES (1, 'Бумага класса С. Надежная и экономичная - для повседневных работ. Совместима со всеми видами копиров и принтеров. Произведена в соответствии с международными требованиями XEROX. Не содержит древесных смол и газообразного хлора. Белизна - 146 CIE. Толщина - 104 мкм. Непрозрачность - 89%. Срок архивного хранения > 150 лет. Копир *** Лазерный принтер *** Струйный принтер **', 'Бумага класса С+. Гладкие листы практически беспыльны, отличаются низкой электростатичностью. Используется для печати на высокоскоростных копирах и принтерах и совместима с любым видом офисной техники. Бумага может применяться для печати на офсетных машинах.'),
       (2, 'Бумага класса С. Надежная и экономичная - для повседневных работ. Совместима со всеми видами копиров и принтеров. Произведена в соответствии с международными требованиями XEROX. Не содержит древесных смол и газообразного хлора. Белизна - 146 CIE. Толщина - 104 мкм. Непрозрачность - 89%. Срок архивного хранения > 150 лет. Копир *** Лазерный принтер *** Струйный принтер **', 'Бумага класса С+. Гладкие листы практически беспыльны, отличаются низкой электростатичностью. Используется для печати на высокоскоростных копирах и принтерах и совместима с любым видом офисной техники. Бумага может применяться для печати на офсетных машинах.');

INSERT INTO prices_types (name)
VALUES ('Стандартная');

INSERT INTO product_prices (id_product, quatity_start, value)
VALUES (1, 1, 5.56),
       (1, 20, 5.35),
       (1, 60, 5.26),
       (1, 120, 5.18),
       (1, 240, 5.13),
       (2, 1, 11.2);

INSERT INTO product_catalog (id_catalog, order_product)
VALUES (7, 1),(7, 2);

INSERT INTO stock (name, id_product, quantity)
VALUES ('Брест центральный', 1, 20);
