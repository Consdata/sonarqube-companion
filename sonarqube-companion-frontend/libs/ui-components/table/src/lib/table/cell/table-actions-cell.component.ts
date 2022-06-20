import {Component} from '@angular/core';
import {TableCell} from './table-cell';
import {Cell} from '../table-model';

@Component({
  selector: 'sqc-table-date-cell',
  template: `
    <ng-container *ngIf="_cell">
      <mat-icon>delete</mat-icon>
    </ng-container>
  `
})
export class TableActionsCellComponent implements TableCell {
  _cell?: Cell;

  setCell(cell: Cell): void {
    this._cell = cell;
  }

}
