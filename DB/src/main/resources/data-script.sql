INSERT INTO categories (name, translit_name_category, link, icon, order_category)
VALUES  ('Тест1', 'Test1', '/test1?link=test1', '<icon></icon>', 1),
        ('Тест2', 'Test2', '/test2?link=test2', '<icon></icon>', 2),
        ('Тест3', 'Test3', '/test3?link=test3', '<icon></icon>', 3),
        ('Тест4', 'Test4', '/test4?link=test4', '<icon></icon>', 2),
        ('Тест5', 'Test5', '/test5?link=test5', '<icon></icon>', 1),
        ('Тест6', 'Test6', '/test6?link=test6', '<icon></icon>', 2);

INSERT INTO categories_ref (id_category, parent_id_category)
VALUES  (1, 0),
        (2, 1),
        (3, 2),
        (4, 0),
        (5, 4),
        (6, 4);