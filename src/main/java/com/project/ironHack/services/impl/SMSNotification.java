package com.project.ironHack.services.impl;

import com.project.ironHack.services.Notification;

public class SMSNotification implements Notification {
    @Override
    public void sendNotification(String title, String message) {
        System.out.println("Send sms");
    }
}
