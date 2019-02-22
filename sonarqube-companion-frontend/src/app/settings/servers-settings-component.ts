import {Component, OnInit} from "@angular/core";
import {ServerDefinition} from "./model/server-definition";
import {SettingsService} from "./service/settings-service";


@Component({
  selector: `sq-settings-servers`,
  template: `
    <div class="sq-settings-group-title">
      <div>Servers</div>
      <hr>
    </div>
    <div class="sq-settings-container">
      <div class="servers-list">
        <div *ngFor="let server of servers; let i=index; let l=last"
             class="server">
          <div>
            <sq-settings-server [server]="server" (remove)="removeServer(i)" (save)="saveServer($event, i)"
                                [edit]="l"></sq-settings-server>
          </div>

        </div>
      </div>
      <div>
        <button (click)="addServer()">Add</button>
      </div>
    </div>
  `
})
export class ServersSettingsComponent implements OnInit {
  servers: ServerDefinition[] = [];

  constructor(private settingsService: SettingsService) {
  }

  ngOnInit(): void {
    this.settingsService.getServers().subscribe(data => this.servers = data);
  }

  addServer() {
    this.servers.push(new ServerDefinition());
  }

  removeServer(i: number) {
    this.servers.splice(i, 1);
    this.settingsService.saveServers(this.servers).subscribe();

  }

  saveServer(server: ServerDefinition, i: number) {
    this.servers[i] = server;
    this.settingsService.saveServers(this.servers).subscribe();
  }
}
