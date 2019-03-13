import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {

  isAuth: boolean;
  isAuthSubscription: Subscription;

  constructor(private authService: AuthService) { }

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

  onSignOut() {
    this.authService.signOutUser();
  }
}
