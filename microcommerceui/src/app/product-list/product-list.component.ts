import { Component, OnInit, OnDestroy } from '@angular/core';
import { Product } from '../models/product';
import { Subscription } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { ProductsService } from '../services/products.service';
import { Router } from '@angular/router';
import { RxStompService } from '@stomp/ng2-stompjs';
import { Message } from '@stomp/stompjs';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit, OnDestroy {

  isAuth: boolean;
  isAuthSubscription: Subscription;

  products: Product[];
  alertsSubscription: Subscription;

  currentPage = 1;
  totalProducts: number;
  itemsPerPage = 2;
  stringItemsPerPage = "2";

  possiblesItemsPerPage = ["2", "5", "10", "25", "50", "100", "All"];

  constructor(private authService: AuthService, private productsService: ProductsService, private router: Router, 
              private rxStompService: RxStompService) {
    this.fetchProducts();
  }

  fetchProducts() {
    this.productsService.getProducts().then(prods => {
      this.products = prods;
      this.totalProducts = this.products.length;
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
    this.alertsSubscription = this.rxStompService.watch('/exchange/alerts').subscribe((message: Message) => {
        console.log('Message reçu %s', message.body);
    });
  }

  ngOnDestroy() {
    this.isAuthSubscription.unsubscribe();
    this.alertsSubscription.unsubscribe();
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

  onChangeItemsPerPage(itemsPerPageDesired: string) {
      this.stringItemsPerPage = itemsPerPageDesired;
      if (itemsPerPageDesired === "All") {
          this.itemsPerPage = this.products.length;
      } else {
          this.itemsPerPage = +itemsPerPageDesired;
      }
  }
}
