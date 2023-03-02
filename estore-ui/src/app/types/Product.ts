import { ImageSource } from './ImageSource';

export interface Product {
  id: number;
  productType: 1 | 2;
  name: string;
  price: number;
  quantity: number;
  description: string;

  fileName: string | null;
  fileSource: ImageSource | null;
  sponsors: string[];
}
