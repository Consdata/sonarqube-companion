import {Component, Input} from "@angular/core";
import {BasicAuthenticationData} from "../model/authentication-data";

@Component({
  selector: `basic-authentication`,
  template: `
    <div>
      <input [(ngModel)]="params.user"/>
      <input [(ngModel)]="params.password"/>
    </div>`
})
export class BasicAuthenticationComponent {
  @Input()
  private params: BasicAuthenticationData;
}
