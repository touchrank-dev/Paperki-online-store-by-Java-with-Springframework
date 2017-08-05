package com.kushnir.paperki.dao.test;

import com.kushnir.paperki.dao.MenuDao;

import com.kushnir.paperki.model.MenuItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.*;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-dao-test.xml"})
@Transactional
public class MainMenuDaoTest {

    private static final Logger LOGGER = LogManager.getLogger(MainMenuDaoTest.class);

    @Autowired
    MenuDao mainMenuDao;

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
        ArrayList<MenuItem> menuItems = mainMenuDao.getAll();
        assertNotNull(menuItems);
        assertTrue(menuItems.size() > 0);
        LOGGER.debug("Test: getAllTest() >>>: {}", menuItems);
    }

    @Test
    public void getItemByTNameTest() {
        MenuItem menuItem = mainMenuDao.getItemByTName("about");
        assertNotNull(menuItem);
        assertTrue("О компании".equals(menuItem.getName()));
        assertTrue("/about".equals(menuItem.getLink()));
        assertTrue(1 == menuItem.getMenu());
        LOGGER.debug("Test: getItemByTNameTest() >>>: {}", menuItem);
    }
}
