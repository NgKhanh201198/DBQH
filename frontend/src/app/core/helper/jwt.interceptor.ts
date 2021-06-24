import {Injectable} from '@angular/core';
import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthenticationService} from '../services/authentication.service';
import {environment} from '../../../environments/environment';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

    constructor(private authenticationService: AuthenticationService) {
    }

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

        const currentAccount = this.authenticationService.currentAccountValue;
        const isLoggedIn = currentAccount && currentAccount.token;
        const isApiUrl = request.url.startsWith(`${environment.baseUrlServer}`);

        if (isLoggedIn && isApiUrl) {
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${currentAccount.token}`
                }
            });
        }

        return next.handle(request);
    }
}
