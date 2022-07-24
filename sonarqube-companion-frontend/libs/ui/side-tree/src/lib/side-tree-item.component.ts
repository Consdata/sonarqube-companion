import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {SideTreeItem} from './side-tree-item';


@Component({
  selector: 'sqc-side-tree-item',
  template: `
    <ng-container *ngIf="item">
      <div class="item" mat-ripple>{{item.label}}</div>
    </ng-container>
  `,
  styleUrls: ['./side-tree-item.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class SideTreeItemComponent {

  @Input()
  item?: SideTreeItem;

}
