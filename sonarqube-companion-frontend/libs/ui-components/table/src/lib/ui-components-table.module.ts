import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ViolationsTableComponent} from './table/violations-table.component';
import {MatTableModule} from '@angular/material/table';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatSortModule} from '@angular/material/sort';
import {MatIconModule} from '@angular/material/icon';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';

@NgModule({
  imports: [
    CommonModule,
    MatTableModule,
    MatCheckboxModule,
    MatSortModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule
  ],
  declarations: [
    ViolationsTableComponent
  ],
  exports: [
    ViolationsTableComponent
  ]
})
export class UiComponentsTableModule {
}
