import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {AgencyService} from '../../../core/services/agency.service';
import {Options} from '../../../core/models/Options';
import {Location} from '@angular/common';

@Component({
    selector: 'app-create-agency',
    templateUrl: './create-agency.component.html',
    styleUrls: ['./create-agency.component.css']
})
export class CreateAgencyComponent implements OnInit {
    @ViewChild('myForm') myForm: NgForm;
    formData: FormGroup;
    submitted = false;
    success = '';
    error = '';

    listStatus: Options[] = [
        {name: 'Hoạt động', value: 'Hoạt động'},
        {name: 'Không hoạt động', value: 'Không hoạt động'}
    ];

    constructor(
        private formBuilder: FormBuilder,
        private agencyService: AgencyService,
        private location: Location
    ) {
    }

    ngOnInit(): void {
        this.formData = this.formBuilder.group({
            agencyName: ['', [Validators.required, Validators.pattern(/^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\s]{0,50}$/)]],
            status: [null, [Validators.required]],
            note: [''],
        });
    }

    get formValid(): any {
        return this.formData.controls;
    };

    closeError() {
        this.error = '';
    }

    comeBack() {
        this.location.back();
    }

    onSubmit(): void {
        this.submitted = true;
        this.success = '';
        if (this.formData.status === 'VALID') {
            this.agencyService.createAgency(this.formData.value).subscribe({
                next: (response) => {
                    this.submitted = false;
                    this.myForm.resetForm();
                    this.success = response.message;
                },
                error: (err) => {
                    this.error = err.message;
                }
            });
        }

        setTimeout(() => {
            this.success = '';
        }, 3000);

        setTimeout(() => {
            this.error = '';
        }, 6000);
    }
}
