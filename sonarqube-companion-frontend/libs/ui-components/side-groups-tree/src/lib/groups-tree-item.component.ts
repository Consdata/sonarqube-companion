import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {GroupLightModel} from '@sonarqube-companion-frontend/group-overview';

@Component({
  selector: 'sqc-side-groups-tree-item',
  template: `
    <mat-list-item>
      <div class="item">
        {{item.name}}
      </div>
    </mat-list-item>
  `,
  styleUrls: ['./groups-tree-item.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GroupsTreeItemComponent {

  @Input()
  item!: GroupLightModel;

}
