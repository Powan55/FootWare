package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.*;

/**
 * @author Mathew Owusu Jr
 */
public interface CouponDAO {
    /**
     * 
     * @return
     * @throws IOException
     */
    Coupon[] getCoupons() throws IOException;


    /**
     * 
     * @param code
     * @return
     * @throws IOException
     */
    Coupon getCoupon(String code) throws IOException;


    /**
     * 
     * @param name
     * @param discount
     * @return
     * @throws IOException
     */
    Coupon createCoupon(Coupon coupon) throws IOException;


    /**
     * 
     * @param code
     * @return
     * @throws IOException
     */
    Boolean deleteCoupon(String code) throws IOException;
}
