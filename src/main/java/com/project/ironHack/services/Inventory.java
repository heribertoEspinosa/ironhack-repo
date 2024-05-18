package com.project.ironHack.services;

import com.project.ironHack.entites.Order;
import com.project.ironHack.exceptions.OutOfStockException;

public interface Inventory {
    void verifyInventory(Order order) throws OutOfStockException;
}
