import {NgModule} from "@angular/core";
import {RankingWidgetComponent} from "./ranking/ranking-widget-component";
import {WidgetWrapperComponent} from "./widget-wrapper-component";
import {WidgetService} from "./widget-service";
import {WidgetModelFactory} from "./widget-model-factory-service";
import {WidgetComponentFactory} from "./widget-compoment-factory";
import {UserStatisticsService} from "../statistics/user-statistics-service";
import {BrowserModule} from "@angular/platform-browser";


@NgModule({
  declarations: [
    RankingWidgetComponent,
    WidgetWrapperComponent
  ],
  entryComponents: [RankingWidgetComponent],
  imports: [
    BrowserModule
  ],
  exports: [
    WidgetWrapperComponent
  ],
  providers: [
    WidgetComponentFactory,
    WidgetModelFactory,
    WidgetService,
    UserStatisticsService
  ]
})
export class WidgetModule {

}
