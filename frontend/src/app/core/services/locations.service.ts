import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};
const URL = `${environment.baseUrlServer}` + 'api/locations/';

@Injectable({
    providedIn: 'root'
})
export class LocationsService {

    constructor(private http: HttpClient) {
    }

    handleError(error: HttpErrorResponse): Observable<never> {
        return throwError(error);
    }

    public getProvinceAll(): Observable<any> {
        return this.http.get<any>(URL + 'province').pipe(
            catchError(this.handleError)
        );
    }

    public getDistrictByProvince(province: any): Observable<any> {
        const params = new HttpParams().append('province', province);
        return this.http.get<any>(URL + 'district', {params}).pipe(
            catchError(this.handleError)
        );
    }

    public getWardByDistrict(district: any): Observable<any> {
        const params = new HttpParams().append('district', district);
        return this.http.get<any>(URL + 'ward', {params}).pipe(
            catchError(this.handleError)
        );
    }
}
