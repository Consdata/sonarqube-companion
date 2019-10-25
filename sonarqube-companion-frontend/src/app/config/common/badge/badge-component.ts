import {Component, EventEmitter, Input, Output} from '@angular/core';


@Component({
  selector: `sq-settings-badge`,
  template: `
    <div class="header">
      <label class="sq-setting-label">{{title}}</label>
    </div>
    <div>
      <div class="container">
        <div class="badge" *ngFor="let item of _items; let i = index"
             (click)="selectedBadge=i">
          <div *ngIf="selectedBadge !== i">{{item}}</div>
          <div class="editable" *ngIf="selectedBadge === i">
            <input autoFocus type="text" [(ngModel)]="item" (keydown)="onKeyDown($event, i, item)"
                   (blur)="onBlur($event, i, item)">
            <button (mousedown)="removeBadge(i)">X</button>
          </div>
        </div>
        <button class="add" (click)="addBadge()">Add</button>
      </div>
    </div>`
})
export class BadgeComponent {
  @Output()
  itemsChange: EventEmitter<string[]> = new EventEmitter();
  @Input()
  title: string;
  selectedBadge: number = -1;

  _items: string[];

  @Input()
  set items(data: string[]) {
    if (data) {
      this._items = data;
    }
  }

  addBadge(): void {
    this._items.push('');
    this.selectedBadge = this._items.length - 1;
    this.itemsChange.emit(this._items);
  }

  removeBadge(i: number): void {
    this._items.splice(i, 1);
    this.itemsChange.emit(this._items);
    this.selectedBadge = -1;
  }

  onKeyDown(event, i, item) {
    if (event.keyCode == 13) {
      this.onEnter(event, i, item);
    }
    if (event.keyCode == 27) {
      this.onEscape(event, i, item);
    }
    this.itemsChange.emit(this._items);
  }

  onEnter(event, i, item) {
    this._items[i] = item;
    this._items.push('');
    event.target.value = '';
    this.selectedBadge = this._items.length - 1;
  }

  onEscape(event, i, item) {
    if (event.target.value) {
      this._items[i] = item;
      event.target.value = '';
      this.itemsChange.emit(this._items);
    } else {
      this._items.splice(i, 1);
      this.itemsChange.emit(this._items);
    }
    this.selectedBadge = -1;
  }
}
