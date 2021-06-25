import {Component} from '@angular/core';

@Component({
  selector: 'sqc-group-overview',
  template: `
    <div class="group">
      <div class="stats">
        <sqc-value-badge></sqc-value-badge>
      </div>
      <div class="timeline">
      </div>
      <div class="activity">
      </div>
    </div>
  `,
  styleUrls: ['./group-overview.component.scss']
})
export class GroupOverviewComponent {
}
