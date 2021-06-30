import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {AccountService} from '../../../core/services/account.service';
import {AgencyService} from '../../../core/services/agency.service';
import {Options} from '../../../core/models/Options';
import {ERole} from '../../../core/models/erole.enum';
import {ActivatedRoute} from '@angular/router';
import {formatDate, Location} from '@angular/common';

@Component({
    selector: 'app-update-account',
    templateUrl: './update-account.component.html',
    styleUrls: ['./update-account.component.css']
})
export class UpdateAccountComponent implements OnInit {
    @ViewChild('myForm') myForm: NgForm;
    @ViewChild('uploadFile') uploadFile: ElementRef;
    submitted = false;
    currentFile: File = null;
    reader = new FileReader();
    imgURL: any = null;
    avatarURL: any = null;
    errorFiles = '';
    success = '';
    error = '';
    id: any;

    listAgency: Array<Options> = [];
    listRole: Options[] = [
        {name: 'Quản trị viên', value: ERole.ADMIN},
        {name: 'Người dùng', value: ERole.USER}
    ];
    listStatus: Options[] = [
        {name: 'Hoạt động', value: 'ACTIVE'},
        {name: 'Khóa', value: 'LOCKED'}
    ];

    formUpdate: FormGroup = this.formBuilder.group({
        fullname: ['', [Validators.required, Validators.pattern(/^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\s]{0,50}$/)]],
        accountName: ['', [Validators.required, Validators.pattern(/^[0-9a-zA-Z_]{3,50}$/)]],
        // password: ['', [Validators.required, Validators.pattern(/^[0-9a-zA-Z_]{6,50}$/)]],
        dateOfBirth: ['', [Validators.required, Validators.pattern(/^\d{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])$/)]],
        position: ['', [Validators.required]],
        candidateplace: ['', [Validators.required]],
        status: ['', [Validators.required]],
        agency: ['', [Validators.required]],
        role: ['', [Validators.required]]
    });

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private location: Location,
        private accountService: AccountService,
        private agencyService: AgencyService
    ) {
        this.agencyService.getAgencyAll().subscribe((result: any) => {
            result.forEach((element) => {
                const index = new Options(element.agencyName, element.agencyName);
                this.listAgency.push(index);
            });
        });
    }

    ngOnInit(): void {
        this.id = this.route.snapshot.paramMap.get('id');

        this.accountService.getAccountByID(this.id).subscribe((result) => {

            this.avatarURL = result.avatar;

            if (result.dateOfBirth != null) {
                result.dateOfBirth = formatDate(result.dateOfBirth, 'yyyy-MM-dd', 'en');
            }

            this.formUpdate = this.formBuilder.group({
                fullname: [result.fullname, [Validators.required, Validators.pattern(/^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\s]{0,50}$/)]],
                accountName: [result.accountName, [Validators.required, Validators.pattern(/^[0-9a-zA-Z_]{3,50}$/)]],
                // password: [, [Validators.required, Validators.pattern(/^[0-9a-zA-Z_]{6,50}$/)]],
                dateOfBirth: [result.dateOfBirth, [Validators.required, Validators.pattern(/^\d{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])$/)]],
                position: [result.position, [Validators.required]],
                candidateplace: [result.candidateplace, [Validators.required]],
                status: [result.status, [Validators.required]],
                agency: [result.agency.agencyName, [Validators.required]],
                role: [result.role.keyName, [Validators.required]]
            });
        });
    }

    get formValid(): any {
        return this.formUpdate.controls;
    };

    comeBack() {
        this.location.back();
    }

    onSelectFile(event): void {
        if (event.target.files.length > 0) {
            this.errorFiles = '';
            this.currentFile = event.target.files[0];

            this.reader.readAsDataURL(this.currentFile);
            this.reader.onload = (_event) => {
                this.imgURL = this.reader.result;
            };
            console.log('dang ok');

            this.accountService.updateAvatarAccountByID(this.id, this.currentFile).subscribe({
                next: (res) => {
                    this.error = '';
                    this.success = res.message;
                },
                error: (err) => {
                    this.success = '';
                    this.errorFiles = err.message;
                }
            });
        }
    }

    onSubmit() {
        this.submitted = true;

        console.log(this.formUpdate.value);

        this.accountService.updateAccountByID(this.id, this.formUpdate.value).subscribe({
            next: (response) => {
                this.error = '';
                this.submitted = false;
                this.success = response.message;
            },
            error: (err) => {
                if (err) {
                    this.error = err.message;
                }
            }
        }),
            setTimeout(() => {
                this.success = '';
            }, 2500);
    }

}
