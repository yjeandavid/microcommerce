import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignupComponent } from './auth/signup/signup.component';
import { SigninComponent } from './auth/signin/signin.component';
import { ProductListComponent } from './product-list/product-list.component';
import { AddProductFormComponent } from './product-list/add-product-form/add-product-form.component';
import { EditProductFormComponent } from './product-list/edit-product-form/edit-product-form.component';
import { HeaderComponent } from './header/header.component';
import { AuthService } from './services/auth.service';
import { AuthGuardService } from './services/auth-guard.service';
import { ProductsService } from './services/products.service';
import { ProductComponent } from './product/product.component';
import { SignoutComponent } from './auth/signout/signout.component';

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    SigninComponent,
    ProductListComponent,
    AddProductFormComponent,
    EditProductFormComponent,
    HeaderComponent,
    ProductComponent,
    SignoutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    AuthService,
    AuthGuardService,
    ProductsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
