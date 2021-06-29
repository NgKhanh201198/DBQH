import {Component, OnInit} from '@angular/core';
import {AccountService} from '../../../core/services/account.service';

@Component({
    selector: 'app-list-account',
    templateUrl: './list-account.component.html',
    styleUrls: ['./list-account.component.css']
})
export class ListAccountComponent implements OnInit {
    listAccount: Array<any> = [];
    page = 1;

    constructor(
        private accountService: AccountService
    ) {
    }

    ngOnInit(): void {
        this.accountService.getAccountAll().subscribe((result) => {
            this.listAccount = result;
        });
    }

}
