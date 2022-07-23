import { Component, Input, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';

export interface ViolationTableItem {
  count: number;
  diff: number;
}

export interface ViolationsTableItem {
  uuid: string;
  name: string;
  blockers: ViolationTableItem;
  criticals: ViolationTableItem;
  majors: ViolationTableItem;
  minors: ViolationTableItem;
  infos: ViolationTableItem;
}

@Component({
  selector: 'sqc-violations-table',
  template: `
    <table
      mat-table
      [dataSource]="dataSource"
      class="sqc-table"
      *ngIf="_data"
      matSort
    >
      <ng-container matColumnDef="name">
        <th mat-header-cell *matHeaderCellDef mat-sort-header>
          {{ nameAlias ? nameAlias : 'Name' }}
        </th>
        <td mat-cell *matCellDef="let element">{{ element.name }}</td>
      </ng-container>

      <ng-container matColumnDef="blockers">
        <th mat-header-cell *matHeaderCellDef mat-sort-header>Blockers</th>
        <td mat-cell *matCellDef="let element">{{ element.blockers.count }}</td>
      </ng-container>

      <ng-container matColumnDef="criticals">
        <th mat-header-cell *matHeaderCellDef mat-sort-header>Criticals</th>
        <td mat-cell *matCellDef="let element">
          {{ element.criticals.count }}
        </td>
      </ng-container>

      <ng-container matColumnDef="majors">
        <th mat-header-cell *matHeaderCellDef mat-sort-header>Majors</th>
        <td mat-cell *matCellDef="let element">{{ element.majors.count }}</td>
      </ng-container>

      <ng-container matColumnDef="minors">
        <th mat-header-cell *matHeaderCellDef mat-sort-header>Minors</th>
        <td mat-cell *matCellDef="let element">{{ element.minors.count }}</td>
      </ng-container>

      <ng-container matColumnDef="infos">
        <th mat-header-cell *matHeaderCellDef mat-sort-header>Infos</th>
        <td mat-cell *matCellDef="let element">{{ element.infos.count }}</td>
      </ng-container>

      <tr mat-header-row *matHeaderRowDef="displayedColumns; sticky: true"></tr>
      <tr mat-row *matRowDef="let row; columns: displayedColumns"></tr>
    </table>
  `,
  styleUrls: ['./violations-table.component.scss'],
})
export class ViolationsTableComponent {
  displayedColumns: string[] = [
    'name',
    'blockers',
    'criticals',
    'majors',
    'minors',
    'infos',
  ];
  dataSource!: MatTableDataSource<ViolationsTableItem>;
  @Input()
  nameAlias?: string;
  @ViewChild(MatSort) sort!: MatSort;

  _data?: ViolationsTableItem[];

  @Input()
  set data(violations: ViolationsTableItem[]) {
    this._data = violations;
    this.dataSource = new MatTableDataSource<ViolationsTableItem>(violations);
    this.dataSource.sort = this.sort;
  }
}
