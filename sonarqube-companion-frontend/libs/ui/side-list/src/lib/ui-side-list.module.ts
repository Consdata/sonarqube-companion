import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SideListComponent} from './side-list.component';
import {MatDividerModule} from '@angular/material/divider';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';

@NgModule({
  imports: [
    CommonModule,
    MatDividerModule,
    MatIconModule,
    MatButtonModule
  ],
  declarations: [
    SideListComponent
  ],
  exports: [
    SideListComponent
  ]
})
export class UiSideListModule {
}
