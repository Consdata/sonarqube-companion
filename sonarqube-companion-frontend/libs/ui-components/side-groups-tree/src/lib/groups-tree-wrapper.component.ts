import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Observable} from 'rxjs';
import {GroupsTreeItem} from './groups-tree-item';

@Component({
  selector: 'sqc-side-groups-tree-wrapper',
  template: `
    <ng-container *ngIf="(rootGroup$ | async) as rootGroup; else spinner">
      <sqc-side-groups-tree [rootGroup]="rootGroup" (groupSelect)="groupSelect.emit($event)"></sqc-side-groups-tree>
    </ng-container>
    <ng-template #spinner>
      <div class="loading">
        <mat-icon>group_work</mat-icon>
        <div class="label">Groups</div>
        <mat-spinner diameter="20"></mat-spinner>
      </div>
    </ng-template>
  `,
  styleUrls: ['./groups-tree-wrapper.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GroupsTreeWrapperComponent {

  @Input('rootGroup')
  rootGroup$!: Observable<GroupsTreeItem>;

  @Output()
  groupSelect: EventEmitter<GroupsTreeItem> = new EventEmitter<GroupsTreeItem>();

}