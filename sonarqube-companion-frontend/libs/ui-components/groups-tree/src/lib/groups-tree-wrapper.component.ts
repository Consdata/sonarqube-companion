import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Observable} from 'rxjs';
import {GroupsTreeItem} from './groups-tree-item';

@Component({
  selector: 'sqc-groups-tree-wrapper',
  template: `
    <ng-container *ngIf="(rootGroup$ | async) as rootGroup; else spinner">
      <sqc-groups-tree [rootGroup]="rootGroup"></sqc-groups-tree>
    </ng-container>
    <ng-template #spinner>
      <mat-spinner></mat-spinner>
    </ng-template>
  `,
  styleUrls: ['./groups-tree.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GroupsTreeWrapperComponent {
  @Input('rootGroup')
  rootGroup$!: Observable<GroupsTreeItem>;

}
