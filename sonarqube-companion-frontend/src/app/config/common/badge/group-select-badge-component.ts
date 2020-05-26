import {AfterViewInit, Component, EventEmitter, Input, Output} from '@angular/core';
import {GroupSettingsService} from '../../service/group-settings-service';
import {GroupLightModel} from '../../model/group-light-model';

@Component({
  selector: `sq-settings-group-badge`,
  template: `
    <div class="header">
      <label class="sq-setting-label">{{title}}</label>
    </div>
    <div>
      <div class="container">
        <div class="badge" *ngFor="let item of _items; let i = index"
             (click)="selectedBadge=i">
          <div *ngIf="selectedBadge !== i">{{item.name}}</div>
          <div class="editable" *ngIf="selectedBadge === i">
            <select (change)="onSelect($event, i)" [value]="item.uuid">
              <option *ngFor="let group of currantDomain" [value]="group.uuid">{{group.name}}</option>
            </select>
            <button (mousedown)="removeBadge(i)">X</button>
          </div>
        </div>
        <button *ngIf="currantDomain.length != 0" class="add" (click)="addBadge()">Add</button>
      </div>
    </div>`
})
export class GroupSelectBadgeComponent implements AfterViewInit {

  domain: GroupLightModel[] = [];
  currantDomain: GroupLightModel[] = [];
  @Output()
  itemsChange: EventEmitter<GroupLightModel[]> = new EventEmitter();
  @Input()
  title: string;
  selectedBadge: number = -1;

  constructor(private groupSettingsService: GroupSettingsService) {
  }

  _items: GroupLightModel[] = [];

  @Input()
  set items(data: GroupLightModel[]) {
    if (data) {
      this._items = data;
    }
  }

  ngAfterViewInit(): void {
    this.groupSettingsService.getAll().subscribe(data => {
      this.domain = data;
      this.currantDomain = this.domain.filter(d => this._items.filter(i => i.uuid === d.uuid).length === 0);
    });
  }

  addBadge(): void {
    this._items.push({name: '', uuid: ''});
    this.selectedBadge = this._items.length - 1;
    this.itemsChange.emit(this._items);
  }

  removeBadge(i: number): void {
    this._items.splice(i, 1);
    this.itemsChange.emit(this._items);
    this.selectedBadge = -1;
  }

  onSelect(event: any, i: number) {
    this._items[i] = this.domain.filter(item => item.uuid === event.target.value)[0];
    this.currantDomain = this.domain.filter(d => this._items.filter(item => item.uuid === d.uuid).length === 0);
    this.addBadge();
  }
}
