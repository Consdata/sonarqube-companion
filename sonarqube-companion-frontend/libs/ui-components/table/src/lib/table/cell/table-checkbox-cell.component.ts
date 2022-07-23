import { Component } from '@angular/core';
import { TableCell } from './table-cell';
import { Cell } from '../table-model';

@Component({
  selector: 'sqc-table-checkbox-cell',
  template: ` <ng-container *ngIf="_cell"> checkbox </ng-container> `,
})
export class TableCheckboxCellComponent implements TableCell {
  _cell?: Cell;

  setCell(cell: Cell): void {
    this._cell = cell;
  }
}
