import { Component, OnInit } from '@angular/core';
import { AccountService } from '../services/account.service';
import { Router } from '@angular/router';
import { Cart } from '../interfaces/cart';
import { Shoe } from '../interfaces/shoe';
import { Account } from '../interfaces/account';
import { Location } from '@angular/common';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {
  accountsList: Account[] = [];
  signUpMode: boolean = false;
  
  items: Shoe[] = [];

  cart: Cart = {
    items: this.items
  };

  account: Account = {
    userName: "n/a",
    displayName: "n/a",
    isAdmin: false,
    cart: this.cart
  };
  

  
  constructor(private accountService: AccountService, private router: Router,
    private location: Location) { }

  ngOnInit(): void {
    this.getAccounts();
    this.fetchLocalStorage();
  }


  isAccountDefined(): boolean {
    const userJson = JSON.parse(localStorage.getItem('currentUser')|| '{}' );
    const accountObject = <Account> userJson;
    if (accountObject.displayName == undefined){
      return false;
    } else{
      return true;
    }
  }

  fetchLocalStorage(): void {
    const userJson = JSON.parse(localStorage.getItem('currentUser')|| '{}' );
    this.account = <Account> userJson;
  }


  fetchOldUsername(): string {
    const userJson = JSON.parse(localStorage.getItem('currentUser')|| '{}' );
    const accountObject = <Account> userJson;
    return accountObject.userName;
  }

  reloadPage() {
    setTimeout(()=>{
      window.location.reload();
    }, 200);
  }

  current(): void {
    this.location.getState();
  }

  logout(): void {
    localStorage.removeItem('currentUser');
  }


  goToAccount(username: string): void{
    this.router.navigate([`/login/${username}`]);
    this.accountsList.forEach(element => {
      if( element.userName == username){
        this.reloadPage();
      }
    });
  }
  
  
  getAccounts(): void {
    this.accountService.getAccounts()
    .subscribe(accountsList => this.accountsList = accountsList);
  }


  signUp(): void {
    this.signUpMode = !this.signUpMode;
  }


  createAccount(username: string, displayName: string): void{
    if (!username || !displayName){
      return;
    }

    this.account.userName = username.trim();
    this.account.displayName = displayName.trim();
    this.account.isAdmin=false;

    //const logIn = false;
    this.accountService.addAccount(this.account)
    .subscribe(account => {
      
        this.accountsList.push(account);
        if (account.userName != null){
          this.goToAccount(account.userName);

        }
        //logIn
    });
  }


  deleteAccount(account: Account): void {
    this.accountsList = this.accountsList.filter(a => a !== account);
    this.accountService.deleteAccount(account.userName).subscribe();
    this.logout();
  }

  save(): void {
    if(this.account){
      this.accountService.updateAccount(this.fetchOldUsername(), this.account).subscribe(
        object => {this.account = <Account> object;
          localStorage.setItem('currentUser', JSON.stringify(this.account));
          () => this.current();  
          this.reloadPage();
        }
        );
    }
  }
}
