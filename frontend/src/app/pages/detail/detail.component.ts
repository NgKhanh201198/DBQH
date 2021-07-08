import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import {RecommendationsService} from '../../core/services/recommendations.service';
import {LoggerService} from '../../core/services/logger.service';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {FeedbackService} from '../../core/services/feedback.service';
import {AuthenticationService} from '../../core/services/authentication.service';
import {Feedback} from '../../core/models/Feedback';

@Component({
    selector: 'app-detail',
    templateUrl: './detail.component.html',
    styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {
    @ViewChild('myForm') myForm: NgForm;
    @ViewChild('uploadFile') uploadFile: ElementRef;
    formData: FormGroup;
    listFeedback: Array<any> = [];
    id: any;
    contents: any;
    files: any;
    filesName: any;
    showForm = false;
    currentFile: File = null;
    imgURL: any = null;
    errorFiles = '';
    success = '';
    error = '';
    submitted = false;
    accountName: string = '';

    constructor(
        private auth: AuthenticationService,
        private recommendationsService: RecommendationsService,
        private feedbackService: FeedbackService,
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private location: Location,
        private logger: LoggerService
    ) {
    }

    ngOnInit(): void {
        this.accountName = this.auth.currentAccountValue.accountName;
        this.route.queryParamMap.subscribe((params) => {
            this.id = params.get('id');
        });
        if (this.id) {
            this.recommendationsService.getRecommendationsByID(this.id).subscribe((result) => {
                this.contents = result.contents;
                this.files = result.files;
                if(result.files){
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
            accountName: ['', [Validators.required]],
            recommendationsid: ['', [Validators.required]]
        });
    }

    get formValid() {
        return this.formData.controls;
    }

    onSelectFile(event): void {
        if (event.target.files.length > 0) {
            this.errorFiles = '';
            this.currentFile = event.target.files[0];
            this.imgURL = event.target.files[0].name;
        }
    }

    closeError() {
        this.error = '';
    }

    closeForm() {
        this.showForm = false;
        this.submitted = false;
        this.errorFiles = '';
        this.imgURL = null;
        this.uploadFile.nativeElement.value = '';
        this.myForm.resetForm();
    }

    comeBack() {
        this.location.back();
    }

    getFiles() {
        console.log('zo file');
        this.recommendationsService.getFiles(this.files).subscribe();
    }

    showFeedbackForm(): void {
        this.showForm = true;
    }

    onSubmit(): void {
        this.success = '';
        this.error = '';
        this.submitted = true;

        if ((!this.currentFile)) {
            this.errorFiles = 'Vui lòng chọn file';
        } else {
            this.formData.get('files').setValue(this.currentFile);
        }

        if (this.id) {
            this.formData.get('recommendationsid').setValue(this.id);
        }

        if (this.accountName) {
            this.formData.get('accountName').setValue(this.accountName);
        }

        this.logger.log(this.formData.value);
        this.feedbackService.createFeedback(this.formData.value).subscribe({
            next: (response) => {
                const item = new Feedback(this.formData.value.title, this.formData.value.contents, 'http://localhost:8080/api/files/' + this.imgURL);
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
        setTimeout(() => {
            this.success = '';
        }, 3000);
        setTimeout(() => {
            this.error = '';
        }, 6000);
    }
}
