(SELECT
   c.*,
   cf.parent_id_catalog AS parent
 FROM catalog AS c
   JOIN catalog_ref AS cf USING (id_catalog)
 WHERE cf.parent_id_catalog = 0)
UNION
(SELECT DISTINCT
   c.*,
   cf.parent_id_catalog AS parent
 FROM products AS p
   LEFT JOIN product_catalog AS pc USING (id_product)
   LEFT JOIN stock AS s USING (id_product)
   LEFT JOIN catalog AS c USING (id_catalog)
   LEFT JOIN catalog_ref AS cf USING (id_catalog)
 WHERE s.id_stock_place = 1
       AND s.quantity_available IS NOT NULL
       AND p.available_day IS NOT NULL)
ORDER BY parent, order_catalog;