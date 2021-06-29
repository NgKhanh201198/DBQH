import {Injectable} from '@angular/core';
import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor
} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {AuthenticationService} from '../services/authentication.service';
import {catchError} from 'rxjs/operators';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

    constructor(private authenticationService: AuthenticationService) {
    }

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        return next.handle(request).pipe(
            catchError(err => {
                    // console.log('Error: ');
                    // console.log(err);
                    if (err.status === 401) {
                        this.authenticationService.logout();
                    }
                    const error = err.error;
                    return throwError(error);
                }
            )
        );
    }
}
