import {Component} from '@angular/core';
import {Observable} from 'rxjs';
import {OverviewService} from '../overview.service';
import {GroupOverview} from '@sonarqube-companion-frontend/ui-components/group-overview';

@Component({
  selector: 'sqc-overview',
  template: `
    <div class="sqc-overview">
      <div class="top-bar">
        <span>overview</span>
      </div>
      <div class="wrapper">
        <sqc-groups-tree-wrapper [rootGroup]="rootGroup$"></sqc-groups-tree-wrapper>
        <mat-divider [vertical]="true"></mat-divider>
        <sqc-group-overview></sqc-group-overview>
      </div>
    </div>
  `,
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent {
  rootGroup$: Observable<GroupOverview> = this.overviewService.overview();

  constructor(private overviewService: OverviewService) {
  }


}
