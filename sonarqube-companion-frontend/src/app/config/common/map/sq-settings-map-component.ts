import {AfterViewInit, Component, EventEmitter, Input, Output} from '@angular/core';
import {GroupSettingsService} from '../../service/group-settings-service';
import {GroupLightModel} from '../../model/group-light-model';


@Component({
  selector: `sq-settings-map`,
  template: `
    <div *ngIf="loaded">
      <div class="header">
        <div class="sq-settings-group-title">
          <div class="title">{{title}}</div>
          <button class="add" (click)="onAddClick()">Add</button>
        </div>
        <hr>
      </div>
      <div *ngIf="data">
        <div *ngFor="let item of data | keyvalue">
          <input type="text" [value]="item.key" (change)="onKeyChange($event, item.key)">
          <select (change)="onSelect($event, item.key)" [value]="item.value">
            <option *ngFor="let group of domain" [value]="group.uuid" [selected]="group.uuid === data[item.key]">{{group.name}}</option>
          </select>
          <button (click)="onDelete(item.key)">x</button>
        </div>
      </div>
    </div>
  `
})
export class SqSettingsMapComponent implements AfterViewInit {

  @Input()
  data: { [key: string]: string };
  @Output()
  dataChange: EventEmitter<{ [key: string]: string }> = new EventEmitter<{ [p: string]: string }>();
  @Input()
  loaded: boolean = false;
  @Input()
  title: string;

  domain: GroupLightModel[];

  constructor(private groupSettingsService: GroupSettingsService) {
  }

  ngAfterViewInit(): void {
    this.groupSettingsService.getAll().subscribe(data => {
      this.domain = data;
    });
  }

  onAddClick(): void {
    this.data[''] = '';
  }


  onSelect(event: Event, key: string) {
    this.data[key] = (<HTMLSelectElement>event.target).value;
    this.dataChange.emit(this.data);
  }

  onKeyChange(event: Event, key: string) {
    this.data[(<HTMLInputElement>event.target).value] = this.data[key];
    delete this.data[''];
    this.dataChange.emit(this.data);
  }

  onDelete(key: string) {
    delete this.data[key];
    this.dataChange.emit(this.data);
  }
}
