import {Component, Input} from "@angular/core";
import {TokenAuthenticationData} from "../model/authentication-data";

@Component({
  selector: `token-authentication`,
  template: `
    <div>
      <input [(ngModel)]="params.token"/>
    </div>`
})
export class TokenAuthenticationComponent {
  @Input()
  private params: TokenAuthenticationData;
}
