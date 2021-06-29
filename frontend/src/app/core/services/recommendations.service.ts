import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';

const URL = `${environment.baseUrlServer}` + 'api/recommendations';

@Injectable({
    providedIn: 'root'
})
export class RecommendationsService {

    constructor() {
    }
}
