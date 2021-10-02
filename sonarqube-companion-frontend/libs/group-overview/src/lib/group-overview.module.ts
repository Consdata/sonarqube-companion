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
import {CdkAccordionModule} from '@angular/cdk/accordion';
import {MatChipsModule} from '@angular/material/chips';
import {OverlayModule} from '@angular/cdk/overlay';
import {UiComponentsTimeSelectModule} from '@sonarqube-companion-frontend/ui-components/time-select';
import { GroupStructureButtonsComponent } from './group-structure-buttons/group-structure-buttons.component';
import { GroupNameComponent } from './group-name/group-name.component';
import { GroupSeveritiesComponent } from './group-severities/group-severities.component';
import { GroupTimelineComponent } from './group-timeline/group-timeline.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { GroupProjectsSummaryComponent } from './group-projects-summary/group-projects-summary.component';
import { GroupMembersSummaryComponent } from './group-members-summary/group-members-summary.component';


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
    MatTooltipModule,
    CdkAccordionModule,
    MatChipsModule,
    UiComponentsTimeSelectModule,
    MatProgressSpinnerModule
  ],
  declarations: [
    GroupOverviewComponent,
    GroupStructureButtonsComponent,
    GroupNameComponent,
    GroupSeveritiesComponent,
    GroupTimelineComponent,
    GroupProjectsSummaryComponent,
    GroupMembersSummaryComponent
  ],
  exports: [
    GroupOverviewComponent
  ]
})
export class GroupOverviewModule {
}
