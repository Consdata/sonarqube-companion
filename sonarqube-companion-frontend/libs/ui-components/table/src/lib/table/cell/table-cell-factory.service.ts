import { Injectable, Type } from '@angular/core';
import { Cell } from '../table-model';
import { TableCell } from './table-cell';
import { TableTextCellComponent } from './table-text-cell.component';
import { TableInputCellComponent } from './table-input-cell.component';
import { TableCheckboxCellComponent } from './table-checkbox-cell.component';
import { TableComboCellComponent } from './table-combo-cell.component';
import { TableDateCellComponent } from './table-date-cell.component';
import { TableLinkCellComponent } from './table-link-cell.component';
import { TableActionsCellComponent } from './table-actions-cell.component';

@Injectable({ providedIn: 'root' })
export class TableCellFactoryService {
  public createCell(cell: Cell): Type<TableCell> {
    if (!cell.type) {
      return TableTextCellComponent;
    }
    if (cell.type.name === 'input') {
      return TableInputCellComponent;
    } else if (cell.type.name === 'checkbox') {
      return TableCheckboxCellComponent;
    } else if (cell.type.name === 'combo') {
      return TableComboCellComponent;
    } else if (cell.type.name === 'date') {
      return TableDateCellComponent;
    } else if (cell.type.name === 'link') {
      return TableLinkCellComponent;
    } else if (cell.type.name === 'actions') {
      return TableActionsCellComponent;
    } else {
      return TableTextCellComponent;
    }
  }
}
