import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Observable} from 'rxjs';
import {OverviewService} from '../overview.service';
import {GroupOverview} from '@sonarqube-companion-frontend/group-overview';
import {GroupsTreeItem} from '../../../../ui-components/groups-tree/src/lib/groups-tree-item';

@Component({
  selector: 'sqc-overview',
  template: `
    <div class="sqc-overview">
      <div class="top-bar">
        <span>overview</span>
      </div>
      <mat-divider></mat-divider>
      <div class="wrapper">
        <sqc-group-overview [group]="selectedGroup"></sqc-group-overview>
      </div>
    </div>
  `,
  styleUrls: ['./overview.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OverviewComponent {
  @Input()
  selectedGroup!: GroupOverview;
}
