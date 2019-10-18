import {Component} from '@angular/core';
import {SettingsListDetailsItem} from '../../common/settings-list/settings-list-item';
import {GroupEvent} from '../../../group/group-event';

@Component({
  selector: `event-detail`,
  template: `
    <div class="sq-settings-detail">
      <div class="element">
        <label class="sq-setting-label">Event name</label>
        <input type="text" [(ngModel)]="model.name"/>
      </div>
      <div class="element">
        <label class="sq-setting-label">Event description</label>
        <input type="text" [(ngModel)]="model.description"/>
      </div>

      <div class="element">
        <label class="sq-setting-label">Event type</label>
        <select [(ngModel)]="model.type">
          <option value="date">Date</option>
          <option value="period">Period</option>
        </select>
      </div>
      <div class="element">
        <date-group-event *ngIf="model.type==='date'"
                          [(data)]="model.data"></date-group-event>
        <period-group-event *ngIf="model.type==='period'"
                            [(data)]="model.data"></period-group-event>
      </div>

    </div>
  `

})
export class EventDetailsComponent implements SettingsListDetailsItem {

  model: GroupEvent;

  setModel(model: any): void {
    this.model = model;
  }

  setMetadata(data: any): void {
  }

  getModel(): any {
    return this.model;
  }

  getTitle(): string {
    return this.model.name;
  }
}
