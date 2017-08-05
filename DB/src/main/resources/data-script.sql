INSERT INTO categories (name, translit_name, link, icon, order_category)
VALUES  ('Тест1', 'Test1', '/category/test1', 'hm1.png', 1),
        ('Тест2', 'Test2', '/category/test2', 'hm1.png', 2),
        ('Тест3', 'Test3', '/category/test3', 'hm1.png', 3),
        ('Тест4', 'Test4', '/category/test4', 'hm1.png', 2),
        ('Тест5', 'Test5', '/category/test5', 'hm1.png', 1),
        ('Тест6', 'Test6', '/category/test6', 'hm1.png', 2);

INSERT INTO categories_ref (id_category, parent_id_category)
VALUES  (1, 0),
        (2, 0),
        (3, 0),
        (4, 0),
        (5, 0),
        (6, 0);

INSERT INTO menu (name, translit_name)
VALUES  ('Главное', 'menu'),
        ('Помощь', 'help');

INSERT INTO menu_items (id_menu, name, translit_name, link, order_item)
VALUES  (1, 'О компании', 'about', '/about', 1),
        (1, 'Помощь', 'help', '/help', 2),
        (1, 'Каталог товаров', 'catalog', '/catalog', 3),
        (1, 'Новости', 'blog', '/blog', 4),
        (1, 'База знаний', 'knowledgebase', '/knowledgebase', 5),
        (1, 'Доставка и оплата', 'payanddelivery', '/payanddelivery', 6),
        (1, 'Отзывы', 'feedback', '/feedback', 7),
        (1, 'Контакты', 'contacts', '/contacts', 8),
        (2, 'О компании', 'about', '/about', 1),
        (2, 'Помощь', 'help', '/help', 2),
        (2, 'PAP-бонус', 'papbonus', '/papbonus', 3),
        (2, 'Каталог товаров', 'catalog', '/catalog', 4),
        (2, 'Новости', 'blog', '/blog', 5),
        (2, 'База знаний', 'knowledgebase', '/knowledgebase', 6),
        (2, 'Доставка и оплата', 'payanddelivery', '/payanddelivery', 7),
        (2, 'Отзывы', 'feedback', '/feedback', 8),
        (2, 'Контакты', 'contacts', '/contacts', 9);