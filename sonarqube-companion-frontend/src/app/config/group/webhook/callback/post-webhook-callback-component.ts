import {Component, EventEmitter, Input, Output} from '@angular/core';
import {PostWebhookCallback} from '../../../model/webhook-definition';

@Component({
  selector: 'sq-post-webhook-callback',
  template: `
    <div class="element">
      <label class="sq-setting-label">Url</label>
      <input [ngModel]="callback.url" (ngModelChange)="onUrlChange($event)" type="text"/>
    </div>
    <sq-no-improvement-action-callback-params [data]="callback.body"
                                              (dataChange)="onDataChange($event)"></sq-no-improvement-action-callback-params>
  `
})
export class PostWebhookCallbackComponent {

  @Input()
  callback: PostWebhookCallback;

  @Output()
  callbackChange: EventEmitter<PostWebhookCallback> = new EventEmitter();

  onUrlChange(url: string) {
    this.callback.url = url;
    this.callbackChange.emit(this.callback);
  }

  onDataChange(data: { [key: string]: any }) {
    this.callback.body = data;
    this.callbackChange.emit(this.callback);
  }
}
