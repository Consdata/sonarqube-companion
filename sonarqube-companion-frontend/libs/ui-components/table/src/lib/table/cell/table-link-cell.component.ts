import { Component } from '@angular/core';
import { TableCell } from './table-cell';
import { Cell } from '../table-model';

@Component({
  selector: 'sqc-table-link-cell',
  template: `
    <ng-container *ngIf="_cell">
      <a [href]="_cell.value" target="_blank">link</a>
    </ng-container>
  `,
})
export class TableLinkCellComponent implements TableCell {
  _cell?: Cell;

  setCell(cell: Cell): void {
    this._cell = cell;
  }
}
