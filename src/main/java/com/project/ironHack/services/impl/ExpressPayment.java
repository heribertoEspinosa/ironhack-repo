package com.project.ironHack.services.impl;

import com.project.ironHack.services.Payment;

public class ExpressPayment implements Payment {

    @Override
    public boolean process(double amount) {
        return false;
    }
}
