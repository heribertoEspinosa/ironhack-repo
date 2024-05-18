package com.project.ironHack.services.impl;

import com.project.ironHack.entites.Order;
import com.project.ironHack.exceptions.OutOfStockException;
import com.project.ironHack.services.Inventory;

public class InventoryService implements Inventory {

    @Override
    public void verifyInventory(Order order) throws OutOfStockException {
        if(!order.isInStock()){
            throw new OutOfStockException("Out of stock");
        }
    }
}
