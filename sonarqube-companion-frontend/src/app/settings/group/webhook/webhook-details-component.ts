import {SettingsListDetailsItem} from '../../common/settings-list-item';
import {RestWebhookTrigger, WebhookAction, WebhookCallback, WebhookDefinition} from '../../model/webhook-definition';
import {Component, Type} from '@angular/core';
import {Subject} from 'rxjs/index';
import {ValidationResult} from '../../common/settings-list-component';
import {WebhookCallbackDetailComponent} from './callback/webhook-callback-detail-component';
import {GroupSettingsService} from '../../service/group-settings-service';

@Component({
  selector: `sq-webhook-detail`,
  template: `
    <div class="sq-settings-detail">
      <div class="element">
        <label class="sq-setting-label">Webhook name</label>
        <input type="text" [(ngModel)]="model.name"/>
      </div>
      <hr/>
      <div class="element">
        <label class="sq-setting-label">Webhook action type</label>
        <select [(ngModel)]="model.action.type">
          <option value="NO_IMPROVEMENT">NO_IMPROVEMENT</option>
        </select>
      </div>
      <sq-no-improvement-action *ngIf="model.action.type === 'NO_IMPROVEMENT'"
                                [model]="model.action"></sq-no-improvement-action>
      <hr/>
      <div class="element">
        <label class="sq-setting-label">Webhook trigger</label>
        <select [(ngModel)]="model.trigger.type">
          <option value="CRON">CRON</option>
          <option value="REST">REST</option>
        </select>
      </div>
      <sq-cron-webhook-trigger *ngIf="model.trigger.type === 'CRON'"
                               [(model)]="model.trigger"></sq-cron-webhook-trigger>
      <sq-rest-webhook-trigger *ngIf="model.trigger.type === 'REST'"
                               [(model)]="model.trigger"></sq-rest-webhook-trigger>
      <hr/>
      <div class="element">
        <sq-spinner *ngIf="!loaded"></sq-spinner>
        <sq-settings-list [details]="webhookDetailsType"
                          [(data)]="model.callbacks"
                          [title]="'Callbacks'"
                          [loaded]="loaded"
                          [foldedListLabel]="'%number% more callbacks defined'"
                          [newItem]="newItem.asObservable()"
                          [validation]="validation.asObservable()"
                          [label]="getLabel"
                          (addClick)="addCallback()"
                          (removeItem)="removeCallback($event)"
                          (saveItem)="saveCallback($event)"
        ></sq-settings-list>
      </div>
    </div>
  `
})
export class WebhookDetailsComponent implements SettingsListDetailsItem {
  model: WebhookDefinition;
  metadata: any;
  loaded: boolean = true;
  webhookDetailsType: Type<SettingsListDetailsItem> = WebhookCallbackDetailComponent;


  newItem: Subject<WebhookCallback> = new Subject<WebhookCallback>();
  validation: Subject<ValidationResult> = new Subject<ValidationResult>();

  constructor(private  settingsService: GroupSettingsService) {
  }

  setModel(model: WebhookDefinition): void {
    this.model = model;
    if (!model.action) {
      this.model.action = new WebhookAction({type: 'NO_IMPROVEMENT'});
    }
    if (!model.trigger) {
      this.model.trigger = new RestWebhookTrigger({type: 'REST'});
    }
    if (!model.callbacks) {
      this.model.callbacks = [];
    }
  }

  setMetadata(data: any): void {
    this.metadata = data;
  }

  getModel(): any {
    return this.model;
  }

  getTitle(): string {
    return this.model.name;
  }


  addCallback(): void {
    const callback: WebhookCallback = new WebhookCallback({type: 'POST'});
    this.model.callbacks.push(callback);
    this.newItem.next(callback);
  }

  removeCallback(event: any): void {
    this.loaded = false;
    this.settingsService.deleteCallback(this.metadata.groupUuid, this.model.uuid, event.uuid).subscribe(validationResult => {
      if (validationResult.valid) {
        this.load();
      } else {
        this.loaded = true;
      }
      validationResult.item = event;
      this.validation.next(validationResult);

    });
  }

  saveCallback(event: any): void {
    this.loaded = false;
    this.settingsService.saveCallback(this.metadata.groupUuid, this.model.uuid, event.item, event.newItem).subscribe(validationResult => {
      if (validationResult.valid) {
        this.load();
      } else {
        this.loaded = true;
      }
      validationResult.item = event.item;
      this.validation.next(validationResult);
    });
  }

  getLabel(item: WebhookDefinition): string {
    return item.name;
  }

  load() {
    this.loaded = false;
    this.settingsService.getCallbacks(this.metadata.groupUuid, this.model.uuid).subscribe(data => {
      this.model.callbacks = data;
      this.loaded = true;
    });
  }
}
