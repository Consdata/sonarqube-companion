import {AfterViewInit, Component} from "@angular/core";
import {Widget} from "../widget-service";
import {RankingModel} from "./ranking-model";
import {UserStatisticsService} from "../../statistics/user-statistics-service";

@Component({
  selector: 'ranking-widget',
  template: `
    <div class="ranking-widget">
      <div class="ranking-header">
        <div class="ranking-header-element">
          <div>DUPA</div>
        </div>
      </div>
      <div class="ranking-body"></div>
    </div>
  `,
})
export class RankingWidgetComponent extends Widget<RankingModel> implements AfterViewInit {
  constructor(private userStatistics: UserStatisticsService) {
    super();
  }

  ngAfterViewInit(): void {
    this.userStatistics.getUserStatistics('db9b61eb-a02d-4b97-8db6-3cf53cda5400', '2017-01-01', '2018-01-01').subscribe(response =>
      console.log(response)
    )
  }

}
