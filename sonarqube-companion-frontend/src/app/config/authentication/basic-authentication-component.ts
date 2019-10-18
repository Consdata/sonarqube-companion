import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BasicAuthenticationData} from '../model/authentication-data';

@Component({
  selector: `basic-authentication`,
  template: `
    <div>
      <div>
        <label class="sq-setting-label">Username</label>
        <input [ngModel]="_params.user" (ngModelChange)="onUserChange($event)"/>
      </div>
      <div>
        <label class="sq-setting-label">Password</label>
        <input [ngModel]="_params.password" (ngModelChange)="onPasswordChange($event)"/>
      </div>
    </div>`
})
export class BasicAuthenticationComponent {
  @Output() paramsChange: EventEmitter<BasicAuthenticationData> = new EventEmitter();

  _params: BasicAuthenticationData;

  @Input()
  set params(data: BasicAuthenticationData) {
    this._params = new BasicAuthenticationData(data);
    this.paramsChange.emit(this._params);
  }

  onUserChange(user: string) {
    this._params.user = user;
    this.paramsChange.emit(this._params);
  }

  onPasswordChange(password: string) {
    this._params.password = password;
    this.paramsChange.emit(this._params);
  }
}
