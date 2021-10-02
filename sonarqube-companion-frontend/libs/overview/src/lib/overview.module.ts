import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {OverviewComponent} from './overview/overview.component';
import {RouterModule} from '@angular/router';
import {overviewRouting} from './overview.routing';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(overviewRouting),
  ],
  declarations: [
    OverviewComponent
  ]
})
export class OverviewModule {
}
