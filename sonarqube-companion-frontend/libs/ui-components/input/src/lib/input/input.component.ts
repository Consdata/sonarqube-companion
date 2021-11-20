import {ChangeDetectionStrategy, Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'sqc-input',
  template: `
    <ng-container *ngIf="!disabled">
      <label *ngIf="label">{{label}}</label>
      <input [value]="value" (change)="onValueChange($event)"/>
    </ng-container>
    <ng-container *ngIf="disabled">
      <label *ngIf="label">{{label}}</label>
      <span>{{value}}</span>
    </ng-container>
  `,
  styleUrls: ['./input.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class InputComponent {
  @Input()
  value: string = '';
  @Input()
  label: string = '';
  @Input()
  disabled: boolean = false;
  @Output()
  valueChange: EventEmitter<string> = new EventEmitter<string>();

  onValueChange(event: Event): void {
    this.valueChange.emit((<HTMLInputElement>event.target).value)
  }
}
