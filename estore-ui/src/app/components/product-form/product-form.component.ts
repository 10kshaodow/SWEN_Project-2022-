import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ProductService } from 'src/app/services/product/product.service';
import { Product } from 'src/app/types/Product';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css'],
})
export class ProductFormComponent implements OnInit {
  @Input() productId?: number;
  product: Product = {
    productType: 1,
    name: '',
    price: 0,
    quantity: 0,
    description: '',

    fileName: null,
    fileSource: null,
  } as Product;

  displaySource?: string;

  @Output() errorChange: EventEmitter<string | undefined> = new EventEmitter<
    string | undefined
  >();
  @Output() onLeave: EventEmitter<void> = new EventEmitter<void>();

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    if (this.productId) {
      this.productService.getProduct(this.productId).subscribe((_product) => {
        this.product = _product;
        this.displaySource = _product.fileName ?? undefined;
      });
    }
  }

  setName(message: string) {
    this.product.name = message;
  }
  setPrice(message: number) {
    this.product.price = message;
  }
  setQuantity(message: number) {
    this.product.quantity = message;
  }
  setDesc(message: string) {
    this.product.description = message;
  }

  setProductType(message: string) {
    if (message == 'Accessory') {
      this.product.productType = 2;
      return;
    }

    this.product.productType = 1;
  }

  setError(message?: string) {
    this.errorChange.emit(message);
  }

  submit() {
    if (!this.product.id) {
      this.createNewProduct();
    } else {
      this.updateExistingProduct();
    }
  }

  /**
   * If the product is valid tell
   * the server to update the existing product
   */
  private updateExistingProduct() {
    if (!this.validateState() || !this.product?.id) return;

    console.log(`Updating`);
    console.log(JSON.stringify(this.product, null, 2));
    console.log();

    this.productService.updateProduct(this.product).subscribe((onServer) => {
      // console.log(`API Response ${JSON.stringify(onServer, null, 2)}`);
      if (onServer) {
        // console.log('Updated');
        this.navigateBack();
      } else {
        this.setError('Internal Error Updating Product Please Try again Later');
      }
    });
  }

  /**
   * If the product is valid tell
   * the server to create the new product
   */
  private createNewProduct() {
    if (!this.validateState()) return;

    console.log(`Creating`);
    console.log(JSON.stringify(this.product, null, 2));
    console.log();

    this.productService
      .addProduct(this.product as Product)
      .subscribe((onServer) => {
        // console.log(`API Response ${JSON.stringify(onServer, null, 2)}`);
        if (onServer) {
          // console.log('Created');
          this.navigateBack();
        } else {
          this.setError(
            'Internal Error Creating Product Please Try again Later'
          );
        }
      });
  }

  /**
   * Validate that all the necassary inputs are filled in
   */
  private validateState() {
    this.setError(undefined);
    if (this.product!.name.length <= 0) {
      this.setError(`Missing Name`);
      return false;
    }
    if (this.product!.price < 0) {
      this.setError(`Price cannot be negative`);
      return false;
    }
    if (this.product!.quantity < 0) {
      this.setError(`Quantity cannot be negative`);
      return false;
    }
    if (this.product!.description.length <= 0) {
      this.setError(`Missing Desc`);
      return false;
    }
    return true;
  }

  /**
   * When the file input changes get
   * the file and ensure that a file was selected
   */
  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    let fileList: FileList | null = input.files;
    let file = fileList?.item(0);
    if (!file) return;
    this.saveFileProperties(file);
  }

  /**
   * manipulate the file object to save the
   * base64 format and display the base64 format of the file
   *
   * @param file file object to be manipulated for display and saving
   */
  private saveFileProperties(file: File) {
    const reader = new FileReader();
    const fileName = file.name;
    const fileType = file.type;
    let replaceString = 'data:' + fileType.trim() + ';base64,';

    console.log(file);
    console.log(fileName);
    console.log(fileType);
    console.log();

    reader.readAsDataURL(file);
    reader.onload = (_event) => {
      if (!reader.result) return;
      this.displaySource = reader.result as string;
      this.product!.fileSource = {
        base64: this.displaySource.replace(replaceString, ' '),
        // base64: this.displaySource,
        orginalName: fileName,
      };
    };
  }

  navigateBack() {
    this.onLeave.emit();
  }
}
