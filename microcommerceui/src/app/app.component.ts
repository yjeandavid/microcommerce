import { Component } from '@angular/core';
import * as firebase from 'firebase';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor() {
    const config = {
      apiKey: "AIzaSyAfjVqIcE5SLUGUfYachLj_f6vhdzNJM9U",
      authDomain: "microcommerce.firebaseapp.com",
      databaseURL: "https://microcommerce.firebaseio.com",
      projectId: "microcommerce",
      storageBucket: "microcommerce.appspot.com",
      messagingSenderId: "80274628191"
    };
    firebase.initializeApp(config);
  }
}
