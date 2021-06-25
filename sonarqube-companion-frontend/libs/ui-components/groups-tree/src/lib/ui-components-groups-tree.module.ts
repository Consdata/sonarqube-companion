import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatTreeModule} from '@angular/material/tree';
import {MatIconModule} from '@angular/material/icon';
import {MatCardModule} from '@angular/material/card';
import {GroupsTreeWrapperComponent} from './groups-tree-wrapper.component';
import {GroupsTreeComponent} from './groups-tree.component';
import {GroupsTreeItemComponent} from './groups-tree-item.component';
import {MatRippleModule} from '@angular/material/core';

@NgModule({
  imports: [
    CommonModule,
    MatProgressSpinnerModule,
    MatTreeModule,
    MatIconModule,
    MatCardModule,
    MatRippleModule
  ],
  declarations: [
    GroupsTreeWrapperComponent,
    GroupsTreeComponent,
    GroupsTreeItemComponent
  ],
  exports: [
    GroupsTreeWrapperComponent
  ]
})
export class UiComponentsGroupsTreeModule {
}
