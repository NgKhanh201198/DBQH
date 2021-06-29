import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../core/services/authentication.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-nav-admin',
    templateUrl: './nav-admin.component.html',
    styleUrls: ['./nav-admin.component.css']
})
export class NavAdminComponent implements OnInit {

    constructor(
        private auth: AuthenticationService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
    }

    logout(): void {
        this.auth.logout();
        this.router.navigate(['dang-nhap']);
    }
}
