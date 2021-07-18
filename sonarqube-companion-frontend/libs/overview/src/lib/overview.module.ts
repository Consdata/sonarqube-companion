import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {OverviewComponent} from './overview/overview.component';
import {RouterModule} from '@angular/router';
import {overviewRouting} from './overview.routing';
import {UiComponentsGroupsTreeModule} from '@sonarqube-companion-frontend/ui-components/groups-tree';
import {MatDividerModule} from '@angular/material/divider';
import {GroupOverviewModule} from '@sonarqube-companion-frontend/group-overview';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(overviewRouting),
    UiComponentsGroupsTreeModule,
    MatDividerModule,
    GroupOverviewModule
  ],
  declarations: [
    OverviewComponent
  ]
})
export class OverviewModule {
}
