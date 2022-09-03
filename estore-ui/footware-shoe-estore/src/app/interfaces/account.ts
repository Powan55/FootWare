import { Cart } from "./cart";

export interface Account {
    userName: string;
    displayName: string;
    isAdmin: boolean;
    cart: Cart;
}