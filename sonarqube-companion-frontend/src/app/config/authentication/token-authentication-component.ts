import {Component, EventEmitter, Input, Output} from '@angular/core';
import {TokenAuthenticationData} from '../model/authentication-data';

@Component({
  selector: `token-authentication`,
  template: `
    <div>
      <label class="sq-setting-label">Authorization token</label>
      <input [ngModel]="_params.token" (ngModelChange)="onTokenChanged($event)"/>
    </div>`
})
export class TokenAuthenticationComponent {
  @Output() paramsChange: EventEmitter<TokenAuthenticationData> = new EventEmitter();

  _params: TokenAuthenticationData;

  @Input()
  set params(data: TokenAuthenticationData) {
    this._params = new TokenAuthenticationData(data);
    this.paramsChange.emit(this._params);

  }

  onTokenChanged(token: string) {
    this._params.token = token;
    this.paramsChange.emit(this._params);
  }

}
