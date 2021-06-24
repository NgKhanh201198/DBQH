import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthenticationService} from '../services/authentication.service';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {
    constructor(private router: Router, private authenticationService: AuthenticationService) {
    }

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

        const currentAccount = this.authenticationService.currentAccountValue;

        if (currentAccount) {
            const role: any = route.data.roles;

            if (role && role.indexOf(currentAccount.role[0]) === -1) {
                this.router.navigate(['/dang-nhap']);
            }
            return true;

        } else {
            this.router.navigate(['dang-nhap']);
            return false;
        }
    }

}
