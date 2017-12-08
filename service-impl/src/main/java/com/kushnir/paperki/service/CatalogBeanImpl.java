package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.CatalogDao;
import com.kushnir.paperki.model.Category;
import com.kushnir.paperki.model.category.CategoryContainer;
import com.kushnir.paperki.model.category.CategorySimple;
import com.kushnir.paperki.model.product.Product;
import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.service.mail.Mailer;
import com.kushnir.paperki.model.util.Transliterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class CatalogBeanImpl implements CatalogBean {

    private static final Logger LOGGER = LogManager.getLogger(CatalogBeanImpl.class);

    @Autowired
    CatalogDao catalogDao;

    @Autowired
    ProductBean productBean;

    @Autowired
    Mailer mailer;

    @Value("${catalog.url}")
    String catalogURL;

    @Override
    public HashMap<Integer, HashMap<Integer, Category>> getAll() throws ServiceException {
        try {
            HashMap categories = catalogDao.getAll();
            return categories;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public HashMap<Integer, CategorySimple> getAllChildrenWithPapIdKey() {
        LOGGER.debug("getAllChildrenWithPapIdKey() >>> ");
        HashMap<Integer, CategorySimple> categories = catalogDao.getAllChildrenWithPapIdKey();
        return categories;
    }

    @Override
    public Category getCategoryByTName(String categoryTName) throws ServiceException {
        LOGGER.debug("getCategoryByTName({}) >>> ", categoryTName);
        try {
            Category category = catalogDao.getCategoryByTName(categoryTName);
            return category;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public HashMap<Integer, Product> getProductsByCategoryTName(String categoryTName, Integer sortType) throws ServiceException {
        LOGGER.debug("getProductsByCategoryTName({}) >>> ", categoryTName);
        try {
            HashMap<Integer, Product> products = productBean.getProductListByCategoryTName(categoryTName, sortType);
            return products;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public HashMap<String, List<Product>> getProductsByGroupView(String categoryTName, Integer sortType) throws ServiceException {
        LOGGER.debug("getProductsByGroupView({}) >>> ", categoryTName);
        HashMap<String, List<Product>> products = new LinkedHashMap();
        Product product;
        String groupName;
        List<Product> listProducts;
        for(Map.Entry<Integer, Product> entry : getProductsByCategoryTName(categoryTName, sortType).entrySet()) {
            product = entry.getValue();
            groupName = product.getPersonalGroupName();

            listProducts = products.get(groupName);
            if(listProducts == null) {
                listProducts = new ArrayList<>();
                listProducts.add(product);
                products.put(groupName, listProducts);
            } else {
                listProducts.add(product);
            }
        }
        return products;
    }


    @Override
    public Product getProductByTName(String productTName) throws ServiceException {
        LOGGER.debug("getProductByTName({}) >>> ", productTName);
        try {
            Product product = productBean.getProductByTName(productTName);
            return product;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Product getProductByPNT(Integer pnt) throws ServiceException {
        LOGGER.debug("getProductByPNT({}) >>> ", pnt);
        try {
            Product product = productBean.getProductByPNT(pnt);
            return product;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public String updateCatalog() throws ServiceException, IOException {
        LOGGER.debug("updateCatalog() START PROCESS >>>");
        StringBuilder sb = new StringBuilder();

        try {

            CategoryContainer CSVCategories = getCategoriesFromCSVToContainer(sb);
            Assert.notNull(CSVCategories, "CSVCategories = null");
            CategoryContainer categories = getCategoriesToContainer();
            Assert.notNull(categories, "categories = null");

            List<Category> updCat = new ArrayList<>();

            /* ========== PARENTS =============================================================*/
            for (Map.Entry<Integer, Category> entry : CSVCategories.getParents().entrySet()) {
                try {
                    Category CSVParentCategory = entry.getValue();
                    Assert.notNull(CSVParentCategory, "CSVParentCategory = null");
                    String translitName = Transliterator.cyr2lat(CSVParentCategory.getName());
                    String link = catalogURL + translitName;
                    CSVParentCategory.setTranslitName(translitName);
                    CSVParentCategory.setLink(link);
                    validateCategory(CSVParentCategory);

                    for(Map.Entry<Integer, Category> catEntry : categories.getParents().entrySet()) {
                        Category category = catEntry.getValue();

                        Integer catPapId = category.getPapId();

                        if(catPapId != null && !catPapId.equals(0)) {
                            if (CSVParentCategory.getPapId().equals(catPapId)) {
                                CSVParentCategory.setId(category.getId());
                                updCat.add(CSVParentCategory);
                                break;
                            }
                        } else if (category.getTranslitName() != null){
                            if (CSVParentCategory.getTranslitName().equals(category.getTranslitName())) {
                                CSVParentCategory.setId(category.getId());
                                updCat.add(CSVParentCategory);
                                break;
                            }
                        }
                    }

                    if (CSVParentCategory.getId() == null) {
                        try {
                            int id = addCategory(CSVParentCategory);
                            CSVParentCategory.setId(id);
                            addRefCategory(CSVParentCategory);
                        } catch (DuplicateKeyException e) {
                            LOGGER.error("ERROR >>> {}", "Вероятно категория '"+CSVParentCategory.getName()+"' уже существует");
                            sb.append("ERROR >>> ")
                                    .append("Вероятно категория '"+CSVParentCategory.getName()+"' уже существует")
                                    .append('\n');
                        }
                    }

                } catch (Exception e) {
                    LOGGER.error("ERROR >>> {}", e.getMessage());
                    sb.append("ERROR >>> ").append(e).append(" >>> ").append(e.getMessage()).append('\n');
                    continue;
                }
            }

            /* ========== CHILDREN =============================================================*/
            for (Map.Entry<Integer, Category> entry : CSVCategories.getChildren().entrySet()) {
                try {
                    Category CSVChildCategory = entry.getValue();
                    Assert.notNull(CSVChildCategory, "CSVChildCategory = null");
                    String translitName = Transliterator.cyr2lat(CSVChildCategory.getName());
                    String link = catalogURL + translitName;
                    CSVChildCategory.setTranslitName(translitName);
                    CSVChildCategory.setLink(link);
                    validateCategory(CSVChildCategory);

                    Integer parentPapId = CSVChildCategory.getParent();
                    if (parentPapId != null && parentPapId > 0) {
                        Category parent = CSVCategories.getParents().get(parentPapId);
                        if (parent != null) {
                            Integer parentId = parent.getId();
                            if (parentId != null && parentId > 0) {
                                CSVChildCategory.setParent(parentId);
                            }
                        }
                    }

                    for(Map.Entry<Integer, Category> catEntry : categories.getChildren().entrySet()) {
                        Category category = catEntry.getValue();

                        Integer catPapId = category.getPapId();

                        if(catPapId != null && !catPapId.equals(0)) {
                            if (CSVChildCategory.getPapId().equals(catPapId)) {
                                CSVChildCategory.setId(category.getId());
                                updCat.add(CSVChildCategory);
                                break;
                            }
                        } else if (category.getTranslitName() != null){
                            if (CSVChildCategory.getTranslitName().equals(category.getTranslitName())) {
                                CSVChildCategory.setId(category.getId());
                                updCat.add(CSVChildCategory);
                                break;
                            }
                        }
                    }

                    if (CSVChildCategory.getId() == null) {
                        try {
                            int id = addCategory(CSVChildCategory);
                            if (id > 0) {
                                CSVChildCategory.setId(id);
                                addRefCategory(CSVChildCategory);
                            }
                        } catch (DuplicateKeyException e) {
                            LOGGER.error("ERROR >>> {}", "Вероятно категория '"+CSVChildCategory.getName()+"' уже существует");
                            sb.append("ERROR >>> ")
                                    .append("Вероятно категория '"+CSVChildCategory.getName()+"' уже существует")
                                    .append('\n');
                        }
                    }

                } catch (Exception e) {
                    LOGGER.error("ERROR >>> {}", e.getMessage());
                    sb.append("ERROR >>> ").append(e).append(" >>> ").append(e.getMessage()).append('\n');
                    continue;
                }
            }

            /* ========== UPDATE ALL ==========================================================*/
            if (!updCat.isEmpty() && updCat.size() > 0) {
                sb.append(updateCategories(updCat.toArray())).append('\n');
                sb.append(updateCategoriesRef(updCat.toArray())).append('\n');
            }

            sb.append("========== UPDATE FINISHED ==========");

        } catch (Exception e) {
            LOGGER.error("UPDATE FINISHED WITH ERROR >>> {}", e.getMessage());
            sb.append("UPDATE FINISHED WITH ERROR >>> ").append(e).append(" >>> ").append(e.getMessage());
        }

        mailer.toSupportMail(sb.toString(), "updateCatalog REPORT");
        return sb.toString();
    }


    public CategoryContainer getCategoriesFromCSVToContainer(StringBuilder sb) throws IOException {
        LOGGER.debug("getCategoriesFromCSVToContainer() >>>");
        return catalogDao.getCategoriesFromCSVToContainer(sb);
    }

    @Override
    public CategoryContainer getCategoriesToContainer() {
        LOGGER.debug("getCategoriesToContainer() >>>");
        return catalogDao.getCategoriesToContainer();
    }

    @Override
    public int addCategory(Category category) {
        LOGGER.debug("addCategory() >>>");
        return catalogDao.addCategory(category);
    }

    @Override
    public int addRefCategory(Category category) {
        LOGGER.debug("addRefCategory() >>>");
        return catalogDao.addRefCategory(category);
    }

    @Override
    public int[] addCategories(Object[] categories) {
        LOGGER.debug("addCategories() >>>");
        return catalogDao.addCategories(categories);
    }

    @Override
    public int[] addCategoriesRef(Object[] categories) {
        LOGGER.debug("addCategoriesRef() >>>");
        return catalogDao.addCategoriesRef(categories);
    }

    @Override
    public int updateCategory(Category category) throws Exception {
        throw new Exception("updateCategory() IS NOT IMPLEMENTED");
    }

    @Override
    public int updateCategoryRef(Category category) throws Exception {
        throw new Exception("updateCategoryRef() IS NOT IMPLEMENTED");
    }

    @Override
    public String updateCategories(Object[] categories) {
        LOGGER.debug("updateCategories(count: {}) >>>", categories.length);
        int[] result = catalogDao.updateCategories(categories);
        String report = "SUCCESSFUL UPDATED: "+result.length+"/"+categories.length+" categories";
        LOGGER.debug(report);
        return report;
    }

    @Override
    public String updateCategoriesRef(Object[] categories) {
        LOGGER.debug("updateCategoriesRef(count: {}) >>>", categories.length);
        int[] result = catalogDao.updateCategoriesRef(categories);
        String report = "SUCCESSFUL UPDATED: "+result.length+"/"+categories.length+" ref categories";
        LOGGER.debug(report);
        return report;
    }

    private void validateCategory(Category category) {
        Assert.notNull(category, "Категория = null");
        Assert.notNull(category.getPapId(), "PapId = null");
        Assert.notNull(category.getName(), "Имя категории = null");
        Assert.hasText(category.getName() ,"Пустое имя категории");
        Assert.notNull(category.getTranslitName(), "translitName = null");
        Assert.hasText(category.getTranslitName(), "translitName is blank");
    }
}
