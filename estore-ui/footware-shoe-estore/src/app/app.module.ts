import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms'; // <-- NgModel lives here

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { InventoryComponent } from './inventory/inventory.component';
import { ShoeProductPageComponent } from './shoe-product-page/shoe-product-page.component';
import { MessagesComponent } from './messages/messages.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ShoeSearchComponent } from './shoe-search/shoe-search.component';
import { AccountsComponent } from './accounts/accounts.component';
import { AccountDetailComponent } from './account-detail/account-detail.component';
import { CartComponent } from './cart/cart.component';
import { CartShoesComponent } from './cart-shoes/cart-shoes.component';
import { WishlistComponent } from './wishlist/wishlist.component';
import { WishlistShoeComponent } from './wishlist-shoe/wishlist-shoe.component';
import { PurchaseHistoryComponent } from './purchase-history/purchase-history.component';
import { TransactionDetailComponent } from './transaction-detail/transaction-detail.component';

import { CouponsComponent } from './coupons/coupons.component';

@NgModule({
  declarations: [
    AppComponent,
    InventoryComponent,
    ShoeProductPageComponent,
    MessagesComponent,
    DashboardComponent,
    ShoeSearchComponent,
    AccountsComponent,
    AccountDetailComponent,
    CartComponent,
    CartShoesComponent,
    WishlistComponent,
    WishlistShoeComponent,
    PurchaseHistoryComponent,
    TransactionDetailComponent,
    CouponsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
