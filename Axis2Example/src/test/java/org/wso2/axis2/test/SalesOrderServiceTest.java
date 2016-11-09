/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * you may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.axis2.test;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wso2.axis2.service.Item;
import org.wso2.axis2.service.SalesOrder;
import org.wso2.axis2.service.SalesOrderService;

public class SalesOrderServiceTest {

    private static SalesOrderService salesOrderService;
    private static SalesOrder salesOrder;
    private static Item item1;
    private static Item item2;
    private static Item item3;
    private static Item item4;

    @BeforeClass
    public static void setup() {

        salesOrderService = new SalesOrderService();

        item1 = new Item();
        item1.setItemId(100);
        item1.setItemName("Bed");
        item1.setPrice((float) 19000.0);

        item2 = new Item();
        item2.setItemId(101);
        item2.setItemName("Bed sheet");
        item2.setPrice((float) 1000.0);

        item3 = new Item();
        item3.setItemId(102);
        item3.setItemName("Metras");
        item3.setPrice((float) 10000.0);

        item4 = new Item();
        item4.setItemId(103);
        item4.setItemName("Cupboard");
        item4.setPrice((float) 20000.0);


    }

    @Test
    public void testAddItem() {
        salesOrderService.addItem(1, item1);
        salesOrder = salesOrderService.getSalesOrder(1);
        Assert.assertTrue(!salesOrder.getItemsList().isEmpty());
    }

    @Test
    public void testRemoveItem() {
        salesOrderService.removeItem(100);
        SalesOrder salesOrder2 = salesOrderService.getSalesOrder(1);
        Assert.assertTrue(salesOrder2.getItemsList().isEmpty());
    }

    @Test
    public void testGetTotalPrice() {
        salesOrderService.addItem(1, item1);
        salesOrderService.addItem(1, item2);
        salesOrderService.addItem(1, item3);
        salesOrderService.addItem(1, item4);

        Double price = salesOrderService.getTotalPrice(1);
        System.out.println(salesOrderService.getSalesOrder(1));
        Assert.assertEquals(price, 50000.0);

    }


}
