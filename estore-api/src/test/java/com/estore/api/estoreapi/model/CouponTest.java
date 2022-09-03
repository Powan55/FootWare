package com.estore.api.estoreapi.model;

import com.estore.api.estoreapi.persistence.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.*;

import com.estore.api.estoreapi.model.*;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Model-tier")
public class CouponTest {
    private Coupon coupon = new Coupon("yzy", 0.15);
    private Coupon couponSame = new Coupon("yzy", 0.25);
    private Coupon coupon2 = new Coupon("yzyszn", 0.15);

    @Test
    public void TestGetDiscount(){
        assertEquals(0.15, coupon.getDiscount());
    }

    @Test
    public void TestEquals(){
        Object notCoupon = new Object();
        assertNotEquals(coupon, coupon2);
        assertNotEquals(coupon, notCoupon);
        assertEquals(coupon, couponSame);

    }
}
