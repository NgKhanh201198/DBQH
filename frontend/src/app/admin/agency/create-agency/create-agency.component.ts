import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AgencyService} from '../../../core/services/agency.service';

@Component({
    selector: 'app-create-agency',
    templateUrl: './create-agency.component.html',
    styleUrls: ['./create-agency.component.css']
})
export class CreateAgencyComponent implements OnInit {
    formData: FormGroup;
    submitted = false;
    success = '';
    error = '';

    constructor(
        private formBuilder: FormBuilder,
        private agencyService: AgencyService
    ) {
    }

    ngOnInit(): void {
        this.formData = this.formBuilder.group({
            agencyName: ['', [Validators.required, Validators.pattern(/^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\s]{0,50}$/)]],
            status: [''],
            note: [''],
        });
    }

    get formValid(): any {
        return this.formData.controls;
    };

    closeError() {
        this.error = '';
    }

    onSubmit(): void {
        this.submitted = true;

        this.agencyService.createAgency(this.formData.value).subscribe({
            next: (response) => {
                this.success = response.message;
            },
            error: (err) => {
                this.error = err.message;
            }
        });

        setTimeout(() => {
            this.success = '';
        }, 3000);

        setTimeout(() => {
            this.error = '';
        }, 6000);
    }
}
