package com.kushnir.paperki.dao.test;

import com.kushnir.paperki.dao.CategoryDao;

import com.kushnir.paperki.model.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-dao-test.xml"})
@Transactional
public class CategoryDaoTest {

    private static final Logger LOGGER = LogManager.getLogger(CategoryDaoTest.class);

    @Autowired
    CategoryDao categoryDao;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        LOGGER.debug("execute: setUpBeforeClass()");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        LOGGER.debug("execute: tearDownAfterClass()");
    }

    @Before
    public void beforeTest() {
        LOGGER.debug("execute: beforeTest()");
    }

    @After
    public void afterTest() {
        LOGGER.debug("execute: afterTest()");
    }

    @Test
    public void getAllTest() {
        HashMap<Integer, Category> categories = categoryDao.getAll();
        assertNotNull(categories);
        assertTrue(categories.size() > 0);
        LOGGER.debug("Test: getAllTest() >>>: {}", categories);
    }
}
