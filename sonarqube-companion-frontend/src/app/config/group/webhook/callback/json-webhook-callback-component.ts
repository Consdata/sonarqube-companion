import {Component, Input} from '@angular/core';
import {JSONWebhookCallback} from '../../../model/webhook-definition';

@Component({
  selector: 'sq-json-webhook-callback',
  template: `
    <sq-no-improvement-action-callback-params [(data)]="callback.body"></sq-no-improvement-action-callback-params>
  `
})
export class JsonWebhookCallbackComponent {

  @Input()
  callback: JSONWebhookCallback;
}
