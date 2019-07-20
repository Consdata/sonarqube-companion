import {Component, EventEmitter, Input, Output, Type} from '@angular/core';
import {SettingsListDetailsItem} from './settings-list-item';
import {Observable, Subscription} from 'rxjs/index';

export interface ValidationResult {
  item?: any;
  message?: string;
  valid: boolean;
}

@Component({
  selector: `sq-settings-list`,
  template: `
    <div *ngIf="loaded">
      <div class="header">
        <div class="sq-settings-group-title">
          <div class="title">{{title}}</div>
          <button class="add" (click)="onAddClick()" [disabled]="selectedItem">Add</button>
        </div>
        <hr>
      </div>
      <div *ngIf="_data">
        <div class="folded" *ngIf="_data.length > foldFrom" (click)="onFold()">
          <div class="sq-setting-label label">
            <div>
              {{getFoldedLabel()}}
            </div>
            <div>
              <div class="actions">
                <div class="action">
                  <div *ngIf="_data.length > foldFrom" class="unfold">
                    <i class="fa fa-arrow-circle-down" *ngIf="folded"></i>
                    <i class="fa fa-arrow-circle-up" *ngIf="!folded"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div *ngFor="let item of _data; let i = index">
          <div class="item" *ngIf="_data && (i >= _data.length - foldFrom || _data.length <= foldFrom || !folded)"
               (click)="select(item)">
            <div class="description" *ngIf="selectedItem !== item">{{label ? label(item) : item.id}}</div>
            <div
              *ngIf="selectedItem === item">
              <div class="header sq-setting-label label" onclick="focus()" #element>
                <div class="description" (click)="onSaveItem()">{{label ? label(item) : item.id}}</div>
                <div class="actions">
                  <div class="action save" (click)="onSaveItem()"><i class="fa fa-save"></i></div>
                  <div class="action remove" (click)="onRemoveItem(item)"><i class="fa fa-trash"></i></div>
                  <div class="action cancel" (click)="onCancel($event)"><i class="fa fa-remove"></i></div>
                </div>
              </div>
              <div>
                <div class="sq-setting-error">{{errorMessage}}</div>
                <sq-settings-list-details [model]="item" [details]="detailsType"
                                          [metadata]="metadata"></sq-settings-list-details>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `

})
export class SettingsListComponent {

  detailsType: Type<SettingsListDetailsItem>;
  @Input()
  loaded: boolean = true;

  @Input()
  set newItem(newItemObservable: Observable<any>) {
    if (this.newItemSubscription) {
      this.newItemSubscription.unsubscribe();
    }
    this.newItemSubscription = newItemObservable.subscribe(item => this.onNewItem(item))
  }

  @Input()
  set validation(validationObservable: Observable<ValidationResult>) {
    if (this.validationSubscription) {
      this.validationSubscription.unsubscribe();
    }
    this.validationSubscription = validationObservable.subscribe(validationResult => this.onError(validationResult));
  }

  validationSubscription: Subscription;
  newItemSubscription: Subscription;

  selectedItem: any;
  isSelectedItemNew: boolean = false;
  errorMessage: string;
  folded: boolean = true;

  @Input()
  metadata: any;

  @Input()
  set data(items: any[]) {
    this.selectedItem = undefined;
    this.isSelectedItemNew = false;
    this.errorMessage = '';
    this._data = items;
    this._dataOld = JSON.parse(JSON.stringify(this._data));
  }

  @Output()
  dataChange: EventEmitter<any[]> = new EventEmitter();

  _data: any[] = [];
  _dataOld: any[] = [];

  @Input()
  foldedListLabel: string;

  @Input()
  label: (any) => string;

  @Input()
  foldFrom: number = 10;

  @Input()
  title: string;

  @Input()
  set details(type: Type<SettingsListDetailsItem>) {
    if (type) {
      this.detailsType = type;
    }
  }

  @Output()
  addClick: EventEmitter<void> = new EventEmitter<void>();

  @Output()
  saveItem: EventEmitter<any> = new EventEmitter<any>();

  @Output()
  removeItem: EventEmitter<any> = new EventEmitter<any>();

  getFoldedLabel(): string {
    if (this.folded) {
      return this.foldedListLabel.replace('%number%', (this._data.length - this.foldFrom).toString());
    } else {
      return 'Hide';
    }
  }

  onAddClick(): void {
    this.addClick.emit();
  }

  onNewItem(item: any): void {
    this.selectedItem = item;
    this.isSelectedItemNew = true;
  }

  onFold(): void {
    this.folded = !this.folded;
    if (this.folded) {
      this.selectedItem = undefined;
      this.isSelectedItemNew = false;
    }
  }

  onCancel(event: MouseEvent) {
    event.stopPropagation();
    this._data = JSON.parse(JSON.stringify(this._dataOld));
    this.dataChange.emit(this._data);
    this.errorMessage = '';
    this.selectedItem = undefined;
    this.isSelectedItemNew = false;
  }

  onSaveItem(): void {
    this.saveItem.emit({item: this.selectedItem, newItem: this.isSelectedItemNew});
  }

  onRemoveItem(item: any): void {
    this.removeItem.emit(item);
  }

  select(item: any): void {
    if (!this.selectedItem) {
      this.selectedItem = item;
    }
  }

  onError(validationResult: ValidationResult): void {
    if (!validationResult.valid) {
      this.selectedItem = validationResult.item;
      this.errorMessage = validationResult.message;
    } else {
      this.errorMessage = '';
      this.selectedItem = undefined;
      this.isSelectedItemNew = false;
    }
  }

}
