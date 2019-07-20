import {Component, EventEmitter, Input, Output} from '@angular/core';
import {RestWebhookTrigger} from '../../../model/webhook-definition';

@Component({
  selector: 'sq-rest-webhook-trigger',
  template: `
    <div class="element">
      <label class="sq-setting-label">Method</label>
      <select [ngModel]="_model.method" (ngModelChange)="onMethodChange($event)">
        <option value="GET">GET</option>
        <option value="POST">POST</option>
      </select>
    </div>
    <div class="element">
      <label class="sq-setting-label">Endpoint</label>
      <input type="text" [ngModel]="_model.endpoint" (ngModelChange)="onEndpointChange($event)"/>
    </div>
  `
})
export class RestWebhookTriggerComponent {
  _model: RestWebhookTrigger;

  @Input()
  set model(data: any) {
    this._model = new RestWebhookTrigger(data);
    this.modelChange.emit(this._model);
  }

  @Output() modelChange: EventEmitter<RestWebhookTrigger> = new EventEmitter();

  onMethodChange(method: string) {
    this._model.method = method;
    this.modelChange.emit(this._model);
  }

  onEndpointChange(endpoint: string) {
    this._model.endpoint = endpoint;
    this.modelChange.emit(this._model);
  }

}
