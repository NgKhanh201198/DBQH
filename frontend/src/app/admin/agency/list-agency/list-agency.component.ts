import {Component, OnInit} from '@angular/core';
import {AgencyService} from '../../../core/services/agency.service';

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
        private agencyService: AgencyService
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

        if (confirm('Bạn có chắc muốn xóa cơ quan này?')) {
            this.agencyService.deleteAgency(id).subscribe({
                next: (response: any) => {
                    for (let i = 0; i < this.listAgency.length; i++) {
                        if (this.listAgency[i].id === id) {
                            this.listAgency.splice(i, 1);
                            this.success = response.message;
                        }
                    }
                },
                error: (err) => {
                    this.error = err.message;
                    console.log(this.error);
                }
            });
        };
        setTimeout(() => {
            this.success = '';
        }, 3000);
        setTimeout(() => {
            this.error = '';
        }, 5000);
    }

}
