import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

const URL = `${environment.baseUrlServer}` + 'api/account';

@Injectable({
    providedIn: 'root'
})
export class AccountService {

    constructor(
        private http: HttpClient
    ) {
    }

    handleError(error: HttpErrorResponse): Observable<never> {
        return throwError(error);
    }

    createAccount(data: any): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('avatar', data.avatar);
        formData.append('accountName', data.accountName);
        formData.append('password', data.password);
        formData.append('fullname', data.fullname);
        formData.append('dateOfBirth', data.dateOfBirth);
        formData.append('position', data.position);
        formData.append('candidateplace', data.candidateplace);
        formData.append('status', data.status);
        formData.append('agency', data.agency);
        formData.append('role', data.role);
        return this.http.post(`${URL}`, formData).pipe(catchError(this.handleError));
    }

    getAccountAll(): Observable<any> {
        return this.http.get(`${URL}`).pipe(catchError(this.handleError));
    }

    getAccountByID(id: any): Observable<any> {
        return this.http.get(`${URL}/${id}`).pipe(catchError(this.handleError));
    }


}
