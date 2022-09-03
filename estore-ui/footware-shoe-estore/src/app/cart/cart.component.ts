import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { Shoe } from '../interfaces/shoe';
import { Transaction } from '../interfaces/transaction';
import { ShoeService } from '../services/shoe.service';
import { Account } from '../interfaces/account';
import { Coupon } from '../interfaces/coupon';
import { AccountService } from '../services/account.service';
import { CouponService } from '../services/coupon.service';
import { MessageService } from '../services/message.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  shoes: Shoe[] = [];
  couponsList: Coupon[] = []

  constructor(
    private accountService: AccountService,
    private couponService: CouponService,
    private messageService: MessageService,
    private shoeService: ShoeService,

    private router: Router,
    private location: Location) { }


  ngOnInit(): void {
    this.getCart();
  }

  getUsername(): string {
    const userJson = JSON.parse(localStorage.getItem('currentUser') || '{}');
    const accountObject = <Account>userJson;
    return accountObject.userName;
  }

  getCart(): void {
    this.accountService
      .getCart(this.getUsername())
      .subscribe((shoes) => (this.shoes = shoes));
  }

  current(): void {
    this.location.getState();
  }

  reloadPage() {
    setTimeout(() => {
      window.location.reload();
    }, 200);
  }

  deleteShoe(shoe: Shoe): void {
    this.shoes = this.shoes.filter((s) => s !== shoe);
    this.accountService
      .removeShoeFromCart(this.getUsername(), shoe.id)
      .subscribe();
  }

  getTotalPrice(): number {
    let price: number = 0;
    this.shoes.forEach((shoe) => (price += shoe.price));
    return price;
  }

  applyCoupon(code: string): void {
    if (code.length) {
      code = code.trim();
      this.couponService.getCoupon(code).subscribe((coupon) => {
        if (coupon != null) {
          if (!this.used(this.getUsername(), coupon)) {
            this.accountService
              .applyCouponToCart(this.getUsername(), coupon)
              .subscribe();
            this.reloadPage();
          }
        }
      });
    }
  }

  getCoupons(username: string): void {
    this.accountService.getUsedCouponsList(username).subscribe((cs) => this.couponsList = cs);

  }

  used(username: string, c: Coupon): boolean {
    var result = false;
    this.getCoupons(username);
    // var coupons: Coupon[] = []
    // coupons.forEach(coupon => {
    result = this.couponsList.includes(c);
    //   if (coupon === c){
    //     result = true;
    //   }
    // });
    return result;
  }

  checkout(): void {

    this.shoeService.updateShoes(this.shoes).subscribe(shoes => {
      this.shoes = shoes

      let transaction: Transaction = {
        price: this.getTotalPrice(),
        shoes: shoes
      }
      this.accountService.addToPurchaseHistory(this.getUsername(), transaction).subscribe(transaction =>
        this.router.navigate([`/purchases/${transaction.id}`]
        ));

      this.shoes.forEach(shoe => {
        this.log(`shoe id ${shoe.id} updated quantity is ${shoe.quantity}`)
      });
      this.shoes.length = 0

      this.accountService.removeAllShoesFromCart(this.getUsername()).subscribe();

    /*this.shoeService.updateShoes(this.shoes).subscribe((shoes) => {
      this.shoes = shoes;
      this.shoes.forEach((shoe) =>
        this.log(`shoe id ${shoe.id} updated quantity is ${shoe.quantity}`)
      );
      this.shoes.length = 0;*/

    });
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

  private log(message: string) {
    this.messageService.add(`ShoeService: ${message}`);
  }
}
