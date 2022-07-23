import {ChangeDetectionStrategy, Component, Input} from '@angular/core';

@Component({
  selector: 'sqc-frame',
  template: `
    <div class="container">
      <div class="frame" [class.error]="error">
        <label>{{label}}</label>
        <ng-content></ng-content>
      </div>
      <span class="error" *ngIf="error">{{ error }}</span>
    </div>
  `,
  styleUrls: ['./ui-frame.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UiFrameComponent {

  @Input()
  label = '';

  @Input()
  error? = '';
}
