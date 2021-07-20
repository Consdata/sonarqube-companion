import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {GroupsTreeItem} from './groups-tree-item';

@Component({
  selector: 'sqc-side-groups-tree-item',
  template: `
    <mat-list-item>
      <div class="item">
        {{item.name}}
      </div>
      <div class="status-indicator" [ngClass]="item.healthStatus"></div>
    </mat-list-item>
  `,
  styleUrls: ['./groups-tree-item.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GroupsTreeItemComponent {

  @Input()
  item!: GroupsTreeItem;

}
