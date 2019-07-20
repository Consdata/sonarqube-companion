import {Component, Input, Type} from '@angular/core';
import {ValidationResult} from '../../common/settings-list-component';
import {SettingsListDetailsItem} from '../../common/settings-list-item';
import {Subject} from 'rxjs/index';
import {WebhookDefinition} from '../../model/webhook-definition';
import {WebhookDetailsComponent} from './webhook-details-component';
import * as uuid from 'uuid';
import {GroupSettingsService} from '../../service/group-settings-service';

@Component({
  selector: 'webhooks-list',
  template: `
    <div class="sq-settings-container">
      <sq-spinner *ngIf="!loaded"></sq-spinner>
      <sq-settings-list
        [loaded]="loaded"
        [details]="webhookDetailsType"
        [(data)]="webhooks"
        [metadata]="{groupUuid: this._uuid}"
        [title]="'Webhooks'"
        [foldedListLabel]="'%number% more webhooks defined'"
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
export class WebhooksListComponent {
  loaded: boolean = false;
  _uuid: string;
  @Input()
  set uuid(id: string) {
    this._uuid = id;
    this.load();
  }

  webhooks: WebhookDefinition[] = [];

  webhookDetailsType: Type<SettingsListDetailsItem> = WebhookDetailsComponent;


  newItem: Subject<WebhookDefinition> = new Subject<WebhookDefinition>();
  validation: Subject<ValidationResult> = new Subject<ValidationResult>();

  constructor(private settingsService: GroupSettingsService) {
  }

  addEvent(): void {
    const webhook: WebhookDefinition = new WebhookDefinition({uuid: uuid.v4()});
    this.webhooks.push(webhook);
    this.newItem.next(webhook);
  }

  removeEvent(webhook: WebhookDefinition): void {
    this.loaded = false;
    this.settingsService.deleteWebhook(this._uuid, webhook.uuid).subscribe(validationResult => {
      if (validationResult.valid) {
        this.load();
      } else {
        this.loaded = true;
      }
      validationResult.item = webhook;
      this.validation.next(validationResult);

    });
  }

  saveEvent(event: any): void {
    this.loaded = false;
    this.settingsService.saveWebhook(this._uuid, event.item, event.newItem).subscribe(validationResult => {
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
    this.settingsService.getWebhooks(this._uuid).subscribe(data => {
      this.webhooks = data;
      this.loaded = true;
    });
  }

}
