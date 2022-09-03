import { Component, OnInit } from '@angular/core';
import { Account } from './interfaces/account';
import { AccountDetailComponent } from './account-detail/account-detail.component';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Footware Shoe Estore';
  account: Account | undefined;


  ngOnInit(): void {
    this.fetchLocalStorage();
  }


  fetchLocalStorage(): void {
    const userJson = JSON.parse(localStorage.getItem('currentUser') || '{}');
    this.account = <Account>userJson;
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
