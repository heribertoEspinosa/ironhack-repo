package com.project.ironHack;

import com.project.ironHack.entites.Order;
import com.project.ironHack.exceptions.OutOfStockException;
import com.project.ironHack.factory.OrderProcessorFactory;
import com.project.ironHack.services.Inventory;
import com.project.ironHack.services.Notification;
import com.project.ironHack.services.Payment;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class SystemManager {

    private final OrderProcessorFactory orderProcessorFactory;
    private final Inventory inventoryService;

    public void processOrder(Order order){
        Payment p = orderProcessorFactory.getPayment(order);
        Notification n = orderProcessorFactory.getNotification(order);

        try {
            inventoryService.verifyInventory(order);
        } catch (OutOfStockException e) {
            System.out.println("Error: " + e.getMessage());
        }

        p.process(order.getAmount());
        n.sendNotification("title", "message");
        System.out.println("factory: " + orderProcessorFactory.toString());
        System.out.println("Payment: " + p.toString());
        System.out.println("Notification: " + n.toString());

    }
}
