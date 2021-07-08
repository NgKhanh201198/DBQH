import {Component, OnInit} from '@angular/core';
import {RecommendationsService} from '../../core/services/recommendations.service';
import {LoggerService} from '../../core/services/logger.service';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    listRecommendations: Array<any> = [];
    page: number = 1;
    keyword: any;

    constructor(
        private recommendationsService: RecommendationsService,
        private logger: LoggerService
    ) {
    }

    ngOnInit(): void {
        this.recommendationsService.getRecommendationsAll().subscribe((result) => {
            this.listRecommendations = result;
        });
    }

    search(keyword): void {
        this.recommendationsService.search(keyword).subscribe((result) => {
            this.listRecommendations = result;
            this.logger.log(result);
        });
    }

}
