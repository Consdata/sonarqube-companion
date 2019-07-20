import {Component, OnInit, Type} from '@angular/core';
import {ServerDefinition} from './model/server-definition';
import {SettingsListDetailsItem} from './common/settings-list-item';
import {ServerComponent} from './server-component';
import {Subject} from 'rxjs/index';
import {ValidationResult} from './common/settings-list-component';
import {ServerSettingsService} from './service/server-settings-service';


@Component({
  selector: `sq-settings-servers`,
  template: `
    <div class="sq-settings-container">
      <sq-spinner *ngIf="!loaded"></sq-spinner>
      <sq-settings-list
        [loaded]="loaded"
        [details]="serverType"
        [(data)]="servers"
        [title]="'Servers'"
        [foldedListLabel]="'%number% more servers defined'"
        [newItem]="newItem.asObservable()"
        [validation]="validation.asObservable()"
        [label]="getLabel"
        (addClick)="addServer()"
        (removeItem)="removeServer($event)"
        (saveItem)="saveServer($event)"
      ></sq-settings-list>
    </div>
  `
})
export class ServersSettingsComponent implements OnInit {
  loaded: boolean = false;
  servers: ServerDefinition[] = [];
  serverType: Type<SettingsListDetailsItem> = ServerComponent;
  newItem: Subject<ServerDefinition> = new Subject();
  validation: Subject<ValidationResult> = new Subject();

  constructor(private serverService: ServerSettingsService) {
  }

  ngOnInit(): void {
    this.load();
  }

  addServer() {
    const newServerDefinition: ServerDefinition = new ServerDefinition();
    this.servers.push(newServerDefinition);
    this.newItem.next(newServerDefinition);
  }

  removeServer(server: ServerDefinition) {
    this.loaded = false;
    this.serverService.delete(server).subscribe(validationResult => {
      if (validationResult.valid) {
        this.load();
      } else {
        this.loaded = true;
      }
      validationResult.item = server;
      this.validation.next(validationResult);

    });
  }

  saveServer(server: { item: ServerDefinition, newItem: boolean }) {
    this.loaded = false;
    this.serverService.save(server.item, server.newItem).subscribe(validationResult => {
      if (validationResult.valid) {
        this.load();
      } else {
        this.loaded = true;
      }
      validationResult.item = server.item;
      this.validation.next(validationResult);
    });
  }

  load() {
    this.loaded = false;
    this.serverService.get().subscribe(data => {
      this.servers = data;
      this.loaded = true;
    });
  }

  getLabel(item: ServerDefinition): string {
    return item.id;
  }
}
