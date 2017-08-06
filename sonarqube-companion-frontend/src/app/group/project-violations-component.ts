import {Component, Input} from '@angular/core';
import {Violations} from '../violations/violations';

@Component({
  selector: 'sq-project-violations',
  template: `
    <span>{{violations[type]}}</span>
    <span>
      (
        <span
          *ngIf="violationsDiff"
          [class.violations-down]="violationsDiff[type] <= 0"
          [class.violations-up]="violationsDiff[type] > 0">
          {{violationsDiff[type]}}
        </span>
        <span
          *ngIf="!violationsDiff">
          <i class="fa fa-refresh fa-spin"></i>
        </span>
      )
    </span>
  `
})
export class ProjectViolationsComponent {

  @Input() type: string;
  @Input() violations: Violations;
  @Input() violationsDiff: Violations;

}
