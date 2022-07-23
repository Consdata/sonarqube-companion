import { Component } from '@angular/core';
import { TableCell } from './table-cell';
import { Cell } from '../table-model';

@Component({
  selector: 'sqc-table-combo-cell',
  template: `
    <ng-container *ngIf="_cell">
      <mat-checkbox [value]="_cell.value"></mat-checkbox>
    </ng-container>
  `,
})
export class TableComboCellComponent implements TableCell {
  _cell?: Cell;

  setCell(cell: Cell): void {
    this._cell = cell;
  }
}
