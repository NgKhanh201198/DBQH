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
import {LoggerService} from '../services/logger.service';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

    constructor(
        private authenticationService: AuthenticationService,
        private logger: LoggerService
    ) {
    }

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        return next.handle(request).pipe(
            catchError(err => {
                    // this.logger.log('Error: ');
                    // this.logger.log(err);
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
