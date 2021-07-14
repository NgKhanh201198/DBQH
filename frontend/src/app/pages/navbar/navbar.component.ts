import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../core/services/authentication.service';
import {Account} from '../../core/models/Account';
import {Router} from '@angular/router';
import {AccountService} from '../../core/services/account.service';
import {LoggerService} from '../../core/services/logger.service';
import {WarningContent} from '../../core/models/WarningContent';
import {RecommendationsService} from '../../core/services/recommendations.service';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
    currentAccount: Account;
    accountName: string = '';
    index: any = 0;
    listWarning: Array<WarningContent> = [];

    constructor(
        private auth: AuthenticationService,
        private accountService: AccountService,
        private recommendationsService: RecommendationsService,
        private router: Router,
        private logger: LoggerService
    ) {
    }

    ngOnInit(): void {
        this.currentAccount = this.auth.currentAccountValue;
        this.accountService.getAccountByID(this.currentAccount.id).subscribe((result) => {
            this.accountName = result.accountName;
        });

        this.recommendationsService.getRecommendationsWarning(this.currentAccount.agency).subscribe((result) => {
            this.listWarning = result;
            this.logger.log(result);
        });

        setInterval(() => {
            if (this.index < this.listWarning.length - 1) {
                this.index++;
            } else {
                this.index = 0;
            }
        }, 10000);
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
