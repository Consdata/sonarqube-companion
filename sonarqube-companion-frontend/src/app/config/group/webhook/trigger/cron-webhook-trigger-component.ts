import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CronWebhookTrigger} from '../../../model/webhook-definition';

@Component({
  selector: 'sq-cron-webhook-trigger',
  template: `
    <div class="element">
      <label class="sq-setting-label">Definition</label>
      <input type="text" [ngModel]="_model.definition" (ngModelChange)="onDefinitionChange($event)"/>
    </div>
  `
})
export class CronWebhookTriggerComponent {
  @Output() modelChange: EventEmitter<CronWebhookTrigger> = new EventEmitter();

  _model: CronWebhookTrigger;

  @Input()
  set model(data: any) {
    this._model = new CronWebhookTrigger(data);
    this.modelChange.emit(this._model);
  }

  onDefinitionChange(definition: string) {
    this._model.definition = definition;
    this.modelChange.emit(this._model);
  }
}
