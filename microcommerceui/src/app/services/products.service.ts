import { Injectable } from '@angular/core';
import { Product } from '../models/product';
import { Subject } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  products: Product[];

  constructor(private httpClient: HttpClient) { }

  async getProducts() : Promise<Product[]>{
    try {
      const products = await this.httpClient
        .get<Product[]>('http://localhost:9090/products')
        .toPromise();
      return products;
    }
    catch (error) {
      console.log(error);
      console.log('Erreur lors de la récupération de la liste de produits!');
      return Promise.reject(error.error || 'Server error');
    }
  }

  async getProductById(id: number): Promise<Product> {
    try {
      const product = await this.httpClient
        .get<Product>('http://localhost:9090/products/' + id)
        .toPromise();
      return product;
    }
    catch (error) {
      console.log(error);
      console.log('Erreur lors de la récupération du produit numéro ' + id);
      return Promise.reject(error.error || 'Server error');
    }
  }

  async addProduct(product: Product) : Promise<Object> {
    try {
      await this.httpClient.post('http://localhost:9090/products', product)
        .toPromise();
    }
    catch (error) {
      console.log(error);
      console.log('Erreur lors de l\'ajout du produit. ');
      return Promise.reject(error || 'Server error');
    }
  }

  async editProduct(editedProduct: Product) : Promise<Object> {
    try {
      await this.httpClient.put('http://localhost:9090/products/'+editedProduct.id, editedProduct).toPromise();
    }
    catch (error) {
      console.log(error);
      console.log('Erreur lors de l\'édition du produit numéro ' + editedProduct.id);
      return Promise.reject(error || 'Server error');
    }
  }

  async deleteProduct(product: Product) : Promise<Object> {
    try {
      await this.httpClient.delete('http://localhost:9090/products/'+product.id).toPromise();
    }
    catch(error) {
      console.log(error);
      console.log('Erreur lors de la suppression du produit numéro ' + product.id);
      return Promise.reject(error || 'Server error');
    }
  }
}
