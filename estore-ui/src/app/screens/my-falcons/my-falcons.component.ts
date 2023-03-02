import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product/product.service';
import { UserService } from 'src/app/services/user/user.service';
import { Product } from 'src/app/types/Product';
import { User } from 'src/app/types/User';

@Component({
  selector: 'app-my-falcons',
  templateUrl: './my-falcons.component.html',
  styleUrls: ['./my-falcons.component.css'],
})
export class MyFalconsComponent implements OnInit {
  constructor(
    private userService: UserService,
    private productService: ProductService
  ) {}
  // falcons: Product[] = [];
  myFalcons: Product[] = [];
  user!: User;

  ngOnInit(): void {
    this.userService.userCurrentlySignedIn.subscribe((signedIn) => {
      if (!signedIn) return;
      this.getCurrentUser();
      if (this.user) {
        this.getAllFalcons();
      }
    });

    this.getCurrentUser();
    this.getAllFalcons();
  }

  getCurrentUser(): void {
    if (this.userService.isSignedIn()) {
      this.user = this.userService.getCurrentUser();
    }
  }

  getAllFalcons(): void {
    this.productService.getAllProducts().subscribe((_products) => {
      for (let product of _products) {
        let existingArray = product.sponsors.filter(
          (spon) => spon == this.user.username
        );

        if (existingArray.length > 0) {
          this.myFalcons.push(product);
        }
      }
    });
  }

  // getMyFalcons(): void{

  //   if(this.user){
  //     let sponsorID:String = this.user.username;
  //     for(let i = 0; i < this.falcons.length; i++){
  //       let falcon = this.falcons[i];
  //       for(let j = 0; j < falcon.sponsors.length; j++){
  //         let sponsor = falcon.sponsors[j];
  //         console.log(falcon.name + " : " + sponsor + " === " + sponsorID + " --> " + (sponsor===sponsorID));
  //         if(sponsor === sponsorID){
  //           if(this.myFalcons.indexOf(falcon) == -1){
  //             this.myFalcons.push(falcon);
  //             console.log("pushing falcon");
  //           }
  //         }
  //       }
  //     }
  //   }
  //   else{
  //     console.log("No user");
  //   }
  // }
}
