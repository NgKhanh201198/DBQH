import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../core/services/authentication.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';
import {Router} from '@angular/router';

@Component({
    selector: 'app-dangnhap',
    templateUrl: './dangnhap.component.html',
    styleUrls: ['./dangnhap.component.css']
})
export class DangnhapComponent implements OnInit {
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
                        this.router.navigate(['/tiep-nhan-phan-anh']);
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
