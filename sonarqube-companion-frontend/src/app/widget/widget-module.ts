import {NgModule} from "@angular/core";
import {RankingWidgetComponent} from "./ranking/ranking-widget-component";
import {WidgetWrapperComponent} from "./widget-wrapper-component";
import {WidgetService} from "./widget-service";
import {WidgetModelFactory} from "./widget-model-factory-service";
import {WidgetFactory} from "./widget-factory";
import {UserStatisticsService} from "../statistics/user-statistics-service";

@NgModule({
  declarations: [
    RankingWidgetComponent,
    WidgetWrapperComponent
  ],
  entryComponents: [RankingWidgetComponent],
  exports: [WidgetWrapperComponent],
  providers: [
    WidgetFactory,
    WidgetModelFactory,
    WidgetService,
    UserStatisticsService
  ]
})
export class WidgetModule {

}
