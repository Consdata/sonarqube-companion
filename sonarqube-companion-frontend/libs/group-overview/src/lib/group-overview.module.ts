import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GroupOverviewComponent} from './group-overview.component';
import {MatDividerModule} from '@angular/material/divider';
import {UiComponentsValueBadgeModule} from '@sonarqube-companion-frontend/ui-components/value-badge';
import {UiComponentsHeatmapModule} from '@sonarqube-companion-frontend/ui-components/heatmap';
import {UiComponentsTimelineModule} from '@sonarqube-companion-frontend/ui-components/timeline';
import {groupOverviewRouting} from './group-overview.routing';
import {RouterModule} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {UiComponentsTableModule} from '@sonarqube-companion-frontend/ui-components/table';
import {MatButtonModule} from '@angular/material/button';
import {MatSidenavModule} from '@angular/material/sidenav';
import {UiComponentsSelectModule} from '@sonarqube-companion-frontend/ui-components/select';
import {MatTooltipModule} from '@angular/material/tooltip';


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(groupOverviewRouting),
    MatDividerModule,
    UiComponentsValueBadgeModule,
    UiComponentsHeatmapModule,
    UiComponentsTimelineModule,
    MatIconModule,
    MatButtonModule,
    MatSidenavModule,
    UiComponentsTableModule,
    UiComponentsSelectModule,
    MatTooltipModule
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
