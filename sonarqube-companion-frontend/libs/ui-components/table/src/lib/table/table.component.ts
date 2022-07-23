import { Component, Input } from '@angular/core';
import { Row, Table } from './table-model';
import { DataSource } from '@angular/cdk/collections';
import { BehaviorSubject, Observable } from 'rxjs';
import { TableService } from './table.service';

@Component({
  selector: 'sqc-table',
  template: `
    <ng-container *ngIf="dataSource">
      <cdk-table [dataSource]="dataSource">
        <ng-container
          *ngFor="let column of displayedColumns"
          [cdkColumnDef]="column"
        >
          <cdk-header-cell *cdkHeaderCellDef>{{
            table.columns[column].name
          }}</cdk-header-cell>
          <cdk-cell *cdkCellDef="let element">
            <sqc-table-cell [cell]="element.cells[column]"></sqc-table-cell>
          </cdk-cell>
        </ng-container>

        <cdk-header-row *cdkHeaderRowDef="displayedColumns"></cdk-header-row>
        <cdk-row *cdkRowDef="let row; columns: displayedColumns"></cdk-row>
      </cdk-table>
    </ng-container>
  `,
  styleUrls: ['./table.component.scss'],
})
export class TableComponent {
  displayedColumns: string[] = [];
  dataSource: SqcTableDataSource | null = null;
  table!: Table;

  constructor(private tableService: TableService) {}

  @Input()
  set data(table: Table | null) {
    if (table) {
      this.table = table;
      this.dataSource = new SqcTableDataSource(table.rows);
      this.displayedColumns = Object.keys(table.columns);
    }
  }
}

export class SqcTableDataSource extends DataSource<Row> {
  data: BehaviorSubject<Row[]>;

  constructor(rows: Row[]) {
    super();
    this.data = new BehaviorSubject<Row[]>(rows);
  }

  connect(): Observable<Row[]> {
    return this.data;
  }

  disconnect() {}
}
