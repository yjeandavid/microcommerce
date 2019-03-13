import { Component, OnInit, OnDestroy } from '@angular/core';
import { Product } from '../models/product';
import { Subscription } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { ProductsService } from '../services/products.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit, OnDestroy {

  isAuth: boolean;
  isAuthSubscription: Subscription;

  products: Product[];

  constructor(private authService: AuthService, private productsService: ProductsService, private router: Router) {
    this.fetchProducts();
  }

  fetchProducts() {
    this.productsService.getProducts().then(prods => {
      this.products = prods;
    }, error => {
      console.log(error);
      console.log('Erreur récupération des produits');
    });
  }

  ngOnInit() {
    this.isAuthSubscription = this.authService.isAuthSubject.subscribe(
      (loggedIn: boolean) => {
        this.isAuth = loggedIn;
      }
    );
    this.authService.emitIsAuthSubject();
  }

  ngOnDestroy() {
    this.isAuthSubscription.unsubscribe();
  }

  onAddProduct() {
    this.router.navigate(['/products', 'new']);
  }

  onEditProduct(id: number) {
    this.router.navigate(['/products', 'edit', id]);
  }

  onDeleteProduct(product: Product) {
    this.productsService.deleteProduct(product).then(
      () => {
        this.fetchProducts();
      },
      (error) => {
        console.log(error);
      }
    );
  }

}
