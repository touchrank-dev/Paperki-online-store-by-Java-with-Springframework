package com.kushnir.paperki.dao.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.*;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-dao-test.xml"})
public class CatalogDaoTest {

    private static final Logger LOGGER = LogManager.getLogger(CatalogDaoTest.class);

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
        LOGGER.debug("Test: getAllTest() >>>:");
    }
}
