import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../core/services/authentication.service';
import {Account} from '../../core/models/Account';
import {Router} from '@angular/router';
import {AccountService} from '../../core/services/account.service';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
    currentAccount: Account;
    accountName: string = '';

    constructor(
        private auth: AuthenticationService,
        private accountService: AccountService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
        this.currentAccount = this.auth.currentAccountValue;
        this.accountService.getAccountByID(this.currentAccount.id).subscribe((result) => {
            console.log(result);
            this.accountName = result.accountName;
        });
    }

    logout(): void {
        this.auth.logout();
        this.router.navigate(['dang-nhap']);
    }

    get isAdmin(): boolean {
        for (let i = 0; i < this.currentAccount.role.length; i++) {
            if (this.currentAccount && this.currentAccount.role[i] === 'ADMIN') {
                return true;
            }
        }
        return false;
    }
}
