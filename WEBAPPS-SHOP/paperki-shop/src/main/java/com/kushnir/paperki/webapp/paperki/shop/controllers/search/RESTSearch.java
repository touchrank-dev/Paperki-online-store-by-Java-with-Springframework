package com.kushnir.paperki.webapp.paperki.shop.controllers.search;

import com.kushnir.paperki.model.RestMessage;
import com.kushnir.paperki.service.ProductBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/api/search")
public class RESTSearch {

    private static final Logger LOGGER = LogManager.getLogger(RESTSearch.class);

    @Autowired
    ProductBean productBean;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage search(@RequestParam String str) {
        LOGGER.debug("REST search({})", str);
        RestMessage restMessage;

        ArrayList products = productBean.searchProducts(str);
        restMessage = new RestMessage(HttpStatus.FOUND, str, products);

        // TODO edit it:

        /*
        *
            SELECT
                p.id_product,
                p.pnt,
                p.base_price,
                p.vat,
                p.personal_group_name,
                p.full_name,
                p.short_name,
                p.link,
                p.measure,
                p.country_from,
                p.country_made,
                c.translit_name AS cat_translit_name,
                s.quantity_available,
                d.value_double,
                d.value_int,
                dt.type AS dtype
            FROM
                stock AS s
                    LEFT JOIN
                products AS p USING (id_product)
                    LEFT JOIN
                product_catalog AS pc USING (id_product)
                    LEFT JOIN
                catalog AS c USING (id_catalog)
                    LEFT JOIN
                discounts AS d USING (id_product)
                    LEFT JOIN
                discount_types AS dt USING (id_discount_type)
            WHERE
                p.full_name REGEXP 'картонная|папка'
                -- p.full_name LIKE '%картонная%' AND p.full_name LIKE '%папка%'
                    OR p.pnt = NULL
            ORDER BY p.personal_group_name;
        *
        * */


        return restMessage;
    }

}
