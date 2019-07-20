import {Component} from '@angular/core';
import {SettingsListDetailsItem} from '../../../common/settings-list-item';
import {JSONWebhookCallback, PostWebhookCallback, WebhookCallback} from '../../../model/webhook-definition';

@Component({
  selector: 'sq-webhook-callback-detail',
  template: `
    <div class="element">
      <label class="sq-setting-label">Webhook callback name</label>
      <input type="text" [(ngModel)]="callback.name"/>
    </div>
    <div class="element">
      <label class="sq-setting-label">Webhook callback</label>
      <select [(ngModel)]="callback.type" (change)="onTypeChange($event)">
        <option value="POST">POST</option>
        <option value="JSON">JSON</option>
      </select>
    </div>
    <sq-json-webhook-callback [callback]="callback" *ngIf="callback.type === 'JSON'"></sq-json-webhook-callback>
    <sq-post-webhook-callback [(callback)]="callback" *ngIf="callback.type === 'POST'"></sq-post-webhook-callback>
  `
})

export class WebhookCallbackDetailComponent implements SettingsListDetailsItem {

  callback: WebhookCallback;

  setModel(model: any): void {
    this.callback = model;
  }

  setMetadata(data: any): void {
  }

  getModel(): any {
    return this.callback;
  }

  getTitle(): string {
    return this.callback.name;
  }


  onTypeChange(event: any) {
    this.callback.type = event.target.value;
    if (event.target.value === 'JSON') {
      this.callback = new JSONWebhookCallback(this.callback);
    } else {
      this.callback = new PostWebhookCallback(this.callback);

    }
  }
}
