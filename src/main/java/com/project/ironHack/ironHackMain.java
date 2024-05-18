package com.project.ironHack;

import com.project.ironHack.entites.NotificationType;
import com.project.ironHack.entites.Order;
import com.project.ironHack.entites.PaymentType;
import com.project.ironHack.factory.OrderProcessorFactory;
import com.project.ironHack.services.Inventory;
import com.project.ironHack.services.impl.InventoryService;

public class ironHackMain {
    public static void main(String[] args){
        Inventory inventory = new InventoryService();
        OrderProcessorFactory factory = new OrderProcessorFactory();
        SystemManager systemManager = new SystemManager(factory, inventory);

        systemManager.processOrder(Order.builder()
                .id("123")
                .name("OrderName")
                .type(PaymentType.STANDARD)
                .inStock(true)
                .amount(123.45)
                .notificationType(NotificationType.EMAIL)
                .build());
        System.out.println("SystemManager: " + systemManager.toString());
    }
}
