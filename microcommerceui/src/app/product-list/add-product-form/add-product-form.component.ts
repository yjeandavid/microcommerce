import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ValidatorFn, AbstractControl } from '@angular/forms';
import { ProductsService } from 'src/app/services/products.service';
import { Router } from '@angular/router';
import { Product } from 'src/app/models/product';

@Component({
  selector: 'app-add-product-form',
  templateUrl: './add-product-form.component.html',
  styleUrls: ['./add-product-form.component.scss']
})
export class AddProductFormComponent implements OnInit {

  title: string;
  addProductForm: FormGroup;
  errorMessage: string;

  constructor(private formBuilder: FormBuilder, protected productsService: ProductsService, protected router: Router) { }

  ngOnInit() {
    this.initForm('Ajouter un nouveau produit', '', 0, 0);
  }

  initForm(title: string, nameValue: string, priceValue: number, purchasePriceValue: number) {
    this.title = title;
    this.addProductForm = this.formBuilder.group({
      name: [nameValue, [Validators.required, Validators.pattern("^[a-zA-Zéèàïùëêîôüö \-']{2,}$"), Validators.maxLength(25)]],
      price: [priceValue, [Validators.required, Validators.min(1)]],
      purchasePrice: [purchasePriceValue, [Validators.required, Validators.min(1)]],
    }, {validators: [this.priceGreaterThanPurchasePrice], updateOn: 'change'});
  }

  onSaveProduct() {
    const name = this.addProductForm.get('name').value;
    const price = this.addProductForm.get('price').value;
    const purchasePrice = this.addProductForm.get('purchasePrice').value;
    const newProduct = new Product(name, price, purchasePrice);
    this.productsService.addProduct(newProduct).then(
      () => {
        this.onBack();
      },
      (error) => {
        this.errorMessage = error;
      }
    );
  }

  onBack() {
    this.router.navigate(['/products']);
  }
  
  priceGreaterThanPurchasePrice(formGroup: FormGroup) {
    const price = formGroup.get('price').value;
    const purchasePrice = formGroup.get('purchasePrice').value;
    return price >= purchasePrice ? null : {'priceGreaterThanPurchasePrice': true};
  }

  get name() {
    return this.addProductForm.get('name');
  }
}
