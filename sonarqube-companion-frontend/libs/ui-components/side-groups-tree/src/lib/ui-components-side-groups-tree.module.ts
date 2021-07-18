import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GroupsTreeComponent} from './groups-tree.component';
import {MatTreeModule} from '@angular/material/tree';
import {MatIconModule} from '@angular/material/icon';
import {GroupsTreeWrapperComponent} from './groups-tree-wrapper.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {GroupsTreeItemComponent} from './groups-tree-item.component';
import {MatListModule} from '@angular/material/list';

@NgModule({
  imports: [
    CommonModule,
    MatTreeModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatListModule
  ],
  declarations: [
    GroupsTreeComponent,
    GroupsTreeWrapperComponent,
    GroupsTreeItemComponent
  ],
  exports: [
    GroupsTreeWrapperComponent
  ]
})
export class UiComponentsSideGroupsTreeModule {
}
