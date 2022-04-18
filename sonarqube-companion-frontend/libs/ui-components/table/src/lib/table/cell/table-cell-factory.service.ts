import {Injectable, Type} from '@angular/core';
import {Cell} from '../table-model';
import {TableCell} from './table-cell';
import {TableTextCellComponent} from './table-text-cell.component';
import {TableInputCellComponent} from './table-input-cell.component';

@Injectable({providedIn: 'root'})
export class TableCellFactoryService {

  public createCell(cell: Cell): Type<TableCell> {
    if (!cell.type) {
      return TableTextCellComponent;
    }
    if (cell.type.name === 'input') {
      return TableInputCellComponent;
    } else {
      return TableTextCellComponent;
    }
  }
}
