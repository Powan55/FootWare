package com.estore.api.estoreapi.model;

import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Coupon 
 * @author Mathew Owusu Jr
 */
public class Coupon {
    private static final Logger LOG = Logger.getLogger(Coupon.class.getName());

    @JsonProperty("code")
    private String code;
    @JsonProperty("discount")
    private double discount;

    /**
     * Creates a coupon object
     * @param code the name of the coupon
     * @param discount the amount the discount is
     */
    public Coupon(@JsonProperty("code") String code,
    @JsonProperty("discount") double discount) {
        this.code = code;
        this.discount = discount;
    }

    /**
     * 
     * @return the name of the coupon object
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * @return the discount amount
     */
    public double getDiscount() {
        return discount;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Coupon){
            Coupon otherCoupon = (Coupon) other;
            return code.equals(otherCoupon.code);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.code.hashCode();
    }
}
