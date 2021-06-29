import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {Options} from '../../core/models/Options';
import {LocationsService} from '../../core/services/locations.service';
import {emailValidator, phoneNumberValidator} from '../../../assets/custom/validation/CustomValidator';
import {Observable} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {AgencyService} from '../../core/services/agency.service';

@Component({
    selector: 'app-tiepnhanphananh',
    templateUrl: './tiepnhanphananh.component.html',
    styleUrls: ['./tiepnhanphananh.component.css']
})
export class TiepnhanphananhComponent implements OnInit {
    @ViewChild('myForm') myForm: NgForm;
    formData: FormGroup;
    submitted = false;
    errorObject = false;
    errorTinh = false;
    errorHuyen = false;
    errorXa = false;
    errorCommenttype = false;
    errorCommenttypeOther = false;
    errorFields = false;
    errorFieldsOther = false;
    errorAgency = false;
    listProvince: Array<Options> = [];
    listDistrict: Array<Options> = [];
    listWard: Array<Options> = [];
    listAgency: Array<Options> = [];
    CommentType: '';
    Fields: '';
    showCommentType = false;
    showFieldes = false;
    ngay = false;
    tinh: any = 'Tỉnh/Thành phố';
    huyen: any = 'Quận/Huyện';
    xa: any = 'Phường/Xã';
    thon: any;
    reader = new FileReader();
    currentFile: File = null;
    imgURL: any = null;
    errorFiles = '';

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


    constructor(
        private formBuilder: FormBuilder,
        private locationsService: LocationsService,
        private agencyService: AgencyService
    ) {
        this.listProvince = this.getProvinceAll();
        this.listAgency = this.getAgencyAll();
    }

    ngOnInit(): void {
        this.formData = this.formBuilder.group({
            object: ['', [Validators.required]],
            fullname: ['', [Validators.required]],
            address: ['', [Validators.required]],
            phonenumber: ['', [Validators.required, phoneNumberValidator()]],
            email: ['', [Validators.required, emailValidator()]],
            title: ['', [Validators.required]],
            commenttype: [this.CommentType, [Validators.required]],
            fields: [this.Fields, [Validators.required]],
            contents: ['', [Validators.required]],
            status: ['', [Validators.required]],
            reportingdeadline: ['', [Validators.required]],
            agency: ['Đăng nhập để chọn cơ quan tiếp nhận', [Validators.required]],
            files: ['', [Validators.required]]
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
                const index = new Options(element.id, element.agencyName);
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
    }

    selectDistrict(even): void {
        this.errorHuyen = false;
        this.listWard = [];
        this.listWard = this.getWardByDistrict(even);
    }

    selectWard(): void {
        this.errorXa = false;
    }

    selectAgency(): void {
        if (this.formData.value.agency) {
            this.errorAgency = false;
        }
    }

    // ----------------------------------------
    resetCommentType(): void {
        this.showCommentType = true;
        this.CommentType = '';
    }

    hiddenCommentType(): void {
        this.showCommentType = false;
        this.CommentType = '';
        if (this.formData.value.commenttype) {
            this.errorCommenttype = false;
            this.errorCommenttypeOther = false;
        }
    }

    commentTypeChange(event): void {
        this.errorCommenttype = false;
        this.CommentType = event;
    }

    // ----------------------------------------
    resetFields(): void {
        this.showFieldes = true;
        this.Fields = '';
    }

    hiddenFields(): void {
        this.showFieldes = false;
        this.Fields = '';
        if (this.formData.value.fields) {
            this.errorFields = false;
            this.errorFieldsOther = false;
        }
    }

    fieldsChange(event): void {
        this.Fields = event;
        this.errorFields = false;
    }

    reportingdeadlineChange(event): void {
        if (event) {
            this.ngay = true;
        } else {
            this.ngay = false;
        }
        if (this.formData.value.reportingdeadline === null) {
            this.ngay = false;
        }
    }


    onSelectFile(event): void {
        if (event.target.files.length > 0) {
            this.errorFiles = '';
            this.currentFile = event.target.files[0];
            this.imgURL = event.target.files[0].name;
        }
    }

    onSubmit(): void {
        this.ngay = false;
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

        if (this.currentFile == null) {
            this.errorFiles = 'Vui lòng chọn file.';
        }
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
        if (this.formData.value.commenttype === '') {
            this.errorCommenttype = true;
        }
        if (this.formData.value.fields === '') {
            this.errorFields = true;
        }
        if (this.formData.value.reportingdeadline === null) {
            this.ngay = false;
        }
        if (this.formData.value.agency === 'Đăng nhập để chọn cơ quan tiếp nhận') {
            this.errorAgency = true;
        }

        console.log(this.CommentType !== '');
        console.log(this.Fields !== '');
        console.log(this.CommentType);
        console.log(this.Fields);
        if (this.CommentType !== '') {
            this.errorCommenttypeOther = false;
            this.formData.get('commenttype').setValue(this.CommentType);
        } else {
            this.errorCommenttypeOther = true;
        }

        if (this.Fields !== '') {
            this.formData.get('fields').setValue(this.Fields);
        } else {
            this.errorFieldsOther = true;
        }


        if (!(this.currentFile === null)) {
            this.formData.get('files').setValue(this.currentFile);
        }

        this.formData.get('reportingdeadline').setValue(this.formData.value.reportingdeadline.toString());
        this.formData.get('address').setValue(this.thon + ', ' + this.xa + ', ' + this.huyen + ', ' + this.tinh);

        console.log(this.formData.value);


        // this.myForm.resetForm();
    }
}
