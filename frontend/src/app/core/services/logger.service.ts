import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class LoggerService {

    constructor() {
    }

    isLog: boolean = environment.isLog;

    log(msg: any): boolean {
        if (this.isLog == true) {
            console.log(msg);
        }
        return false;
    }

    loggerMessages(msg: any): boolean {
        if (this.isLog == true) {
            console.log('Message: ' + JSON.stringify(msg));
        }
        return false;
    }

    loggerError(msg: any): boolean {
        if (this.isLog == true) {
            console.error('Error: ' + JSON.stringify(msg));
        }
        return false;
    }

    loggerWarn(msg: any): boolean {
        if (this.isLog == true) {
            console.warn('Warning: ' + JSON.stringify(msg));
        }
        return false;
    }
}
