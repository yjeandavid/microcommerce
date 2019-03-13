import { Component, OnInit } from '@angular/core';
import { AddProductFormComponent } from '../add-product-form/add-product-form.component';
import { FormBuilder } from '@angular/forms';
import { ProductsService } from 'src/app/services/products.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Product } from 'src/app/models/product';

@Component({
  selector: 'app-edit-product-form',
  templateUrl: '../add-product-form/add-product-form.component.html',
  styleUrls: ['./edit-product-form.component.scss']
})
export class EditProductFormComponent extends AddProductFormComponent {

  constructor(formBuilder: FormBuilder, productsService: ProductsService, router: Router, private route: ActivatedRoute) { 
    super(formBuilder, productsService, router);
  }

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    const productToEdit = this.productsService.getProductById(+id).then(
      productToEdit => {
        this.initForm('Éditer un produit', productToEdit.name, productToEdit.price, productToEdit.purchasePrice);
      },
      error => {
        console.log(error);
        console.log('Erreur pour récupere le produit avec le numéro ' + id);
        this.router.navigate(['/products']);
      }
    );
  }

  onSaveProduct() {
    const name = this.addProductForm.get('name').value;
    const price = this.addProductForm.get('price').value;
    const purchasePrice = this.addProductForm.get('purchasePrice').value;
    const id = this.route.snapshot.params['id'];
    const newProduct = new Product(name, price, purchasePrice);
    newProduct.id = +id;
    this.productsService.editProduct(newProduct).then(
      () => {
        this.onBack();
      },
      (error) => {
        this.errorMessage = error;
      }
    );
  }

}
