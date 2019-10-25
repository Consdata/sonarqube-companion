import {Component, Input, Type} from '@angular/core';
import {GroupEvent} from '../../../group/group-event';
import {Subject} from 'rxjs';
import {ValidationResult} from '../../common/settings-list/settings-list-component';
import {SettingsListDetailsItem} from '../../common/settings-list/settings-list-item';
import {EventDetailsComponent} from './event-details-component';
import {GroupSettingsService} from '../../service/group-settings-service';

@Component({
  selector: `sq-settings-event-component`,
  template: `
    <div class="sq-settings-container">
      <sq-spinner *ngIf="!loaded"></sq-spinner>
      <sq-settings-list [details]="eventDetailsType"
                        [(data)]="events"
                        [loaded]="loaded"
                        [title]="'Events'"
                        [foldedListLabel]="'%number% more events defined'"
                        [newItem]="newItem.asObservable()"
                        [validation]="validation.asObservable()"
                        [label]="getLabel"
                        (addClick)="addEvent()"
                        (removeItem)="removeEvent($event)"
                        (saveItem)="saveEvent($event)"
      ></sq-settings-list>
    </div>
  `
})
export class EventComponent {
  loaded: boolean = false;
  events: GroupEvent[] = [];
  eventDetailsType: Type<SettingsListDetailsItem> = EventDetailsComponent;
  newItem: Subject<GroupEvent> = new Subject<GroupEvent>();
  validation: Subject<ValidationResult> = new Subject<ValidationResult>();

  constructor(private settingsService: GroupSettingsService) {
  }

  _uuid: string;

  @Input()
  set uuid(id: string) {
    this._uuid = id;
    this.load();
  }

  addEvent(): void {
    const event: GroupEvent = new GroupEvent();
    this.events.push(event);
    this.newItem.next(event);
  }

  removeEvent(event: GroupEvent): void {
    this.loaded = false;
    this.settingsService.deleteEvent(this._uuid, event.uuid).subscribe(validationResult => {
      if (validationResult.valid) {
        this.load();
      } else {
        this.loaded = true;
      }
      validationResult.item = event;
      this.validation.next(validationResult);

    }, er => {
      this.loaded = true;
    });
  }

  saveEvent(event: any): void {
    this.loaded = false;
    this.settingsService.saveEvent(this._uuid, event.item, event.newItem).subscribe(validationResult => {
      if (validationResult.valid) {
        this.load();
      } else {
        this.loaded = true;
      }
      validationResult.item = event.item;
      this.validation.next(validationResult);
    }, er => {
      this.loaded = true;
    });
  }

  getLabel(item: GroupEvent): string {
    return item.name;
  }

  load() {
    this.loaded = false;
    this.settingsService.getEvents(this._uuid).subscribe(data => {
      this.events = data;
      this.loaded = true;
    });
  }
}
