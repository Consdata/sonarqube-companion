import {ChangeDetectionStrategy, Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'sqc-select',
  template: `
    <sqc-frame [label]="label">
      <div class="wrapper">
        <input [value]="value ?? ''"
               (change)="onValueChange($event)"/>
        <mat-icon>expand_more</mat-icon>
      </div>
    </sqc-frame>
  `,
  styleUrls: ['./ui-select.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class UiSelectComponent {

  @Input()
  value?: string;

  @Input()
  label = '';

  @Output()
  valueChange: EventEmitter<string> = new EventEmitter<string>();

  onValueChange(event: Event): void {
    this.valueChange.emit((<HTMLInputElement>event.target).value);
  }
}
