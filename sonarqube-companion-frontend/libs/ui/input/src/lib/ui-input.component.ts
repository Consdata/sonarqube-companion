import {ChangeDetectionStrategy, Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'sqc-input',
  template: `
    <sqc-frame [label]="label" [error]="error">
      <input [value]="value ?? ''"
             (change)="onValueChange($event)"/>
    </sqc-frame>
  `,
  styleUrls: ['./ui-input.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class UiInputComponent {

  @Input()
  value?: string;

  @Input()
  label = '';

  @Input()
  error? = '';

  @Output()
  valueChange: EventEmitter<string> = new EventEmitter<string>();

  onValueChange(event: Event): void {
    this.valueChange.emit((<HTMLInputElement>event.target).value);
  }
}
