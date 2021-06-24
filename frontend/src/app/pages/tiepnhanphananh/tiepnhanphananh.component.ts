import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Options} from '../../core/models/Options';

@Component({
    selector: 'app-tiepnhanphananh',
    templateUrl: './tiepnhanphananh.component.html',
    styleUrls: ['./tiepnhanphananh.component.css']
})
export class TiepnhanphananhComponent implements OnInit {
    formData: FormGroup;
    CommentType: any;
    Fields: any;
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
    ) {
    }

    ngOnInit(): void {
        this.formData = this.formBuilder.group({
            object: ['', []],
            fullname: ['', []],
            address: ['', []],
            phonenumber: ['', []],
            email: ['', []],
            title: ['', []],
            commenttype: ['', []],
            fields: ['', []],
            contents: ['', []],
            status: ['', []],
            reportingdeadline: ['', []],
            agency: ['', []],
            files: ['', []]
        });
    }

    resetCommentType(): void {
        this.showCommentType = true;
        this.CommentType = '';
    }

    hiddenCommentType(): void {
        this.showCommentType = false;
        this.CommentType = '';
    }

    commentTypeChange(event): void {
        this.CommentType = event;
    }

    resetFields(): void {
        this.showFieldes = true;
        this.Fields = '';
        console.log(this.CommentType);
    }

    hiddenFields(): void {
        this.showFieldes = false;
        this.Fields = '';
    }

    fieldsChange(event): void {
        this.Fields = event;
    }


    reportingdeadlineChange(event): void {
        this.ngay = false;
        if (event != null) {
            this.ngay = true;
        }
    }

    onSelectFile(event): void {
        if (event.target.files.length > 0) {
            this.currentFile = event.target.files[0];
            console.log(event.target.files[0].name);
            this.imgURL = event.target.files[0].name;

            // this.reader.readAsDataURL(this.currentFile);
            // // tslint:disable-next-line:variable-name
            // this.reader.onload = (_event) => {
            //     this.imgURL = this.reader.result;
            // };
        }
    }

    onSubmit(): void {
        if (!(this.Fields === '')) {
            this.formData.get('fields').setValue(this.Fields);
        }
        if (!(this.CommentType === '')) {
            this.formData.get('commenttype').setValue(this.CommentType);
        }
        if (!(this.currentFile === null)) {
            this.formData.get('files').setValue(this.currentFile);
        }

        this.formData.get('address').setValue(this.thon + ', ' + this.xa + ', ' + this.huyen + ', ' + this.tinh);
        console.log(this.formData.value);
    }
}
