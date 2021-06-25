import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GroupOverviewComponent} from './group-overview.component';
import {MatDividerModule} from '@angular/material/divider';

@NgModule({
  imports: [
    CommonModule,
    MatDividerModule
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
