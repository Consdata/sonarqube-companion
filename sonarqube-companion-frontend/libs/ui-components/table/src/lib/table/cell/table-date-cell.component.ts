import { Component } from '@angular/core';
import { TableCell } from './table-cell';
import { Cell } from '../table-model';

@Component({
  selector: 'sqc-table-date-cell',
  template: `
    <ng-container *ngIf="_cell">
      <button class="dateBtn" (click)="datepicker.open()">asdasd</button>
      <input [matDatepicker]="datepicker" class="date" />
      <mat-datepicker #datepicker></mat-datepicker>
    </ng-container>
  `,
})
export class TableDateCellComponent implements TableCell {
  _cell?: Cell;

  setCell(cell: Cell): void {
    this._cell = cell;
  }
}
