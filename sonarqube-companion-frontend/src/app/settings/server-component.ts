import {Component} from '@angular/core';
import {ServerDefinition} from './model/server-definition';
import {SettingsListDetailsItem} from './common/settings-list-item';
import * as uuid from 'uuid';


@Component({
  selector: `sq-settings-server`,
  template: `
    <div class="sq-settings-detail">
      <div class="element">
        <label class="sq-setting-label">Server unique id</label>
        <input type="text" [(ngModel)]="server.id"/>
      </div>
      <div class="element">
        <label class="sq-setting-label">Server url</label>
        <input type="text" [(ngModel)]="server.url"/>
      </div>
      <div class="element">
        <label class="sq-setting-label">Authorization method</label>
        <div>
          <select [(ngModel)]="server.authentication.type">
            <option value="none">None</option>
            <option value="token">Token</option>
            <option value="basic">Basic</option>
          </select>
        </div>
      </div>
      <div class="element">
        <basic-authentication *ngIf="server.authentication.type=='basic'"
                              [(params)]="server.authentication.params"></basic-authentication>
        <token-authentication *ngIf="server.authentication.type=='token'"
                              [(params)]="server.authentication.params"></token-authentication>
      </div>

      <div class="element">
        <sq-settings-badge [(items)]="server.blacklistUsers"
                           [title]="'Users blackList'"></sq-settings-badge>
      </div>
    </div>
  `
})
export class ServerComponent implements SettingsListDetailsItem {

  server: ServerDefinition;

  setModel(model: any): void {
    this.server = <ServerDefinition>model;
    if (!this.server.id) {
      this.server.id = uuid.v4();
    }
  }

  setMetadata(data: any): void {
  }

  getModel(): any {
    return this.server
  }

  getTitle(): string {
    return this.server.id;
  }
}
