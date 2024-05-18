package com.project.ironHack.factory;

import com.project.ironHack.entites.NotificationType;
import com.project.ironHack.entites.Order;
import com.project.ironHack.entites.PaymentType;
import com.project.ironHack.exceptions.OutOfStockException;
import com.project.ironHack.services.Inventory;
import com.project.ironHack.services.Notification;
import com.project.ironHack.services.Payment;
import com.project.ironHack.services.impl.EmailNotification;
import com.project.ironHack.services.impl.ExpressPayment;
import com.project.ironHack.services.impl.SMSNotification;
import com.project.ironHack.services.impl.StandardPayment;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class OrderProcessorFactory {

    public Payment getPayment(Order order){
        if (PaymentType.STANDARD.equals(order.getType())){
            return new StandardPayment();
        } else if(PaymentType.EXPRESS.equals(order.getType())){
            System.out.println("Tipo de pago: "+ order.getType());
            return new ExpressPayment();
        }
        return null;
    }

    public Notification getNotification(Order order){
        if(NotificationType.EMAIL.equals(order.getNotificationType())){
            return new EmailNotification();
        } else if (NotificationType.SMS.equals(order.getNotificationType())) {
            return new SMSNotification();
        }
        return null;
    }
}
