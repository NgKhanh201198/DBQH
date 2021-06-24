import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Account} from '../models/Account';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

const API_LOGIN = `${environment.baseUrlServer}` + 'api/login';
const TOKEN_KEY = 'TOKEN';
const USER_KEY = 'CURRENT_USER';
const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {
    private currentAccountSubject: BehaviorSubject<Account>;
    public currentAccount: Observable<Account>;

    constructor(private http: HttpClient) {
        this.currentAccountSubject = new BehaviorSubject<Account>(JSON.parse(window.sessionStorage.getItem(USER_KEY)));
        this.currentAccount = this.currentAccountSubject.asObservable();
    }

    // tslint:disable-next-line:typedef
    handleError(error: HttpErrorResponse) {
        return throwError(error);
    }

    public storeToken(token: string): any {
        window.sessionStorage.removeItem(TOKEN_KEY);
        window.sessionStorage.setItem(TOKEN_KEY, token);
    }

    public getToken(): string {
        return window.sessionStorage.getItem(TOKEN_KEY);
    }

    public storeAccount(account: string): any {
        window.sessionStorage.removeItem(USER_KEY);
        window.sessionStorage.setItem(USER_KEY, JSON.stringify(account));
    }

    public getAccount(): any {
        return JSON.parse(window.sessionStorage.getItem(USER_KEY));
    }

    public get currentAccountValue(): Account {
        return this.currentAccountSubject.value;
    }

    login(accountName: any, password: any): Observable<any> {
        return this.http.post(API_LOGIN, {accountName, password}, httpOptions)
            .pipe(
                map((account: any) => {
                    this.storeToken(account.token);
                    this.storeAccount(account);
                    this.currentAccountSubject.next(account);
                    return account;
                }),
                catchError((error) => {
                    return throwError(error);
                })
            );
    }

    logout(): any {
        window.sessionStorage.clear();
        this.currentAccountSubject.next(null);
    }

    isLoggedIn(): boolean {
        return !!this.getToken();
    }
}
