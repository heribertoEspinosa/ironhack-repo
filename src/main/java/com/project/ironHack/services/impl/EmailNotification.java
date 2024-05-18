package com.project.ironHack.services.impl;

import com.project.ironHack.services.Notification;
import lombok.ToString;

@ToString
public class EmailNotification implements Notification {
    @Override
    public void sendNotification(String title, String message) {
        System.out.println("Send email");
    }
}
