import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { Cart } from '../interfaces/cart';
import { Shoe } from '../interfaces/shoe';
import { Account } from '../interfaces/account';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-account-detail',
  templateUrl: './account-detail.component.html',
  styleUrls: ['./account-detail.component.css']
})
export class AccountDetailComponent implements OnInit {
  @Input() account?: Account | undefined;

  items: Shoe[] = [];

  cart: Cart = {
    items: this.items
  };

  data: Account = {
    userName: "d",
    displayName: "nd",
    isAdmin: false,
    cart: this.cart
  };
  
  constructor(private route: ActivatedRoute, private accountService: AccountService, 
    private router: Router) { }

    
  ngOnInit(): void {
    const username = (this.route.snapshot.paramMap.get('username'));
    if (username != null){
      this.accountService.getAccount(username).subscribe(account => {this.account = account;
        if (account != undefined){
          const data = JSON.stringify(this.account);
          localStorage.setItem('currentUser', data);
        }else{
          this.router.navigate(['/login']);
        }
      });
    }
  }
}
