package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ExitCodeEvent;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.*;


/**
 * @author Mathew Owusu Jr
 */
@Component
public class CouponFileDAO implements CouponDAO{
    private static final Logger LOG = Logger.getLogger(AccountFileDAO.class.getName());
    Map<String, Coupon> couponMap; // Provides a local cache of the coupons objects
    // so that we don't need to read from the file
    // each time
    private ObjectMapper objectMapper; // Provides conversion between Hero
                                       // objects and JSON text format written
                                       // to the file
    private String filename; // Filename to read from and write to

    /**
     * 
     * @param filename
     * @param objectMapper
     * @throws IOException
     */
    public CouponFileDAO(@Value("${coupons.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        couponMap = new TreeMap<>();
        load(); // load the coupons from the file
    }


    /**
     * 
     * @return
     * @throws IOException
     */
    private boolean save() throws IOException {
        Coupon[] couponArray = getCouponArray(null);

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), couponArray);
        return true;
    }


    /**
     * 
     * @return
     * @throws IOException
     */
    private boolean load() throws IOException {
        // Deserializes the JSON objects from the file into an array of heroes
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Coupon[] couponArray = objectMapper.readValue(new File(filename), Coupon[].class);

        // Add each coupon to the tree map
        for (Coupon coupon : couponArray) {
            couponMap.put(coupon.getCode(), coupon);
        }
        return true;
    }


    /**
     * 
     * @param containsText
     * @return
     */
    private Coupon[] getCouponArray(String containsText) {
        ArrayList<Coupon> couponArrayList = new ArrayList<>();

        for (Coupon coupon : couponMap.values()) {
            if (containsText == null || coupon.getCode().contains(containsText)) {
                couponArrayList.add(coupon);
            }
        }

        Coupon[] couponArray = new Coupon[couponArrayList.size()];
        couponArrayList.toArray(couponArray);
        return couponArray;
    }

    @Override
    public Coupon[] getCoupons() throws IOException {
        synchronized (couponMap) {
            return getCouponArray(null);
        }
    }

    @Override
    public Coupon getCoupon(String code) throws IOException {
        synchronized (couponMap) {
            if (couponMap.containsKey(code))
                return couponMap.get(code);
            else
                return null;
        }
    }

    @Override
    public Coupon createCoupon(Coupon coupon) throws IOException {
        synchronized (couponMap) {
            Coupon newCoupon;
            newCoupon = new Coupon(coupon.getCode(), coupon.getDiscount());

            for (Coupon existing : couponMap.values()) {
                if (coupon.equals(existing)) {
                    return null;
                }
            }

            couponMap.put(newCoupon.getCode(), newCoupon);
            save();
            return newCoupon;
        }
    }

    @Override
    public Boolean deleteCoupon(String code) throws IOException {
        synchronized (couponMap) {
            if (couponMap.containsKey(code)) {
                couponMap.remove(code);
                save();
                return true;
            }
            save();
            return false;
        }
    }
    
}
