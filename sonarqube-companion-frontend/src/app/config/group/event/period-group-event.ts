import {Component, EventEmitter, Input, Output} from '@angular/core';


@Component({
  selector: `period-group-event`,
  template: `
    <div *ngIf="_data">
      <div class="element">
        <label class="sq-setting-label">Start date</label>
        <input type="text" [ngModel]="_data.startDate" (ngModelChange)="onStartDateChange($event)"/>
      </div>
      <div class="element">
        <label class="sq-setting-label">End date</label>
        <input type="text" [ngModel]="_data.endDate" (ngModelChange)="onEndDateChange($event)"/>
      </div>
    </div>
  `
})
export class PeriodGroupEvent {
  @Output()
  dataChange: EventEmitter<{ [key: string]: any }> = new EventEmitter();

  _data: { [key: string]: any };

  @Input()
  set data(input: { [key: string]: any }) {
    if (input && (input.startDate || input.endDate)) {
      this._data = input;
    } else {
      this._data = {startDate: '', endDate: ''};
    }
  }

  onStartDateChange(startDate: string) {
    this._data.startDate = startDate;
    this.dataChange.emit(this._data);
  }


  onEndDateChange(endDate: string) {
    this._data.endDate = endDate;
    this.dataChange.emit(this._data);
  }
}
