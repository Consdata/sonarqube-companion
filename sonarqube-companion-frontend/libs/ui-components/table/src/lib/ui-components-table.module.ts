import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViolationsTableComponent } from './table/violations-table.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSortModule } from '@angular/material/sort';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { TableComponent } from './table/table.component';
import { CdkTableModule } from '@angular/cdk/table';
import { MatTableModule } from '@angular/material/table';
import { TableCellComponent } from './table/cell/table-cell.component';
import { TableTextCellComponent } from './table/cell/table-text-cell.component';
import { TableInputCellComponent } from './table/cell/table-input-cell.component';
import { TableCheckboxCellComponent } from './table/cell/table-checkbox-cell.component';
import { TableComboCellComponent } from './table/cell/table-combo-cell.component';
import { TableDateCellComponent } from './table/cell/table-date-cell.component';
import { TableLinkCellComponent } from './table/cell/table-link-cell.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { TableActionsCellComponent } from './table/cell/table-actions-cell.component';

@NgModule({
  imports: [
    CommonModule,
    CdkTableModule,
    MatTableModule,
    MatCheckboxModule,
    MatSortModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatButtonModule,
    MatNativeDateModule,
  ],
  declarations: [
    ViolationsTableComponent,
    TableComponent,
    TableCellComponent,
    TableTextCellComponent,
    TableInputCellComponent,
    TableCheckboxCellComponent,
    TableComboCellComponent,
    TableDateCellComponent,
    TableLinkCellComponent,
    TableActionsCellComponent,
  ],
  exports: [ViolationsTableComponent, TableComponent],
})
export class UiComponentsTableModule {}
