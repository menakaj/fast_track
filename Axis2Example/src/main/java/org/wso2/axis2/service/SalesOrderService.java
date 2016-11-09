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

package org.wso2.axis2.service;

import org.wso2.axis2.domain.SalesOrderProcessingService;

import javax.jws.WebService;
import java.util.*;

/**
 * Service implementation for processing sales orders.
 * Has following service functions.
 *
 *  1. Add item.
 *  2. Remove item
 *  3. Get total price.
 */
@WebService
public class SalesOrderService implements SalesOrderProcessingService {

    private static SalesOrder salesOrder;

    private static Map<Integer, SalesOrder> orders = new HashMap<>();

    public SalesOrderService(){}

    /**
     * Initiate the sales order process by creating an empty sales order.
     */
    private void start(Integer orderId) {
        if (orders.containsKey(orderId)) {
            salesOrder = orders.get(orderId);
            return;
        }
        SalesOrder order = new SalesOrder();
        order.setOrderId(orderId);
        order.setDate(new Date());
        order.setItemsList(new ArrayList<Item>());
        orders.put(orderId, order);
        salesOrder = order;
    }

    /**
     * Add new item to Sales order.
     * As this can be invoked by multiple users at the same time, make it synchronized.
     * @param orderId : Id of the order which the item should be added.
     * @param item : New item to be added.
     */
    @Override
    public void addItem(Integer orderId, Item item) {
        start(orderId);
        List<Item> items;
        if (salesOrder.getItemsList().size() == 0) {
            items = new ArrayList<>();
        } else {
            items = salesOrder.getItemsList();
        }
        items.add(item);
        salesOrder.setItemsList(items);
        orders.put(orderId, salesOrder);

    }

    /**
     * Remove the given item from the list of items.
     * @param itemId :
     */
    @Override
    public void removeItem(Integer itemId) {
        List<Item> items = salesOrder.getItemsList();
        for (Iterator<Item> itemIterator = items.iterator(); itemIterator.hasNext(); ) {
            int id = itemIterator.next().getItemId();
            if (id == itemId){
                itemIterator.remove();
            }
        }
        salesOrder.setItemsList(items);
        orders.put(salesOrder.getOrderId(), salesOrder);
    }

    /**
     * Get the total price of the Sales order.
     * @param orderId : Id of the order which the cost should be calculated.
     * @return : Total cost.
     */
    @Override
    public double getTotalPrice(Integer orderId) {
        double total = 0.0;
        salesOrder = orders.get(orderId);
        for (Item item : salesOrder.getItemsList()) {
            total += item.getPrice();
            System.out.println(item.toString());
        }
        return total;
    }

    public SalesOrder getSalesOrder(int orderId) {
        return orders.get(orderId);
    }

}
