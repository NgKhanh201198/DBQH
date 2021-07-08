import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, retry} from 'rxjs/operators';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

const URL = `${environment.baseUrlServer}` + 'api/recommendations';

@Injectable({
    providedIn: 'root'
})
export class RecommendationsService {

    constructor(
        private http: HttpClient
    ) {
    }

    handleError(error: HttpErrorResponse): Observable<never> {
        return throwError(error);
    }

    create(data: any): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('files', data.files);
        formData.append('object', data.object);
        formData.append('fullname', data.fullname);
        formData.append('address', data.address);
        formData.append('phonenumber', data.phonenumber);
        formData.append('email', data.email);
        formData.append('title', data.title);
        formData.append('commenttype', data.commenttype);
        formData.append('fields', data.fields);
        formData.append('contents', data.contents);
        formData.append('status', data.status);
        formData.append('reportingdeadline', data.reportingdeadline);
        formData.append('agency', data.agency);
        return this.http.post(`${URL}`, formData).pipe(catchError(this.handleError));
    }

    getRecommendationsAll(): Observable<any> {
        return this.http.get(`${URL}`).pipe(catchError(this.handleError));
    }

    search(keyword: any): Observable<any> {
        const params = new HttpParams().append('keyword', keyword);
        return this.http.get(`${URL}/search`, {params}).pipe(catchError(this.handleError));
    }

    // search(keyword: any): Observable<any> {
    //     return this.http.get(`${URL}?keyword=${keyword}`).pipe(catchError(this.handleError));
    // }

    getRecommendationsByID(id: any): Observable<any> {
        return this.http.get(`${URL}/${id}`).pipe(catchError(this.handleError));
    }

    getFiles(url: any): Observable<any> {
        return this.http.get(url).pipe(catchError(this.handleError));
    }
}
