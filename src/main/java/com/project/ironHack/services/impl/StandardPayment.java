package com.project.ironHack.services.impl;

import com.project.ironHack.services.Payment;
import lombok.ToString;

@ToString
public class StandardPayment implements Payment {
    @Override
    public boolean process(double amount) {
        return true;
    }
}
