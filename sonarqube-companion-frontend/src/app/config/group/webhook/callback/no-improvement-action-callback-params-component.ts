import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'sq-no-improvement-action-callback-params',
  template: `
    <div *ngIf="_data">
      <div class="element">
        <label class="sq-setting-label">Response for 'no_improvement' status</label>
        <input type="text" [ngModel]="_data.no_improvement" (ngModelChange)="onNoImprovementMsgChange($event)"/>
      </div>
      <div class="element">
        <label class="sq-setting-label">Response for 'improvement' status</label>
        <input type="text" [ngModel]="_data.improvement" (ngModelChange)="onImprovementMsgChange($event)"/>
      </div>
      <div class="element">
        <label class="sq-setting-label">Response for 'clean' response</label>
        <input type="text" [ngModel]="_data.clean" (ngModelChange)="onCleanMsgChange($event)"/>
      </div>
    </div>
  `
})

export class NoImprovementActionCallbackParamsComponent {
  @Output()
  dataChange: EventEmitter<{ [key: string]: any }> = new EventEmitter();

  _data: { [key: string]: any };

  @Input()
  set data(input: { [key: string]: any }) {

    if (input && (input.no_improvement || input.improvement || input.clean)) {
      this._data = input;
    } else {
      this._data = {no_improvement: '', improvement: '', clean: ''};
    }
  }

  onNoImprovementMsgChange(msg: string) {
    this._data.no_improvement = msg;
    this.dataChange.emit(this._data);
  }

  onImprovementMsgChange(msg: string) {
    this._data.improvement = msg;
    this.dataChange.emit(this._data);
  }

  onCleanMsgChange(msg: string) {
    this._data.clean = msg;
    this.dataChange.emit(this._data);
  }
}
