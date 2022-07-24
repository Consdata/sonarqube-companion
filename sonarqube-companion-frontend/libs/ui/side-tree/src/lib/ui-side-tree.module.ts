import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SideTreeItemComponent} from './side-tree-item.component';
import {SideTreeComponent} from './side-tree.component';
import {MatTreeModule} from '@angular/material/tree';
import {MatIconModule} from '@angular/material/icon';
import {MatRippleModule} from '@angular/material/core';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';

@NgModule({
  imports: [
    CommonModule,
    MatTreeModule,
    MatIconModule,
    MatRippleModule,
    MatDividerModule,
    MatButtonModule
  ],
  declarations: [
    SideTreeItemComponent,
    SideTreeComponent
  ],
  exports: [
    SideTreeItemComponent,
    SideTreeComponent
  ]
})
export class UiSideTreeModule {
}
