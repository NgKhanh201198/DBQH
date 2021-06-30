import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../core/services/authentication.service';
import {Router} from '@angular/router';
import {Account} from '../../core/models/Account';

@Component({
    selector: 'app-nav-admin',
    templateUrl: './nav-admin.component.html',
    styleUrls: ['./nav-admin.component.css']
})
export class NavAdminComponent implements OnInit {
    currentAccount: Account;
    accountName: string = '';

    constructor(
        private auth: AuthenticationService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
        this.currentAccount = this.auth.currentAccountValue;
        this.accountName = this.currentAccount.accountName;
        console.log(this.accountName);
    }

    logout(): void {
        this.auth.logout();
        this.router.navigate(['dang-nhap']);
    }
}
