import {Component, OnInit} from '@angular/core';
import {AgencyService} from '../../../core/services/agency.service';
import {LoggerService} from '../../../core/services/logger.service';
import {stringify} from '@angular/compiler/src/util';

@Component({
    selector: 'app-list-agency',
    templateUrl: './list-agency.component.html',
    styleUrls: ['./list-agency.component.css']
})
export class ListAgencyComponent implements OnInit {
    listAgency: Array<any> = [];
    page = 1;
    success = '';
    error = '';

    constructor(
        private agencyService: AgencyService,
        private logger: LoggerService
    ) {
    }

    ngOnInit(): void {
        this.agencyService.getAgencyAll().subscribe((result) => {
            this.listAgency = result;
        });
    }

    closeError() {
        this.error = '';
    }

    deleteAgency(id: any) {
        this.success = '';
        if (confirm('Bạn có chắc muốn xóa cơ quan này?')) {
            this.agencyService.deleteAgency(id).subscribe({
                next: (response: any) => {
                    for (let i = 0; i < this.listAgency.length; i++) {
                        if (this.listAgency[i].id === id) {
                            // this.listAgency.splice(i, 1);
                            this.listAgency[i].status = 'Không hoạt động'
                            this.success = response.message;
                        }
                    }
                },
                error: (err) => {
                    this.error = err.message;
                    this.logger.log(this.error);
                }
            });
        }
        ;

        if (this.success) {
            setTimeout(() => {
                this.success = '';
            }, 3000);
        }

        setTimeout(() => {
            this.error = '';
        }, 5000);
    }

}
