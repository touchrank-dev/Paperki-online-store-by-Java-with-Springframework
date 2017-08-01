INSERT INTO categories (name, translit_name_category, link, icon, order_category)
VALUES  ('Тест1', 'Test1', '/test1?link=test1', '<icon></icon>', 1),
        ('Тест2', 'Test2', '/test2?link=test2', '<icon></icon>', 2),
        ('Тест3', 'Test3', '/test3?link=test3', '<icon></icon>', 3);

INSERT INTO categories_ref (id_category, parent_id_category)
VALUES  (1, 0),
        (2, 1),
        (3, 2);