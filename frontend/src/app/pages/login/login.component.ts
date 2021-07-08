import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../../core/services/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    loginForm: FormGroup;
    error = '';
    errorAcc = '';
    errorPass = '';
    loading = false;

    constructor(
        private authenticationService: AuthenticationService,
        private formBuilder: FormBuilder,
        private router: Router
    ) {
    }

    ngOnInit(): void {
        this.loginForm = this.formBuilder.group({
            accountName: ['', Validators.required],
            password: ['', Validators.required]
        });
    }

    onSubmit(): void {
        this.errorAcc = '';
        this.errorPass = '';
        this.error = '';

        if (this.loginForm.value.accountName === '') {
            this.errorAcc = 'Tài khoản không được để trống';
        } else if (this.loginForm.value.password === '') {
            this.errorPass = 'Mật khẩu không được để trống';
        } else {

            this.loading = true;
            this.authenticationService.login(this.loginForm.value.accountName, this.loginForm.value.password)
                .subscribe({
                    next: () => {
                        this.router.navigate(['/trang-chu']);
                    },
                    error: (err: any) => {
                        setTimeout(() => {
                            this.loading = false;
                            this.error = err.message;
                        }, 1000);
                    }
                });
        }
    }
}
