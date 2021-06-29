import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {AgencyService} from '../../../core/services/agency.service';
import {ERole} from '../../../core/models/erole.enum';
import {AccountService} from '../../../core/services/account.service';
import {Options} from '../../../core/models/Options';

@Component({
    selector: 'app-create-accout',
    templateUrl: './create-accout.component.html',
    styleUrls: ['./create-accout.component.css']
})
export class CreateAccoutComponent implements OnInit {
    formData: FormGroup;
    @ViewChild('myForm') myForm: NgForm;
    @ViewChild('uploadFile') uploadFile: ElementRef;
    submitted = false;
    currentFile: File = null;
    reader = new FileReader();
    imgURL: any = null;
    errorFiles = '';
    success = '';
    error = '';

    listAgency: Array<Options> = [];
    listRole: Options[] = [
        {name: 'Quản trị viên', value: ERole.ADMIN},
        {name: 'Người dùng', value: ERole.USER}
    ];
    listStatus: Options[] = [
        {name: 'Hoạt động', value: 'ACTIVE'},
        {name: 'Khóa', value: 'LOCKED'}
    ];

    constructor(
        private formBuilder: FormBuilder,
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

    onSelectFile(event): void {
        if (event.target.files.length > 0) {
            this.errorFiles = '';
            this.currentFile = event.target.files[0];

            this.reader.readAsDataURL(this.currentFile);
            this.reader.onload = (_event) => {
                this.imgURL = this.reader.result;
            };
        }
    }

    ngOnInit(): void {
        this.formData = this.formBuilder.group({
            avatar: ['', []],
            fullname: ['', [Validators.required, Validators.pattern(/^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\s]{0,50}$/)]],
            accountName: ['', [Validators.required, Validators.pattern(/^[0-9a-zA-Z_]{3,50}$/)]],
            password: ['', [Validators.required, Validators.pattern(/^[0-9a-zA-Z_]{6,50}$/)]],
            dateofbirth: ['', [Validators.required, Validators.pattern(/^\d{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])$/)]],
            position: ['', [Validators.required]],
            candidateplace: ['', [Validators.required]],
            status: ['', [Validators.required]],
            agency: ['', [Validators.required]],
            role: ['', [Validators.required]]
        });
    }

    get formValid(): any {
        return this.formData.controls;
    };

    onSubmit() {
        this.submitted = true;

        if ((!this.currentFile)) {
            this.errorFiles = 'Vui lòng chọn file';
        }
        console.log(this.formData.value);
        this.formData.get('avatar').setValue(this.currentFile);
        this.accountService.createAccount(this.formData.value).subscribe({
            next: (response) => {
                this.error = '';
                this.imgURL = null;
                this.submitted = false;
                this.uploadFile.nativeElement.value = '';
                this.myForm.resetForm();
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
