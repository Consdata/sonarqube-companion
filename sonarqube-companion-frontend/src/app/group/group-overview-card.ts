import {Component, Input} from '@angular/core';

@Component({
  selector: 'sq-group-overview-card',
  template: `
    <div>
      <div class="overview-card">
        <div class="icon">
            <i [class]="icon"></i>
        </div>
        <div class="details">
            <div class="value">{{value}}</div>
            <div class="metric">{{metric}}</div>
        </div>
      </div>
    </div>
  `
})
export class GroupOverviewCardComponent {

  @Input() icon: string;
  @Input() metric: string;
  @Input() value: string;

}
