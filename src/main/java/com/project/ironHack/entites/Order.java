package com.project.ironHack.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Order {

    private String id;
    private String name;
    private PaymentType type;
    private NotificationType notificationType;
    private double amount;
    private boolean inStock;

}
