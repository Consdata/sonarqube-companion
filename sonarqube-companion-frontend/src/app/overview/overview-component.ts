import {Component, OnInit} from '@angular/core';

import {OverviewService} from './overview-service';
import {GroupOverview} from './group-overview';

@Component({
  selector: 'sq-overview',
  template: `
    <sq-spinner *ngIf="!group"></sq-spinner>
    <div *ngIf="group">
      <sq-overview-tree-item [group]="group"></sq-overview-tree-item>
    </div>
  `
})
export class OverviewComponent implements OnInit {

  group: GroupOverview;

  constructor(private service: OverviewService) {
  }

  ngOnInit(): void {
    this.service
      .getRootGroupOverview()
      .subscribe(group => this.group = group);
  }

}
