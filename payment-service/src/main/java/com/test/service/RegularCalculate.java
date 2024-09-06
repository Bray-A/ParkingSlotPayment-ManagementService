package com.test.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class RegularCalculate implements PriceCalculable{
    @Override
    public double calculateTotalPrice(String type) {
        switch (type) {
            case "Light":
            case "Medium":
                return 100;
            case "Heavy":
                return 150;
            default:
                return 0;
        }
    }
}
