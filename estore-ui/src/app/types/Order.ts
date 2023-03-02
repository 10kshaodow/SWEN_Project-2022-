import { Address } from "./Address";
import { CreditCard } from "./CreditCard";
import { OrderItem } from "./OrderItem";
import { OrderStatus } from "./OrderStatus";

export interface Order {
    id: number; 
    username: string;
    orderDate: Date;
    status: OrderStatus;
    address: Address;
    creditCard: CreditCard;
    orderItemList: OrderItem[];
    subtotal: number;
    taxes: number;
    total: number;
}