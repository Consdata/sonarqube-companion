import {ChangeDetectionStrategy, Component, Input} from '@angular/core';

@Component({
  selector: 'sqc-value-badge',
  template: `
    <div class="badge {{priority}}">
      <div class="content">
        <div class="value">
          <ng-content></ng-content>
        </div>
        <span class="label">{{label}}</span>
        <mat-icon *ngIf="icon">{{icon}}</mat-icon>
      </div>
      <mat-divider vertical *ngIf="suffix"></mat-divider>
      <div class="suffix" *ngIf="suffix">{{suffix}}</div>
    </div>
  `,
  styleUrls: ['./value-badge.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ValueBadgeComponent {
  @Input()
  label: string = '';
  @Input()
  priority: string = '';
  @Input()
  icon?: string;
  @Input()
  suffix?: string;
}
