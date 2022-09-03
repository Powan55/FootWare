import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountDetailComponent } from './account-detail/account-detail.component';
import { AccountsComponent } from './accounts/accounts.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { InventoryComponent } from './inventory/inventory.component';
import { ShoeProductPageComponent } from './shoe-product-page/shoe-product-page.component';
import { CartComponent } from './cart/cart.component';
import { CartShoesComponent } from './cart-shoes/cart-shoes.component';
import {WishlistComponent} from './wishlist/wishlist.component';
import {WishlistShoeComponent} from './wishlist-shoe/wishlist-shoe.component';
import {PurchaseHistoryComponent} from './purchase-history/purchase-history.component';
import {TransactionDetailComponent} from './transaction-detail/transaction-detail.component';
import { CouponsComponent } from './coupons/coupons.component';


const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'inventory', component: InventoryComponent },
  { path: 'inventory/:id', component: ShoeProductPageComponent},
  { path: 'login', component: AccountsComponent},
  { path: 'login/:username', component: AccountDetailComponent},
  { path: 'cart', component: CartComponent},
  { path: 'cart/:id', component: CartShoesComponent},
  { path: 'wishlist', component: WishlistComponent },
  { path: 'wishlist/:id', component: WishlistShoeComponent },
  { path: 'purchases', component: PurchaseHistoryComponent },
  { path: 'purchases/:id', component: TransactionDetailComponent },
  { path: 'coupons', component: CouponsComponent}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}