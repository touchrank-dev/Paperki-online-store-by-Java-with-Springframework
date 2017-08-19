SET NAMES utf8;

INSERT INTO users (login_user, email, name_user, password)
VALUES ('a-kush@mail.ru', 'a-kush@mail.ru', 'Artem Kushnir', '42Kush6984');

INSERT INTO catalog (name, translit_name, link, icon, order_catalog)
VALUES  ('Бумага и бумажная продукция', 'bumaga-i-bymazhnaz produkcia', '/catalog/bumaga-i-bymazhnaz produkcia', 'hm1.png', 1),
        ('Канцелярские товары', 'kanceliarskie-tovary', '/catalog/kanceliarskie-tovary', 'hm2.png', 2),
        ('Пишущие и рисовальные принадлежности', 'pishushie-i-risovalnye-prinadlezhnosti', '/catalog/pishushie-i-risovalnye-prinadlezhnosti', 'hm3.png', 3),
        ('Файлы, папки, портфели', 'fajly-papki-portfeli', '/catalog/fajly-papki-portfeli', 'hm4.png', 2),
        ('Подставки, лотки, настольные наборы', 'podstavki-lotki-nastolnye-nabory', '/catalog/podstavki-lotki-nastolnye-nabory', 'hm5.png', 1),
        ('Демонстрационное оборудование', 'demonstracionnoe-oborudovanie', '/catalog/demonstracionnoe-oborudovanie', 'hm6.png', 2),
        ('Бумага офисная белая', 'bumaga-ofisnaja-belaja', '/catalog/bumaga-ofisnaja-belaja', 'hcm1.png', 1),
        ('Бумага офисная цветная', 'bumaga-ofisnaja-cvetnaja', '/catalog/bumaga-ofisnaja-cvetnaja', 'hcm2.png', 2),
        ('Бумага писчая, газетная', 'bumaga-pischaja-gazetnaja', '/bumaga-pischaja-gazetnaja', 'hcm3.png', 3),
        ('Бумага для факса', 'bumaga-dlya-faksa', '/bumaga-dlya-faksa', 'hcm4.png', 4),
        ('Клей', 'klej', '/klej', 'hcm1.png', 1),
        ('Корректоры', 'korrektory', '/korrektory', 'hcm2.png', 2),
        ('Дыроколы', 'dyrokoly', '/dyrokoly', 'hcm4.png', 4);

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

INSERT INTO products (pnt, full_name, short_name, translit_name, id_brand, country_made, measure, vat)
VALUES  (9491, 'Бумага офисная Ксерокс Перформер А4, 80 г/м2, 500 л.', 'А4, 80 г/м2, 500 л.', 'bumaga-ofisnaja-xerox-performer-A4-80-gm2-500l', 1, 'РФ', 'пач.', 20),
        (9496, 'Бумага офисная Ксерокс Перформер А3, 80 г/м2, 500 л.', 'А3, 80 г/м2, 500 л.', 'bumaga-ofisnaja-xerox-performer-A3-80-gm2-500l', 1, 'РФ', 'пач.', 10);

INSERT INTO prices_types (name)
VALUES ('Сиандартная');

INSERT INTO product_prices (id_price_type, id_product, value)
VALUES (1, 1, 4.6),(1, 2, 4.6);

INSERT INTO product_catalog (id_catalog, order_product)
VALUES (7, 1),(7, 2);

INSERT INTO stock (name, id_product, quantity)
VALUES ('Брест центральный', 1, 20);
