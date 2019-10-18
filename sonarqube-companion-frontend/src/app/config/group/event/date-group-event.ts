import {Component, EventEmitter, Input, Output} from '@angular/core';


@Component({
  selector: `date-group-event`,
  template: `
    <div *ngIf="_data">
      <div class="element">
        <label class="sq-setting-label">Date</label>
        <input type="text" [ngModel]="_data.date" (ngModelChange)="onDataChange($event)"/>
      </div>
    </div>`
})
export class DateGroupEvent {
  @Output()
  dataChange: EventEmitter<{ [key: string]: any }> = new EventEmitter();

  _data: { [key: string]: any };

  @Input()
  set data(input: { [key: string]: any }) {
    if (input && input.date) {
      this._data = input;
    } else {
      this._data = {date: ''};
    }
  }

  onDataChange(date: string) {
    this._data.date = date;
    this.dataChange.emit(this._data);
  }
}
