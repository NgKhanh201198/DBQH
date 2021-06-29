import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

const URL = `${environment.baseUrlServer}` + 'api/agency/';

@Injectable({
    providedIn: 'root'
})
export class AgencyService {

    constructor(
        private http: HttpClient
    ) {
    }

    handleError(error: HttpErrorResponse): Observable<never> {
        return throwError(error);
    }

    public getAgencyAll(): Observable<any> {
        return this.http.get<any>(URL).pipe(
            catchError(this.handleError)
        );
    }
}
