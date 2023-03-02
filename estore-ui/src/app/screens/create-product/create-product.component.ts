import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from 'src/app/services/product/product.service';
import { UserService } from 'src/app/services/user/user.service';
import { Product } from 'src/app/types/Product';
import { User } from 'src/app/types/User';
import { UserType } from 'src/app/types/UserType';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css'],
})
export class CreateProductComponent implements OnInit {
  admin!: User;
  product: Product = {
    productType: 1,
    name: '',
    price: 0,
    quantity: 0,
    description: '',

    fileName: null,
    fileSource: null,
  } as Product;

  errorMessage: string | null = null;

  displaySource: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.getProductFromParams();

    // Guard against non admin user from accessing page
    if (!this.userService.isSignedIn()) {
      this.router.navigate(['/home']);
    }

    if (this.userService.getCurrentUser()?.type == UserType.Admin) {
      this.admin = this.userService.getCurrentUser();
    }

    // this is how we should be navigating not using the location way
    if (!(this.admin?.type == UserType.Admin)) {
      this.router.navigate(['/home']);
    }
  }

  submit() {
    if (!this.product.id) {
      this.createNewProduct();
    } else {
      this.updateExistingProduct();
    }
  }

  /* Event Handlers for the input component to set the fields of the product */

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

  /**
   * Ask the server for the specified product details
   *
   * product is specified from the route parameters
   */
  private getProductFromParams() {
    let paramId = this.route.snapshot.paramMap.get(`id`);
    if (paramId) {
      let id = Number(paramId);
      this.productService.getProduct(id).subscribe((_product) => {
        this.product = _product;
        this.displaySource = _product.fileName;
      });
    }
  }

  /**
   * If the product is valid tell
   * the server to update the existing product
   */
  private updateExistingProduct() {
    if (!this.validateState() || !this.product.id) return;

    // console.log(`Updating`);
    // console.log(this.product);
    // console.log();

    this.productService.updateProduct(this.product).subscribe((onServer) => {
      // console.log(`API Response ${JSON.stringify(onServer, null, 2)}`);
      if (onServer) {
        // console.log('Updated');
        this.navigateBack();
      } else {
        this.errorMessage =
          'Internal Error Updating Product Please Try again Later';
      }
    });
  }

  /**
   * If the product is valid tell
   * the server to create the new product
   */
  private createNewProduct() {
    if (!this.validateState()) return;

    console.log(`Sending ${JSON.stringify(this.product, null, 2)}\n`);

    this.productService
      .addProduct(this.product as Product)
      .subscribe((onServer) => {
        // console.log(`API Response ${JSON.stringify(onServer, null, 2)}`);
        if (onServer) {
          // console.log('Created');
          this.navigateBack();
        } else {
          this.errorMessage =
            'Internal Error Creating Product Please Try again Later';
        }
      });
  }

  /**
   * Validate that all the necassary inputs are filled in
   */
  private validateState() {
    this.errorMessage = null;
    if (this.product.name.length <= 0) {
      this.errorMessage = `Missing Name`;
      return false;
    }
    if (this.product.price < 0) {
      this.errorMessage = `Price cannot be negative`;
      return false;
    }
    if (this.product.quantity < 0) {
      this.errorMessage = `Quantity cannot be negative`;
      return false;
    }
    if (this.product.description.length <= 0) {
      this.errorMessage = `Missing Desc`;
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
    console.log(JSON.stringify(input, null, 2));
    console.log();
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

    // console.log(file);
    // console.log(fileName);
    // console.log(fileType);
    // console.log();

    reader.readAsDataURL(file);
    reader.onload = (_event) => {
      if (!reader.result) return;
      this.displaySource = reader.result as string;
      this.product.fileSource = {
        base64: this.displaySource.replace(replaceString, ' '),
        orginalName: fileName,
      };
    };
  }

  /**
   * change the type of the
   * product to bird (1)
   * or bird accessory (2)
   */
  changeProductType(type: 1 | 2) {
    this.product.productType = type;
  }

  /**
   * Navigate to the previous page
   */
  private navigateBack() {
    this.router.navigate(['/admin']);
  }
}
