import {Component, Input} from '@angular/core';
import {GroupsTreeItem} from './groups-tree-item';

@Component({
  selector: 'sqc-groups-tree-item',
  template: `
    <div class="item" [ngClass]="item.healthStatus">
      {{item.name}}
    </div>
  `,
  styleUrls: ['./groups-tree-item.component.scss']
})
export class GroupsTreeItemComponent {
  @Input()
  item!: GroupsTreeItem;
}
