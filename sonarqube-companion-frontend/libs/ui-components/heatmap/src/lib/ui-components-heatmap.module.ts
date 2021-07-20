import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HeatmapComponent} from './heatmap.component';
import {NgxChartsModule} from '@swimlane/ngx-charts';

@NgModule({
  imports: [
    CommonModule,
    NgxChartsModule
  ],
  declarations: [
    HeatmapComponent
  ],
  exports: [
    HeatmapComponent
  ]
})
export class UiComponentsHeatmapModule {
}
