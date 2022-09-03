import { Component, OnInit } from '@angular/core';
import { Coupon } from '../interfaces/coupon';
import { CouponService } from '../services/coupon.service';
import { Account } from '../interfaces/account';
import { MessageService } from '../services/message.service';
import { Location } from '@angular/common';
@Component({
  selector: 'app-coupons',
  templateUrl: './coupons.component.html',
  styleUrls: ['./coupons.component.css']
})
export class CouponsComponent implements OnInit {
  coupons: Coupon[] = [];
  coupon: Coupon = {
    code: "",
    discount: 0.0
  }
  constructor(private couponService: CouponService,
    private location: Location) { }

  ngOnInit(): void {
    this.getCoupons();
  }

  current(): void {
    this.location.getState();
  }

  getCoupons(): void {
    this.couponService.getCoupons().subscribe(coupons => this.coupons = coupons);
  }

  createCoupon(code: string, discount: string): void {
    code = code.trim();
    discount = discount.trim();
    if (!code || !discount){
      return;
    }
    
    const c: Coupon = {
      code: code,
      discount: Number(discount)
    };

    this.couponService.addCoupon(c).subscribe(coupon => this.coupons.push(coupon));
  }

  deleteCoupon(coupon: Coupon): void {
    this.coupons = this.coupons.filter(c => c !== coupon);
    this.couponService.deleteCoupon(coupon.code).subscribe();
  }

  isAccountDefined(): boolean {
    const userJson = JSON.parse(localStorage.getItem('currentUser') || '{}');
    const accountObject = <Account>userJson;
    if (accountObject.displayName == undefined) {
      return false;
    } else {
      return true;
    }
  }

  isAdmin(): boolean {
    const userJson = JSON.parse(localStorage.getItem('currentUser') || '{}');
    const accountObject = <Account>userJson;
    if (accountObject.displayName == undefined) {
      return false;
    } else {
      return accountObject.isAdmin;
    }
  }
}
