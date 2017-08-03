INSERT INTO categories (name, translit_name_category, link, icon, order_category)
VALUES  ('Тест1', 'Test1', '/category/test1', 'hm1.png', 1),
        ('Тест2', 'Test2', '/category/test2', 'hm1.png', 2),
        ('Тест3', 'Test3', '/category/test3', 'hm1.png', 3),
        ('Тест4', 'Test4', '/category/test4', 'hm1.png', 2),
        ('Тест5', 'Test5', '/category/test5', 'hm1.png', 1),
        ('Тест6', 'Test6', '/category/test6', 'hm1.png', 2);

INSERT INTO categories_ref (id_category, parent_id_category)
VALUES  (1, 0),
        (2, 1),
        (3, 2),
        (4, 0),
        (5, 4),
        (6, 4);