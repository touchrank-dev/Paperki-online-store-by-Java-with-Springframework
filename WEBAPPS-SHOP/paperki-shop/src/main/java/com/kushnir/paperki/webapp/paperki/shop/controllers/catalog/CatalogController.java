package com.kushnir.paperki.webapp.paperki.shop.controllers.catalog;

import com.kushnir.paperki.model.Category;
import com.kushnir.paperki.model.product.Product;
import com.kushnir.paperki.service.CatalogBean;
import com.kushnir.paperki.service.ImageService;
import com.kushnir.paperki.service.MenuBean;
import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.webapp.paperki.shop.exceptions.PageNotFound;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    private static final Logger LOGGER = LogManager.getLogger(CatalogController.class);
    private static final String CATALOG_MENU_NAME = "catalog";

    @Autowired
    CatalogBean catalogBean;

    @Autowired
    MenuBean menuBean;

    @Autowired
    ImageService imageService;

    @Value("${content.path}")
    String contentPath;

    @GetMapping
    public String catalogPage(Model model) {
        LOGGER.debug("catalogPage() >>>");
        model.addAttribute("templatePathName", contentPath + CATALOG_MENU_NAME);
        model.addAttribute("fragmentName", CATALOG_MENU_NAME);

        model.addAttribute("title", "Каталог");

        return "index";
    }

    @GetMapping("/{catalogItemTranslitName}")
    public String catalogItemPage(@PathVariable String catalogItemTranslitName,
                                  @RequestParam(required = false) Map<String, String> filterParams,
                                  HttpSession session, Model model) throws ServiceException, PageNotFound {
        LOGGER.debug("catalogItemPage() >>> filter args: {}", filterParams);

        Integer type =      session.getAttribute("catview") == null ? 1:(Integer)session.getAttribute("catview");
        Integer sortType =  session.getAttribute("sortedby") == null ? 1:(Integer)session.getAttribute("sortedby");

        Category category = catalogBean.getCategoryByTName(catalogItemTranslitName);
        if (category == null) throw new PageNotFound();

            model.addAttribute("title", !StringUtils.isBlank(category.getCustomtitle()) ? category.getCustomtitle() : category.getName());
            model.addAttribute("description", StringUtils.isBlank(category.getMetadesk()) ? null : category.getMetadesk());
            model.addAttribute("category", category);

            // congenerale category has 0 in parent category ID
        if (category.getParent().equals(0)) {
            model.addAttribute("templatePathName", contentPath + "subcategory");
            model.addAttribute("fragmentName", "subcategory");
        } else {
            Map products = null;
            try {
                if (type == null || type == 1) {
                    products = catalogBean.getProductsByCategoryTName(catalogItemTranslitName, sortType);
                    HashMap<String, List<Product>> brands = createBrandFilter(products);
                    model.addAttribute("filterParams", filterParams);
                    model.addAttribute("products", filterByBrands(products, brands, filterParams));
                    model.addAttribute("brands", brands);
                    model.addAttribute("templatePathName", contentPath + "product-list");
                    model.addAttribute("fragmentName", "product-list");
                } else if (type == 2) {
                    products = catalogBean.getProductsByCategoryTName(catalogItemTranslitName, sortType);
                    HashMap<String, List<Product>> brands = createBrandFilter(products);
                    model.addAttribute("filterParams", filterParams);
                    model.addAttribute("products", filterByBrands(products, brands, filterParams));
                    model.addAttribute("brands", brands);
                    model.addAttribute("templatePathName", contentPath + "product-list-row");
                    model.addAttribute("fragmentName", "product-list-row");
                } else if (type == 3) {
                    model.addAttribute("products", catalogBean.getProductsByGroupView(catalogItemTranslitName, sortType));
                    model.addAttribute("templatePathName", contentPath + "product-list-group");
                    model.addAttribute("fragmentName", "product-list-group");
                }

            } catch (IllegalArgumentException e) {
                LOGGER.error(e.getMessage());
                throw new PageNotFound();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                throw e;
            }
        }

        return "index";
    }

    private HashMap<String, List<Product>> createBrandFilter(Object products) {
        HashMap<String, List<Product>> brands = new HashMap<>();
        for (Product product:((HashMap<Integer, Product>) products).values()) {
            List<Product> productList = brands.get(product.getBrand().getName());
            if (productList == null) {
                productList = new LinkedList<Product>();
                productList.add(product);
                brands.put(product.getBrand().getName(), productList);
            } else {
                productList.add(product);
            }
        }

        return brands;
    }

    private HashMap<Integer, Product> filterByBrands(Map prod, HashMap<String, List<Product>> brands, Map<String, String> filterParams) {
        if (filterParams != null && filterParams.size() > 0) {
            HashMap<Integer, Product> products = new HashMap();
            for (Map.Entry<String, String> entry: filterParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key.startsWith("brand")) {
                    for (Product product:brands.get(value)) {
                        products.put(product.getPnt(), product);
                    }
                }
            }
            return products;
        } else return (HashMap<Integer, Product>) prod;
    }


    @GetMapping("/{catalogItemTranslitName}/{productTranslitName}")
    public String productItemPage(@PathVariable String catalogItemTranslitName,
                                  @PathVariable String productTranslitName,
                                  Model model) throws ServiceException, PageNotFound {
        LOGGER.debug("productItemPage() >>>");
        try {

            Integer pnt = Integer.parseInt(productTranslitName.split("-")[0]);

            Product product = catalogBean.getProductByPNT(pnt);
            model.addAttribute("title", product.getFullName());
            model.addAttribute("description", product.getMetadesk());
            model.addAttribute("product", product);
            model.addAttribute("category", catalogBean.getCategoryByTName(catalogItemTranslitName));

            model.addAttribute("templatePathName", contentPath + "product-details");
            model.addAttribute("fragmentName", "product-details");

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new PageNotFound();
        }
        return "index";
    }

    @ModelAttribute("mainmenu")
    public ArrayList getMainMenu () {
        return menuBean.getAll("root");
    }

    @ModelAttribute("mapcategories")
    public HashMap getCatalog () throws ServiceException {
        return catalogBean.getAll();
    }

    @ModelAttribute("catview")
    public Integer viewType(HttpSession session) {
        Integer type = (Integer)session.getAttribute("catview");
        return type == null ? 1:type;
    }

    @ModelAttribute("sortedby")
    public Integer sortType(HttpSession session) {
        Integer type = (Integer)session.getAttribute("sortedby");
        return type == null ? 1:type;
    }


    @ModelAttribute("oldImages")
    public HashMap<Integer, ArrayList<String>> getOldImages() {
        LOGGER.debug("getOldImages()");
        HashMap<Integer, ArrayList<String>> oldImages = imageService.getAllOldImages();
        return oldImages;
    }


}
