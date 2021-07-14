import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import {RecommendationsService} from '../../core/services/recommendations.service';
import {LoggerService} from '../../core/services/logger.service';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {FeedbackService} from '../../core/services/feedback.service';
import {AuthenticationService} from '../../core/services/authentication.service';
import {Feedback} from '../../core/models/Feedback';
import {environment} from '../../../environments/environment';
import {Account} from '../../core/models/Account';
import {Options} from '../../core/models/Options';
import {AgencyService} from '../../core/services/agency.service';


@Component({
    selector: 'app-detail',
    templateUrl: './detail.component.html',
    styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {
    @ViewChild('myForm') myForm: NgForm;
    @ViewChild('myFormForwarded') myFormForwarded: NgForm;
    @ViewChild('uploadFile') uploadFile: ElementRef;
    currentAccount: Account;
    formData: FormGroup;
    formDataForwarded: FormGroup;
    listFeedback: Array<any> = [];
    listAgency: Array<Options> = [];
    id: any;
    contents: any;
    files: any;
    filesName: any;
    showForm = false;
    showFormForwarded = false;
    currentFile: File = null;
    imgURL: any = null;
    errorFiles = '';
    success = '';
    error = '';
    submitted = false;
    accountName: string = '';
    agencyName: string = '';
    errorAgency = false;

    constructor(
        private auth: AuthenticationService,
        private recommendationsService: RecommendationsService,
        private agencyService: AgencyService,
        private feedbackService: FeedbackService,
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private location: Location,
        private logger: LoggerService
    ) {
    }

    ngOnInit(): void {
        this.currentAccount = this.auth.currentAccountValue;
        this.accountName = this.currentAccount.accountName;
        this.listAgency = this.getAgencyAll();

        this.route.queryParamMap.subscribe((params) => {
            this.id = params.get('id');
        });
        if (this.id) {
            this.recommendationsService.getRecommendationsByID(this.id).subscribe((result) => {
                this.contents = result.contents;
                this.files = result.files;
                if (result.files) {
                    this.filesName = result.files.substring(32);
                }
                this.logger.log(result);
            });
            this.feedbackService.getByRecommendations(this.id).subscribe((result) => {
                this.listFeedback = result;
                this.logger.log(this.listFeedback);
            });
        }

        this.formData = this.formBuilder.group({
            files: [''],
            title: ['', [Validators.required]],
            contents: ['', [Validators.required]],
            status: [''],
            accountName: [''],
            recommendationsid: ['']
        });

        this.formDataForwarded = this.formBuilder.group({
            agency: [null, [Validators.required]],
            title: ['', [Validators.required]],
            contents: ['', [Validators.required]],
            status: [''],
            accountName: [''],
            recommendationsid: ['']
        });
    }

    get formValid() {
        return this.formData.controls;
    }

    get formValidForwarded() {
        return this.formDataForwarded.controls;
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

    selectAgency(): void {
        if (this.formDataForwarded.value.agency) {
            this.errorAgency = false;
        }
    }

    onSelectFile(event): void {
        if (event.target.files.length > 0) {
            this.errorFiles = '';
            this.currentFile = event.target.files[0];
            this.imgURL = event.target.files[0].name;
            this.formData.get('files').setValue(event.target.files[0]);
        }
    }

    closeError() {
        this.error = '';
    }

    closeForm() {
        this.showForm = false;
        this.showFormForwarded = false;
        this.submitted = false;
        this.errorFiles = '';
        this.imgURL = null;
        this.uploadFile.nativeElement.value = '';
        this.myForm.resetForm();
    }

    closeFormForwarded() {
        this.showFormForwarded = false;
        this.submitted = false;
        this.myFormForwarded.resetForm();
    }

    comeBack() {
        this.location.back();
    }

    showFeedbackForm(): void {
        this.showForm = true;
        this.showFormForwarded = false;
    }

    showForwarded(): void {
        this.showForm = false;
        this.showFormForwarded = true;
    }

    onSubmitFeedback(): void {
        this.success = '';
        this.error = '';
        this.submitted = true;

        if (this.currentFile === null) {
            this.imgURL = null;
            this.formData.get('files').setValue(new File([''], null));
        }
        if (this.id) {
            this.formData.get('recommendationsid').setValue(this.id);
        }
        if (this.accountName) {
            this.formData.get('accountName').setValue(this.accountName);
        }
        this.formData.get('status').setValue('Phản hồi');

        this.logger.log(this.formData.value);

        if (this.formData.status === 'VALID') {
            this.feedbackService.createFeedback(this.formData.value).subscribe({
                next: (response) => {
                    let fileURL = '';
                    if (this.imgURL) {
                        fileURL = `${environment.baseUrlServer}` + 'api/files/' + this.imgURL;
                    }
                    const item = new Feedback(this.formData.value.title, this.formData.value.contents, fileURL);
                    this.listFeedback.unshift(item);

                    this.showForm = false;
                    this.imgURL = null;
                    this.submitted = false;
                    this.uploadFile.nativeElement.value = '';
                    this.myForm.resetForm();
                    this.error = '';
                    this.success = response.message;
                    this.logger.log(this.success);
                },
                error: (err) => {
                    if (err) {
                        this.error = err.message;
                    }
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

    onSubmitForwarded(): void {
        this.submitted = true;
        this.errorAgency = false;

        if (this.formDataForwarded.value.agency === null) {
            this.errorAgency = true;
        }

        // this.formDataForwarded.get('title').setValue('Chuyển tiếp xử lý phản ánh');
        // this.formDataForwarded.get('contents').setValue('Kính gửi cơ quan ' + this.formDataForwarded.value.agency + ', chúng tôi .........');
        this.formDataForwarded.get('status').setValue('Chuyển tiếp');
        this.formDataForwarded.get('accountName').setValue(this.accountName);
        this.formDataForwarded.get('accountName').setValue(this.accountName);
        this.formDataForwarded.get('recommendationsid').setValue(this.id);
        // this.formDataForwarded.get('files').setValue(new File([''], null));
        this.logger.log(this.formDataForwarded.value);


        if (this.formDataForwarded.status === 'VALID') {
            this.feedbackService.createForwarded(this.formDataForwarded.value).subscribe({
                next: (response) => {
                    this.showForm = false;
                    this.showFormForwarded = false;
                    this.submitted = false;
                    this.myFormForwarded.resetForm();
                    this.error = '';
                    this.success = response.message;
                },
                error: (err) => {
                    if (err) {
                        this.error = err.message;
                    }
                }
            });
        }
    }
}
