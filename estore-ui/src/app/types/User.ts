import { Address } from './Address'; 
import { Cart } from './Cart';
import { CreditCard } from './CreditCard';
import { UserType } from './UserType';

export interface User {
    username: string
    name: string; 
    type: UserType; 
    address: Address;
    creditCard: CreditCard;
    orderHistory: string[];
    cart: Cart;
    notifications: number[]; 
    notificationHistory: number[];
}