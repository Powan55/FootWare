import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Shoe } from '../interfaces/shoe';
import { ShoeService } from '../services/shoe.service';
import { Account } from '../interfaces/account';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-cart-shoes',
  templateUrl: './cart-shoes.component.html',
  styleUrls: ['./cart-shoes.component.css']
})
export class CartShoesComponent implements OnInit {
  @Input() shoe?: Shoe | undefined;

  constructor(private route: ActivatedRoute, private accountService: AccountService, 
    private location: Location) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.accountService.getShoeFromCart(this.getUsername(), id).subscribe(shoe => this.shoe = shoe);
    //this.shoeService.getShoe(id).subscribe(shoe => this.shoe = shoe);
  }

  getUsername(): string {
    const userJson = JSON.parse(localStorage.getItem('currentUser')|| '{}' );
    const accountObject = <Account> userJson;
    return accountObject.userName;
  }

  goBack(): void {
    this.location.back();
    }
  
  current(): void {
    this.location.getState();
  }

  save(): void {
    if (this.shoe) {
      this.accountService.updateShoeInCart(this.getUsername(), this.shoe).subscribe(() => this.current());
    }
  }

}
