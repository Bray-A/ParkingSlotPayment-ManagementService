package com.test.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("VIP")
public class VipCalculate implements PriceCalculable{

    @Override
    public double calculateTotalPrice(String type) {
        switch (type) {
            case "Light":
                return 500;
            case "Medium":
                return 1000;
            case "Heavy":
                return 1500;
            default:
                return 0;
        }
    }
}
