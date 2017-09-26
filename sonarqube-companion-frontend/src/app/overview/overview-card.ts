import {Component, Input} from '@angular/core';

@Component({
  selector: 'sq-overview-card',
  template: `
    <div>
      <div class="overview-card">
        <div class="icon">
            <i [class]="icon"></i>
        </div>
        <div class="details">
            <div class="text">
              <ng-content select="[text]"></ng-content>
            </div>
            <div class="description">
              <ng-content select="[description]"></ng-content>
            </div>
        </div>
      </div>
    </div>
  `
})
export class GroupOverviewCardComponent {

  @Input() icon: string;

}
