import { Component } from '@angular/core';
import { TableCell } from './table-cell';
import { Cell } from '../table-model';

@Component({
  selector: 'sqc-table-text-cell',
  template: `
    <ng-container *ngIf="_cell">
      {{ _cell.value }}
    </ng-container>
  `,
})
export class TableTextCellComponent implements TableCell {
  _cell?: Cell;

  setCell(cell: Cell): void {
    this._cell = cell;
  }
}
