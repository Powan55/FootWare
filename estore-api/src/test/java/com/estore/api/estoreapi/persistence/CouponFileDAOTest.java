package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.persistence.AccountFileDAO;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.*;
import java.io.*;

import com.estore.api.estoreapi.model.*;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;


/**
 * @author Mathew Owusu Jr, Laxmi Poudel, Matthew Zhang
 */
@Tag("Persistence-tier")
public class CouponFileDAOTest {
    // CuT
    private CouponFileDAO couponFileDAO;

    // Mock object
    private ObjectMapper mockObjectMapper;
    // Test objects
    private Coupon[] mockCouponList;

    @BeforeEach
    public void setup() throws StreamReadException, DatabindException, IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        mockCouponList = new Coupon[3];


        mockCouponList[0] = new Coupon("test1", .50);
        mockCouponList[1] = new Coupon("test2", .75);
        mockCouponList[2] = new Coupon("test3", 1);
        
        when(mockObjectMapper.readValue(new File("file.txt"), Coupon[].class)).thenReturn(mockCouponList);
        couponFileDAO = new CouponFileDAO("file.txt", mockObjectMapper);
    }

    @Test
    public void testGetCoupons() throws IOException {
        Coupon[] coupons = couponFileDAO.getCoupons(); 
        assertArrayEquals(mockCouponList, coupons);
    }

    @Test
    public void testGetCoupon() throws IOException {
        assertNotNull(couponFileDAO.getCoupon("test1"));
        assertNull(couponFileDAO.getCoupon("random"));
    }

    @Test
    public void testCreateCoupon() throws IOException {
        Coupon coupon = new Coupon("test4", .25);  
        Coupon existing = new Coupon("test1", .50);

        assertNotNull(couponFileDAO.createCoupon(coupon));
        assertNull(couponFileDAO.createCoupon(existing));

    }

    @Test
    public void testDeleteCoupon() throws IOException {
        assertEquals(true, couponFileDAO.deleteCoupon("test1"));
        assertEquals(false, couponFileDAO.deleteCoupon("test1"));
    }

}
