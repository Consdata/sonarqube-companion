import {Component, EventEmitter, Input, Output} from "@angular/core";
import {ServerDefinition} from "./model/server-definition";
import {BasicAuthenticationData, TokenAuthenticationData} from "./model/authentication-data";


@Component({
  selector: `sq-settings-server`,
  template: `
    <div class="header">
      <div class="id">{{server.id}}</div>
      <div class="actions">
        <button *ngIf="edit" (click)="onSave()">save</button>
        <button (click)="toggleEdit()">edit</button>
        <button (click)="onRemove()">remove</button>
      </div>
    </div>
    <div class="detail" *ngIf="edit">
      <div class="id">
        <input type="text" [(ngModel)]="server.id"/>
      </div>
      <div class="url">
        <input type="text" [(ngModel)]="server.url"/>
      </div>
      <div class="authentication">
        <select [(ngModel)]="server.authentication.type">
          <option value="none">None</option>
          <option value="token" (select)="onTokenAuthSelect()">Token</option>
          <option value="basic" (select)="onBasicAuthSelect()">Basic</option>
        </select>
        <div>
          <basic-authentication *ngIf="server.authentication.type=='basic'"
                                [params]="server.authentication.params"></basic-authentication>
          <token-authentication *ngIf="server.authentication.type=='token'"
                                [params]="server.authentication.params"></token-authentication>
        </div>
      </div>
    </div>
  `
})
export class ServerComponent {


  @Input()
  private server: ServerDefinition;
  @Input()
  private edit: boolean = false;

  @Output()
  private remove = new EventEmitter<void>();

  @Output()
  private save = new EventEmitter<ServerDefinition>();

  toggleEdit() {
    this.edit = !this.edit;
  }

  onSave() {
    this.save.emit(this.server);
    this.toggleEdit();
  }

  onRemove() {
    this.remove.emit();
  }

  onTokenAuthSelect() {
    this.server.authentication.params = new TokenAuthenticationData({token: ''});
  }

  onBasicAuthSelect() {
    this.server.authentication.params = new BasicAuthenticationData({user: '', password: ''});
  }
}
