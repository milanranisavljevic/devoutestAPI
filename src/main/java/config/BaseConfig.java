package config;

import org.apache.log4j.Logger;

/**
 * Order of execution:
 *
 *      BaseConfig's BeforeTest
 *      ChildTestClass's BeforeTest
 *      BaseConfig's BeforeClass
 *      ChildTestClass's BeforeClass
 *      BaseConfig's BeforeMethod
 *      ChildTestClass's BeforeMethod
 *      === Executing actual test ===
 *      ChildTestClass's AfterMethod
 *      BaseConfig's AfterMethod
 *      ChildTestClass's AfterClass
 *      BaseConfig's AfterClass
 *      ChildTestClass's AfterTest
 *      BaseConfig's AfterTest
 *
 * Note:
 *
 *      If method in ChildClass has the same name as in BaseConfig (parent),
 *      BaseConfig's method will be overridden.
 *
 */

public class BaseConfig {

    final static Logger logger = Logger.getLogger(BaseConfig.class);

}
