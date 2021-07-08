import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AgencyService} from '../../../core/services/agency.service';
import {LoggerService} from '../../../core/services/logger.service';
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'app-update-agency',
    templateUrl: './update-agency.component.html',
    styleUrls: ['./update-agency.component.css']
})
export class UpdateAgencyComponent implements OnInit {
    submitted = false;
    success = '';
    error = '';
    id: any;

    constructor(
        private formBuilder: FormBuilder,
        private agencyService: AgencyService,
        private route:ActivatedRoute,
        private looger: LoggerService
    ) {
    }

    formUpdate = this.formBuilder.group({
        agencyName: ['', [Validators.required, Validators.pattern(/^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\s]{0,50}$/)]],
        status: [''],
        note: [''],
    });

    ngOnInit(): void {
        this.id = this.route.snapshot.paramMap.get('id');
        this.agencyService.getAgencyByID(this.id).subscribe((result) => {
            this.looger.log(result);
            this.formUpdate = this.formBuilder.group({
                agencyName: [result.agencyName, [Validators.required, Validators.pattern(/^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\s]{0,50}$/)]],
                status: [result.status],
                note: [result.note],
            });
        });
    }

    get formValid(): any {
        return this.formUpdate.controls;
    };

    closeError() {
        this.error = '';
    }

    onSubmit(): void {
        this.submitted = true;

        this.agencyService.updateAgency(this.id,this.formUpdate.value).subscribe({
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
