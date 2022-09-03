package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;


import com.estore.api.estoreapi.persistence.*;
import com.estore.api.estoreapi.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Controller-tier")
public class CouponControllerTest {
    private CouponDAO mockCouponDAO;
    private CouponController couponController;
    private Coupon coupon = new Coupon("test", 0.15);

    @BeforeEach
    public void setupInventoryController(){
        mockCouponDAO = mock(CouponDAO.class);
        couponController = new CouponController(mockCouponDAO);
    }

    @Test 
    public void TestCreateCoupon() throws IOException{
        // Setup
        when(mockCouponDAO.createCoupon(coupon)).thenReturn(coupon);

        // Invoke
        ResponseEntity<Coupon> response = couponController.createCoupon(coupon);

        // Analzye
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(coupon, response.getBody());
    }

    @Test
    public void testCreateCouponFailed() throws IOException {
        // Setup
        // when createShoe is called, return false simulating failed
        // creation and save
        when(mockCouponDAO.createCoupon(coupon)).thenReturn(null);

        // Invoke
        ResponseEntity<Coupon> response = couponController.createCoupon(coupon);

        // Analzye
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateShoeHandleException() throws Exception {
        // Setup
        
        doThrow(new IOException()).when(mockCouponDAO).createCoupon(coupon);
        
        // Invoke
        ResponseEntity<Coupon> response = couponController.createCoupon(coupon);

        // Analzye
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test 
    public void TestGetCoupons() throws IOException{
        // Setup
        Coupon[] coupons = new Coupon[2];
        Coupon coupon2 = new Coupon("Test 2", 0.25);
        coupons[0] = coupon;
        coupons[1] = coupon2;
        when(mockCouponDAO.getCoupons()).thenReturn(coupons);

        // Invoke
        ResponseEntity<Coupon[]> response = couponController.getCoupons();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coupons, response.getBody());
    }

    @Test
    public void testGetCouponsHandleException() throws IOException {
        // Setup
        // When getCOupons is called on the Mock Coupons DAO, throw an IOException
        doThrow(new IOException()).when(mockCouponDAO).getCoupons();

        // Invoke
        ResponseEntity<Coupon[]> response = couponController.getCoupons();

        // Analzye
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test 
    public void TestGetCoupon() throws IOException{
        // Setup
        // When the same string is passed in, our mock Coupon DAO will return the Coupon object
        when(mockCouponDAO.getCoupon(coupon.getCode())).thenReturn(coupon);

        // Invoke
        ResponseEntity<Coupon> response = couponController.getCoupon(coupon.getCode());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coupon, response.getBody());
    }

    @Test
    public void testGetCouponNotFound() throws Exception{
        // Setup
        String notFound = "NotReal";
        // When the same String is passed in, our mock Coupon DAO will return null, simulating
        // no coupon found
        when(mockCouponDAO.getCoupon(notFound)).thenReturn(null);

        // Invoke
        ResponseEntity<Coupon> response = couponController.getCoupon(notFound);

        // Anaylze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetShoeHandleException() throws Exception {
        // Setup
        String notFound = "NotReal";
        // When getCoupon is called on the Mock Coupon DAO, throw an IOException
        doThrow(new IOException()).when(mockCouponDAO).getCoupon(notFound);
        
        // Invoke
        ResponseEntity<Coupon> response = couponController.getCoupon(notFound);

        // Anaylze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test 
    public void TestDeleteCoupon() throws IOException{
        // Setup
        when(mockCouponDAO.deleteCoupon(coupon.getCode())).thenReturn(true);

        // Invoke
        ResponseEntity<Coupon> response = couponController.deleteCoupon(coupon.getCode());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test 
    public void TestDeleteCouponFail() throws IOException{
        // Setup
        String notFound = "NotReal";
        when(mockCouponDAO.deleteCoupon(notFound)).thenReturn(false);

        // Invoke
        ResponseEntity<Coupon> response = couponController.deleteCoupon(notFound);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteCouponHandleException() throws IOException {
        // Setup
        String notFound = "NotReal";
        // when deleteCoupon is called on the Mock Coupon DAO, throw an IOException
        doThrow(new IOException()).when(mockCouponDAO).deleteCoupon(notFound);

        // Invoke
        ResponseEntity<Coupon> response = couponController.deleteCoupon(notFound);

        // Analzye
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}
