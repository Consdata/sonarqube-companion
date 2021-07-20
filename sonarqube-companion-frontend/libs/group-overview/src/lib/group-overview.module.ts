import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GroupOverviewComponent} from './group-overview.component';
import {MatDividerModule} from '@angular/material/divider';
import {UiComponentsValueBadgeModule} from '@sonarqube-companion-frontend/ui-components/value-badge';
import {UiComponentsHeatmapModule} from '@sonarqube-companion-frontend/ui-components/heatmap';
import {UiComponentsTimelineModule} from '@sonarqube-companion-frontend/ui-components/timeline';
import {groupOverviewRouting} from './group-overview.routing';
import {RouterModule} from '@angular/router';


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(groupOverviewRouting),
    MatDividerModule,
    UiComponentsValueBadgeModule,
    UiComponentsHeatmapModule,
    UiComponentsTimelineModule
  ],
  declarations: [
    GroupOverviewComponent
  ],
  exports: [
    GroupOverviewComponent
  ]
})
export class GroupOverviewModule {
}
