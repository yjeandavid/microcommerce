import { Injectable } from '@angular/core';
import * as firebase from 'firebase';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAuth = false;
  isAuthSubject = new Subject<boolean>();

  constructor() {
    firebase.auth().onAuthStateChanged(
      (user) => {
        if(user) {
          this.isAuth = true;
          this.emitIsAuthSubject();
        } else {
          this.isAuth = false;
          this.emitIsAuthSubject();
        }
      }
    );
   }

  emitIsAuthSubject() {
    this.isAuthSubject.next(this.isAuth);
  }

  createNewUser(email: string, password: string) {
    return new Promise(
      (resolve, reject) => {
        firebase.auth().createUserWithEmailAndPassword(email, password).then(
          () => {
            this.isAuth = true;
            this.emitIsAuthSubject();
            resolve();
          },
          (error) => {
            reject(error);
          }
        );
      }
    );
  }

  signInUser(email: string, password: string) {
    return new Promise(
      (resolve, reject) => {
        firebase.auth().signInWithEmailAndPassword(email, password).then(
          () => {
            this.isAuth = true;
            this.emitIsAuthSubject();
            resolve();
          },
          (error) => {
            reject(error);
          }
        );
      }
    );
  }

  signOutUser() {
    firebase.auth().signOut();
    this.isAuth = false;
    this.emitIsAuthSubject();
  }
}
