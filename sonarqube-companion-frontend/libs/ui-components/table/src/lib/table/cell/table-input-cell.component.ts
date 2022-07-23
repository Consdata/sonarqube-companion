import { Component } from '@angular/core';
import { TableCell } from './table-cell';
import { Cell } from '../table-model';

@Component({
  selector: 'sqc-table-input-cell',
  template: `
    <ng-container *ngIf="_cell">
      <input [value]="_cell.value" />
    </ng-container>
  `,
})
export class TableInputCellComponent implements TableCell {
  _cell?: Cell;

  setCell(cell: Cell): void {
    this._cell = cell;
  }
}
