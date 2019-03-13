import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SignupComponent } from './auth/signup/signup.component';
import { SigninComponent } from './auth/signin/signin.component';
import { ProductListComponent } from './product-list/product-list.component';
import { AddProductFormComponent } from './product-list/add-product-form/add-product-form.component';
import { EditProductFormComponent } from './product-list/edit-product-form/edit-product-form.component';
import { AuthGuardService } from './services/auth-guard.service';
import { SignoutComponent } from './auth/signout/signout.component';

const routes: Routes = [
  { path: 'auth/signup', component: SignupComponent },
  { path: 'auth/signin', component: SigninComponent },
  { path: 'auth/signout', component: SignoutComponent },
  { path: 'products/new', canActivate: [AuthGuardService], component: AddProductFormComponent },
  { path: 'products/edit/:id', canActivate: [AuthGuardService], component: EditProductFormComponent },
  { path: 'products', component: ProductListComponent },
  { path: '', redirectTo: 'products', pathMatch: 'full' },
  { path: '**', redirectTo: 'products' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
