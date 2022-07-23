import {Component, Input} from '@angular/core';

@Component({
  selector: 'sqc-button',
  template: `
    <button mat-ripple [class.warn]="warn">
      <ng-content></ng-content>
    </button>`,
  styleUrls: ['./ui-button.component.scss'],
})
export class UiButtonComponent {

  @Input()
  warn = false;


}
