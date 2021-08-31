import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {Options} from '../../core/models/Options';
import {LocationsService} from '../../core/services/locations.service';
import {AgencyService} from '../../core/services/agency.service';
import {RecommendationsService} from '../../core/services/recommendations.service';
import {emailOrNullValidator, phoneNumberValidator} from '../../../assets/custom/validation/CustomValidator';
import {LoggerService} from '../../core/services/logger.service';
import {Location} from '@angular/common';
import {AuthenticationService} from '../../core/services/authentication.service';
import {$} from 'protractor';

@Component({
    selector: 'app-recommendations',
    templateUrl: './recommendations.component.html',
    styleUrls: ['./recommendations.component.css']
})
export class RecommendationsComponent implements OnInit {
    @ViewChild('myForm') myForm: NgForm;
    @ViewChild('uploadFile') uploadFile: ElementRef;
    formData: FormGroup;
    submitted = false;
    errorObject = false;
    errorTinh = false;
    errorHuyen = false;
    errorXa = false;
    errorAgency = false;
    errorStatus = false;
    errorStatusOther = false;
    errorCommenttype = false;
    errorCommenttypeOther = false;
    errorFields = false;
    errorFieldsOther = false;
    showCommentType = false;
    showFieldes = false;
    showStatus = false;
    listProvince: Array<Options> = [];
    listDistrict: Array<Options> = [];
    listWard: Array<Options> = [];
    listAgency: Array<Options> = [];
    // ngay = false;
    tinh: any = 'Tỉnh/Thành phố';
    huyen: any = 'Quận/Huyện';
    xa: any = 'Phường/Xã';
    thon: any = '';
    currentFile: File = null;
    imgURL: any = null;
    errorFiles = '';
    fields = '';
    commenttype = '';
    status = '';
    success = '';
    error = '';

    listObject: Options[] = [
        {name: 'Người dân', value: 'Người dân'},
        {name: 'Doanh nghiệp', value: 'Doanh nghiệp'},
        {name: 'Tổ chức', value: 'Tổ chức'}
    ];
    listCommentType: Options[] = [
        {name: 'Phản ánh', value: 'Phản ánh'},
        {name: 'Kiến nghị', value: 'Kiến nghị'},
        {name: 'Khiếu nại', value: 'Khiếu nại'},
        {name: 'Tố cáo', value: 'Tố cáo'}
    ];
    listFields: Options[] = [
        {name: 'Văn hóa', value: 'Văn hóa'},
        {name: 'Giáo dục', value: 'Giáo dục'},
        {name: 'Thể thao', value: 'Thể thao'},
        {name: 'Truyền thông', value: 'Truyền thông'},
        {name: 'Tôn giáo', value: 'Tôn giáo'}
    ];
    listStatus: Options[] = [
        {name: 'Gửi kiến nghị', value: 'Gửi kiến nghị'},
        {name: 'Đã phản hồi', value: 'Đã phản hồi'},
        {name: 'Hoàn thành', value: 'Hoàn thành'},
        {name: 'Khác', value: 'Khác'}
    ];

    constructor(
        private auth: AuthenticationService,
        private formBuilder: FormBuilder,
        private locationsService: LocationsService,
        private agencyService: AgencyService,
        private recommendationsService: RecommendationsService,
        private logger: LoggerService,
        private location: Location
    ) {
    }

    ngOnInit(): void {
        this.listProvince = this.getProvinceAll();
        this.listAgency = this.getAgencyAll();
        this.logger.log(this.listProvince);
        this.logger.log(this.listDistrict);
        this.logger.log(this.listAgency);

        this.formData = this.formBuilder.group({
            object: ['', [Validators.required]],
            fullname: ['', [Validators.required]],
            address: ['', [Validators.required]],
            phonenumber: ['', [Validators.required, phoneNumberValidator()]],
            email: ['', [emailOrNullValidator()]],
            title: ['', [Validators.required]],
            commenttype: [null, [Validators.required]],
            fields: [null, [Validators.required]],
            contents: ['', [Validators.required]],
            status: ['', [Validators.required]],
            reportingdeadline: ['', [Validators.required]],
            agency: [null],
            files: ['']
        });
    }

    get formValid(): any {
        return this.formData.controls;
    }

    getProvinceAll(): Array<Options> {
        this.locationsService.getProvinceAll().subscribe((result: any) => {
            result.forEach((element) => {
                const index = new Options(element.name, element.name);
                this.listProvince.push(index);
            });
        });
        return this.listProvince;
    }

    getDistrictByProvince(province: any): Array<Options> {
        this.locationsService.getDistrictByProvince(province).subscribe((result: any) => {
            result.forEach((element) => {
                const index = new Options(element.name, element.name);
                this.listDistrict.push(index);
            });
        });
        return this.listDistrict;
    }

    getWardByDistrict(district): Array<Options> {
        this.locationsService.getWardByDistrict(district).subscribe((result: any) => {
            result.forEach((element) => {
                const index = new Options(element.name, element.name);
                this.listWard.push(index);
            });
        });
        return this.listWard;
    }

    getAgencyAll(): Array<Options> {
        this.agencyService.getAgencyAll().subscribe((result: any) => {
            result.forEach((element) => {
                const index = new Options(element.agencyName, element.agencyName);
                this.listAgency.push(index);
            });
        });
        return this.listAgency;
    }

    selectObject(): void {
        this.errorObject = false;
    }

    selectProvince(even): void {
        this.errorTinh = false;
        this.listDistrict = [];
        this.listDistrict = this.getDistrictByProvince(even);
        console.log(even);
        this.logger.log(this.listDistrict);
    }

    selectDistrict(even): void {
        this.errorHuyen = false;
        this.listWard = [];
        this.listWard = this.getWardByDistrict(even);
    }

    selectWard(): void {
        this.errorXa = false;
    }

    selectThon(): void {
    }

    selectAgency(): void {
        if (this.formData.value.agency) {
            this.errorAgency = false;
        }
    }

    // ----------------------------------------
    hiddenCommentType(event): void {
        this.showCommentType = false;
        this.errorCommenttype = false;
        this.errorCommenttypeOther = false;
        this.formData.patchValue({
            commenttype: event.target.value
        });
    }

    resetCommentType(): void {
        this.showCommentType = true;
        this.errorCommenttype = false;
        this.formData.patchValue({
            commenttype: ''
        });
    }

    changeCommentType(event): void {
        this.errorCommenttypeOther = false;
        this.formData.patchValue({
            commenttype: event.target.value
        });
    }

    // ----------------------------------------------------

    selectStatus(): void {
        if (this.formData.value.status === 'Khác') {
            this.showStatus = true;
        } else {
            this.showStatus = false;
            this.errorStatusOther = false;
        }
    }

    changeStatus(event): void {
        this.errorStatusOther = false;
        this.status = event.target.value;
    }

    // ----------------------------------------
    hiddenFields(event): void {
        this.showFieldes = false;
        this.errorFields = false;
        this.errorFieldsOther = false;
        this.formData.patchValue({
            fields: event.target.value
        });
    }

    resetFields(): void {
        this.showFieldes = true;
        this.errorFields = false;
        this.formData.patchValue({
            fields: ''
        });
    }

    changeFields(event): void {
        this.errorFieldsOther = false;
        this.formData.patchValue({
            fields: event.target.value
        });
    }

    // ----------------------------------------

    comeBack() {
        this.location.back();
    }

    onSelectFile(event): void {
        if (event.target.files.length > 0) {
            this.logger.log(event.target.files[0]);
            this.errorFiles = '';
            this.currentFile = event.target.files[0];
            this.imgURL = event.target.files[0].name;

            this.formData.patchValue({
                files: this.currentFile
            });
        }
    }

    closeError(): void {
        this.error = '';
    }

    onSubmit(): void {
        // this.ngay = false;
        this.submitted = true;
        this.errorObject = false;
        this.errorTinh = false;
        this.errorHuyen = false;
        this.errorXa = false;
        this.errorCommenttype = false;
        this.errorCommenttypeOther = false;
        this.errorFields = false;
        this.errorFieldsOther = false;
        this.errorAgency = false;
        this.errorStatus = false;
        this.errorStatusOther = false;

        if (!this.formData.value.object) {
            this.errorObject = true;
        }
        if (this.tinh === 'Tỉnh/Thành phố') {
            this.errorTinh = true;
        }
        if (this.huyen === 'Quận/Huyện') {
            this.errorHuyen = true;
        }
        if (this.xa === 'Phường/Xã') {
            this.errorXa = true;
        }
        if (this.tinh !== 'Tỉnh/Thành phố' && this.huyen !== 'Quận/Huyện' && this.xa !== 'Phường/Xã' && this.thon !== '') {
            this.formData.patchValue({
                address: this.thon + ', ' + this.xa + ', ' + this.huyen + ', ' + this.tinh
            });
        }

        //----------------------------------------------------------------
        if (this.formData.value.status === '') {
            this.errorStatus = true;
        }
        if (!this.status && this.formData.value.status === 'Khác') {
            this.errorStatusOther = true;
        }
        if (this.status) {
            this.formData.get('status').setValue(this.status);
        }

        // ----------------------------------------------------------------
        if (this.formData.value.commenttype === '') {
            this.errorCommenttypeOther = true;
        } else if (this.formData.value.commenttype === null) {
            this.errorCommenttype = true;
        }

        if (this.formData.value.fields === '') {
            this.errorFieldsOther = true;
        } else if (this.formData.value.fields === null) {
            this.errorFields = true;
        }

        if (this.fields) {
            this.formData.get('fields').setValue(this.fields);
        }
        if (this.commenttype) {
            this.formData.get('commenttype').setValue(this.commenttype);
        }
        // ----------------------------------------------------------------

        if (this.currentFile === null) {
            this.formData.get('files').setValue(new File([''], null));
        }

        //--------------------------------------------------------------
        if (this.formData.value.reportingdeadline) {
            this.formData.get('reportingdeadline').setValue(this.formData.value.reportingdeadline.toString());
        }
        if (!this.formData.value.agency) {
            this.formData.get('agency').setValue(this.auth.currentAccountValue.agency);
        }

        this.logger.log(this.formData.value);

        if (this.formData.status === 'VALID') {
            this.recommendationsService.create(this.formData.value).subscribe({
                next: (response) => {
                    this.commenttype = '';
                    this.fields = '';
                    this.status = '';
                    this.showCommentType = false;
                    this.showFieldes = false;
                    this.showStatus = false;
                    this.error = '';
                    this.imgURL = null;
                    this.submitted = false;
                    this.uploadFile.nativeElement.value = '';
                    this.tinh = 'Tỉnh/Thành phố';
                    this.huyen = 'Quận/Huyện';
                    this.xa = 'Phường/Xã';
                    this.thon = '';
                    this.myForm.resetForm();
                    this.success = response.message;
                },
                error: (err) => {
                    this.logger.log(err);
                    this.error = err.message;
                }
            });
        }

        setTimeout(() => {
            this.success = '';
        }, 3000);
        setTimeout(() => {
            this.error = '';
        }, 5000);
    }


}
