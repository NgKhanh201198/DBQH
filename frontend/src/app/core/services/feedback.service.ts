import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

const URL = `${environment.baseUrlServer}` + 'api/feedback';
const URL1 = `${environment.baseUrlServer}` + 'api/forwarded';

@Injectable({
    providedIn: 'root'
})
export class FeedbackService {

    constructor(
        private http: HttpClient
    ) {
    }

    handleError(error: HttpErrorResponse): Observable<never> {
        return throwError(error);
    }

    createFeedback(data: any): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('files', data.files);
        formData.append('title', data.title);
        formData.append('contents', data.contents);
        formData.append('status', data.status);
        formData.append('accountName', data.accountName);
        formData.append('recommendationsid', data.recommendationsid);
        return this.http.post(`${URL}`, formData).pipe(catchError(this.handleError));
    }

    createForwarded(data: any): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('agency', data.agency);
        formData.append('title', data.title);
        formData.append('contents', data.contents);
        formData.append('status', data.status);
        formData.append('accountName', data.accountName);
        formData.append('recommendationsid', data.recommendationsid);
        return this.http.post(`${URL1}`, formData).pipe(catchError(this.handleError));
    }

    getByRecommendations(id: any): Observable<any> {
        return this.http.get(`${URL}/recommendations?id=${id}`).pipe(
            catchError(this.handleError)
        );
    }
}
