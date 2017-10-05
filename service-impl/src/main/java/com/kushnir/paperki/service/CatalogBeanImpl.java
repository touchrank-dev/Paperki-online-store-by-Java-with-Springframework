package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.CatalogDao;
import com.kushnir.paperki.model.Category;
import com.kushnir.paperki.model.category.CategoryContainer;
import com.kushnir.paperki.model.product.Product;
import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.service.util.Transliterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${catalog.url}")
    String catalogURL;

    @Override
    @Transactional
    public HashMap<Integer, HashMap<Integer, Category>> getAll() throws ServiceException {
        try {
            HashMap categories = catalogDao.getAll();
            return categories;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
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
    @Transactional
    public HashMap<Integer, Product> getProductsByCategoryTName(String categoryTName) throws ServiceException {
        LOGGER.debug("getProductsByCategoryTName({}) >>> ", categoryTName);
        try {
            HashMap<Integer, Product> products = productBean.getProductListByCategoryTName(categoryTName);
            return products;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
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
    @Transactional
    public String updateCatalog() throws ServiceException, IOException {
        LOGGER.debug("updateCatalog() START PROCESS >>>");

        StringBuilder sb = new StringBuilder();
        CategoryContainer CSVCategories = getCategoriesFromCSVToContainer();
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

                    if(category.getPapId() != null) {
                        if (CSVParentCategory.getPapId().equals(category.getPapId())) {
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
                    int id = addCategory(CSVParentCategory);
                    CSVParentCategory.setId(id);
                    addRefCategory(CSVParentCategory);
                }

            } catch (Exception e) {
                LOGGER.error("ERROR >>> {}", e.getMessage());
                sb.append("ERROR >>> ").append(e.getMessage()).append('\n');
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

                int parentPapId = CSVChildCategory.getParent();
                if (parentPapId > 0) {
                    Category parent = CSVCategories.getParents().get(parentPapId);
                    if (parent != null) {
                        int parentId = parent.getId();
                        if (parentId > 0) {
                            CSVChildCategory.setParentPapId(parentPapId);
                            CSVChildCategory.setParent(parentId);
                        }
                    }
                }

                for(Map.Entry<Integer, Category> catEntry : categories.getChildren().entrySet()) {
                    Category category = catEntry.getValue();

                    if(category.getPapId() != null) {
                        if (CSVChildCategory.getPapId().equals(category.getPapId())) {
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
                    int id = addCategory(CSVChildCategory);
                    if (id > 0) {
                        CSVChildCategory.setId(id);
                        addRefCategory(CSVChildCategory);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("ERROR >>> {}", e.getMessage());
                sb.append("ERROR >>> ").append(e.getMessage()).append('\n');
                continue;
            }
        }

        /* ========== UPDATE ALL ==========================================================*/
        try {
            if (!updCat.isEmpty() && updCat.size() > 0) {
                updateCategories(updCat.toArray());
                updateCategoriesRef(updCat.toArray());
            }
        } catch (Exception e) {
            LOGGER.error("ERROR UPDATE >>> {}", e.getMessage());
            sb.append("ERROR UPDATE>>> ").append(e.getMessage()).append('\n');
        }

        return sb.toString();
    }


    public CategoryContainer getCategoriesFromCSVToContainer() throws IOException {
        LOGGER.debug("getCategoriesFromCSVToContainer() >>>");
        return catalogDao.getCategoriesFromCSVToContainer();
    }

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
        throw new Exception("NOT IMPLEMENTED");
    }

    @Override
    public int updateCategoryRef(Category category) throws Exception {
        throw new Exception("NOT IMPLEMENTED");
    }

    @Override
    public int[] updateCategories(Object[] categories) {
        LOGGER.debug("updateCategories() >>>");
        return catalogDao.updateCategories(categories);
    }

    @Override
    public int[] updateCategoriesRef(Object[] categories) {
        LOGGER.debug("updateCategoriesRef() >>>");
        return catalogDao.updateCategoriesRef(categories);
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
