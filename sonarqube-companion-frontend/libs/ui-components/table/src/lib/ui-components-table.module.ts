import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ViolationsTableComponent} from './table/violations-table.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatSortModule} from '@angular/material/sort';
import {MatIconModule} from '@angular/material/icon';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {TableComponent} from './table/table.component';
import {CdkTableModule} from '@angular/cdk/table';
import {MatTableModule} from '@angular/material/table';
import {TableCellComponent} from './table/cell/table-cell.component';
import {TableTextCellComponent} from './table/cell/table-text-cell.component';
import {TableInputCellComponent} from './table/cell/table-input-cell.component';

@NgModule({
  imports: [
    CommonModule,
    CdkTableModule,
    MatTableModule,
    MatCheckboxModule,
    MatSortModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule
  ],
  declarations: [
    ViolationsTableComponent,
    TableComponent,
    TableCellComponent,
    TableTextCellComponent,
    TableInputCellComponent
  ],
  exports: [
    ViolationsTableComponent,
    TableComponent
  ]
})
export class UiComponentsTableModule {
}
