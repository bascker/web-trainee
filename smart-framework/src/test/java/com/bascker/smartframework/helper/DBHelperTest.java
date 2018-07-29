package com.bascker.smartframework.helper;

import org.testng.annotations.Test;

/**
 * DBHelper Unit Test
 *
 * @author bascker
 */
@Test
public class DBHelperTest {

    public void testExecuteSqlFile() {
        DBHelper.executeSqlFile("sql/customer_init.sql");
    }

}
