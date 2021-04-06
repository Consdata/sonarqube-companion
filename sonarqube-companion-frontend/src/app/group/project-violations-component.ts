import {Component, Input} from '@angular/core';
import {Violations} from '../violations/violations';

@Component({
  selector: 'sq-project-violations',
  template: `
    <ng-template #violationsTpl>
      <span>{{violations[type]}}</span>
    </ng-template>
    <ng-template #violationsDiffTpl>
   <span>
      (
        <span
          *ngIf="isLoaded()"
          [class.violations-down]="violationsDiff[type] <= 0"
          [class.violations-up]="violationsDiff[type] > 0">
            <ng-container *ngIf="!detailedDiff">{{violationsDiff[type]}}</ng-container>
            <ng-container *ngIf="detailedDiff">+{{addedViolations[type]}} -{{removedViolations[type]}}</ng-container>
        </span>
        <span
          *ngIf="!isLoaded()">
          <i class="fa fa-refresh fa-spin"></i>
        </span>
      )
    </span>
    </ng-template>

    <a target="_blank" rel="noopener noreferrer" [href]="violationsLink" *ngIf="violationsLink; else violationsTpl">
      <ng-container *ngTemplateOutlet="violationsTpl"></ng-container>
    </a>
    <a target="_blank" rel="noopener noreferrer" [href]="violationsDiffLink" *ngIf="violationsDiffLink; else violationsDiffTpl">
      <ng-container *ngTemplateOutlet="violationsDiffTpl"></ng-container>
    </a>
  `
})
export class ProjectViolationsComponent {

  @Input() type: string;
  @Input() violations: Violations;
  @Input() violationsDiff: Violations;
  @Input() addedViolations: Violations;
  @Input() removedViolations: Violations;
  @Input() detailedDiff = false;
  @Input() violationsLink: string;
  @Input() violationsDiffLink: string;


  isLoaded(): boolean {
    if (this.detailedDiff) {
      return !!this.violations && !!this.violationsDiff && !!this.addedViolations && !!this.removedViolations;
    } else {
      return !!this.violations && !!this.violationsDiff;
    }
  }

}
