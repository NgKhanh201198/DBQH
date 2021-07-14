import {Component, OnInit} from '@angular/core';
import {RecommendationsService} from '../../core/services/recommendations.service';
import {LoggerService} from '../../core/services/logger.service';
import {LocationsService} from '../../core/services/locations.service';
import {Options} from '../../core/models/Options';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    listRecommendations: Array<any> = [];
    listProvince: any;
    listDistrict: any;
    page: number = 1;
    keyword: any;
    showProvince = false;
    showDicstric = false;
    indexProvince: any;
    indexDicstric: any;

    constructor(
        private recommendationsService: RecommendationsService,
        private locationsService: LocationsService,
        private logger: LoggerService
    ) {
    }

    ngOnInit(): void {
        this.recommendationsService.getRecommendationsAll().subscribe((result) => {
            this.listRecommendations = result;
        });

        this.locationsService.getProvinceAll().subscribe((result) => {
            this.listProvince = result;
            this.logger.log(result);
        });
    }

    search(keyword): void {
        this.recommendationsService.search(keyword).subscribe((result) => {
            this.listRecommendations = result;
            this.logger.log(result);
        });
    }

    onChange() {
        if (this.keyword === '') {
            this.recommendationsService.search('').subscribe((result) => {
                this.listRecommendations = result;
            });
        }
    }

    selectProvince(id, province) {
        this.indexProvince = id;
        this.showProvince = true;
        this.showDicstric = false;
        this.locationsService.getDistrictByProvince(province).subscribe((result: any) => {
            this.logger.log(result);
            this.listDistrict = result;
        });

        this.recommendationsService.search(province).subscribe((result) => {
            this.listRecommendations = result;
            this.logger.log(result);
        });
    }

    selectDicstric(id, dicstric) {
        this.indexDicstric = id;
        this.showDicstric = true;
        this.recommendationsService.search(dicstric).subscribe((result) => {
            this.listRecommendations = result;
            this.logger.log(result);
        });
    }

    closeProvince() {
        this.showProvince = false;
        this.recommendationsService.search('').subscribe((result) => {
            this.listRecommendations = result;
            this.logger.log(result);
        });
    }

}
