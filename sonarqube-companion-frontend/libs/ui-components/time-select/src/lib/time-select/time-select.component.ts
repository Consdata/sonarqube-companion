import {AfterViewInit, Component, EventEmitter, Output} from '@angular/core';
import {MatDatepickerInputEvent} from '@angular/material/datepicker';

// TODO weryfikacja dat

export interface DateRange {
  from: Date;
  to: Date;
  fromString: string;
  toString: string
}

@Component({
  selector: 'sqc-time-select',
  template: `
    <div class="wrapper">
      <button mat-flat-button class="dateBtn" (click)="fromPicker.open()">{{range.fromString}} </button>
      <input [matDatepicker]="fromPicker" class="date" (dateChange)="fromDateChanged($event)">
      <div class="separator">-</div>
      <button mat-flat-button class="dateBtn" (click)="toPicker.open()">{{range.toString}}</button>
      <input [matDatepicker]="toPicker" class="date" (dateChange)="toDateChanged($event)">
      <mat-datepicker #fromPicker></mat-datepicker>
      <mat-datepicker #toPicker></mat-datepicker>
    </div>
  `,
  styleUrls: ['./time-select.component.scss']
})
export class TimeSelectComponent implements AfterViewInit {
  @Output()
  rangeChanged: EventEmitter<DateRange> = new EventEmitter<DateRange>();

  range: DateRange = {
    from: TimeSelectComponent.dateMinusDays(new Date(), 31),
    to: TimeSelectComponent.dateMinusDays(new Date(), 1),
    fromString: TimeSelectComponent.toLocalString(TimeSelectComponent.dateMinusDays(new Date(), 31)),
    toString: TimeSelectComponent.toLocalString(TimeSelectComponent.dateMinusDays(new Date(), 1)),
  }

  public static dateMinusDays(date: Date, days: number): Date {
    date.setDate(date.getDate() - days);
    return date;
  }

  public static toLocalString(date: Date) {
    const month = `${date.getMonth() + 1}`.padStart(2, '0');
    const day = `${date.getDate()}`.padStart(2, '0');
    return `${day}-${month}-${date.getFullYear()}`
  }

  ngAfterViewInit(): void {
    this.rangeChanged.emit(this.range);
  }

  fromDateChanged($event: MatDatepickerInputEvent<Date>): void {
    this.range.from = $event.value || TimeSelectComponent.dateMinusDays(new Date(), 31);
    this.range.fromString = TimeSelectComponent.toLocalString(this.range.from);
    this.rangeChanged.emit({...this.range});
  }

  toDateChanged($event: MatDatepickerInputEvent<Date>): void {
    this.range.to = $event.value || TimeSelectComponent.dateMinusDays(new Date(), 1);
    this.range.toString = TimeSelectComponent.toLocalString(this.range.to);
    this.rangeChanged.emit({...this.range});
  }
}

export const DEFAULT_DATE_RANGE: DateRange = {
  from: TimeSelectComponent.dateMinusDays(new Date(), 30),
  to: TimeSelectComponent.dateMinusDays(new Date(), 1),
  fromString: TimeSelectComponent.toLocalString(TimeSelectComponent.dateMinusDays(new Date(), 30)),
  toString: TimeSelectComponent.toLocalString(TimeSelectComponent.dateMinusDays(new Date(), 1)),
}
