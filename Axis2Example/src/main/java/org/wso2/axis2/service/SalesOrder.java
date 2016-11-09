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

import org.wso2.axis2.service.Item;

import java.util.Date;
import java.util.List;

/**
 * POJO class for sales order.
 * Contains the following components.
 *  1. OrderId
 *  2. Date/ Time
 *  4. List of Items
 *  5. Total Price
 */
public class SalesOrder {
    private Integer orderId;
    private Date date;
    private List<Item> itemsList;
    private double totalPrice;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Item> itemsList) {
        this.itemsList = itemsList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Sales Order : \n\t OrderId : " + orderId + " \n\t OrderDate : " + date + "\n\t Item Count : " +
                itemsList.size() + " \n\t Total Price : " + new SalesOrderService().getTotalPrice(orderId);
    }
}
