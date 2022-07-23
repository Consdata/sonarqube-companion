import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {ServerConfig} from '@sonarqube-companion-frontend/data-access/settings';
import {ServerErrorModel} from './server-error-model';
import {Store} from '@ngrx/store';
import {State} from './state/servers.reducer';
import {deleteServer, saveServer, updateServer} from './state/servers.actions';
import {MatTabChangeEvent} from '@angular/material/tabs';
import {selectCurrentServerLoading} from './state/servers.selectors';

@Component({
  selector: 'sqc-settings-server',
  template: `
    <ng-container  *ngIf="(loading$ | async) === false; else spinner">
    <div class="container" *ngIf="server; else blank">
      <div class="header">
        <div class="content">
          <div class="title">{{server.id}}</div>
          <div class="actions">
            <div class="delete">
              <sqc-button [warn]="true" (click)="deleteServer(server)">Delete</sqc-button>
            </div>
            <div class="save">
              <sqc-button (click)="saveServer(server)">Save</sqc-button>
            </div>
          </div>
        </div>
        <mat-divider class="header-divider"></mat-divider>
      </div>
      <div class="properties">
        <sqc-input class="property" [label]="'Server ID'" [value]="server.id" [error]="errors?.id"
                   (valueChange)="updateServer({id: $event})"></sqc-input>
        <sqc-input class="property" [label]="'Server URL'" [value]="server.url" [error]="errors?.url"
                   (valueChange)="updateServer({url: $event})"></sqc-input>
        <div class="subsection">
          <div class="title">Authorization</div>
          <mat-divider></mat-divider>
        </div>
        <mat-tab-group mat-align-tabs="start" class="property" [selectedIndex]="tabIndex(server.authentication.type)"
                       (selectedTabChange)="updateAuthType($event)">
          <mat-tab label="Basic auth">
            <div class="basic">
              <sqc-input class="property" [label]="'Username'"
                         [value]="server.authentication.params.user"
                         (valueChange)="updateServerAuthParams({user: $event})"
                         [error]="errors?.authUser"
              ></sqc-input>
              <sqc-input class="property" [label]="'Password'"
                         [value]="server.authentication.params.password"
                         (valueChange)="updateServerAuthParams({password: $event})"
                         [error]="errors?.authPassword"
              ></sqc-input>
            </div>
          </mat-tab>
          <mat-tab label="Token">
            <div class="token">
              <sqc-input class="property" [label]="'Token'"
                         [value]="server.authentication.params.token"
                         (valueChange)="updateServerAuthParams({token: $event})"
                         [error]="errors?.authToken"
              ></sqc-input>
            </div>
          </mat-tab>
        </mat-tab-group>
        <div class="subsection">
          <div class="title">Miscellaneous</div>
          <mat-divider></mat-divider>
          <sqc-chips class="property" [label]="'Users blacklist'" [value]="copy(server.blacklistUsers)"
                     (valueChange)="updateServer({blacklistUsers: $event})"
          ></sqc-chips>
          <sqc-chips class="property" [label]="'Users aliases'" [value]="copy(server.aliases)"
                     (valueChange)="updateServer({aliases: $event})"
          ></sqc-chips>
        </div>
      </div>
    </div>
    </ng-container>

    <ng-template #spinner>
      <div class="spinner">
        <mat-spinner></mat-spinner>
      </div>
    </ng-template>

    <ng-template #blank>
      <div class="blank">
        <mat-icon class="icon">dns</mat-icon>
        <div class="description">Servers configuration</div>
      </div>
    </ng-template>
  `,
  styleUrls: ['server.component.scss'],

  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ServerComponent {

  @Input()
  server?: ServerConfig;

  @Input()
  errors?: ServerErrorModel;

  loading$ = this.store.select(selectCurrentServerLoading);


  constructor(private store: Store<State>) {
  }

  tabIndex(type: string): number {
    if (type === 'token') {
      return 1;
    } else {
      return 0;
    }
  }

  deleteServer(server: ServerConfig): void {
    this.store.dispatch(deleteServer({server: server}));
  }

  saveServer(server: ServerConfig): void {
    this.store.dispatch(saveServer({server: server}));
  }

  updateServer(patch: any): void {
    this.store.dispatch(updateServer({patch: patch}));
  }

  updateAuthType(tabChange: MatTabChangeEvent): void {
    if (tabChange.index == 0) {
      this.updateServerAuth({type: 'basic'})
    }
    if (tabChange.index == 1) {
      this.updateServerAuth({type: 'token'})
    }
  }

  updateServerAuth(patch: any): void {
    this.store.dispatch(updateServer({
      patch: {
        authentication: {
          ...this.server?.authentication,
          ...patch
        }
      }
    }));
  }

  updateServerAuthParams(patch: any): void {
    this.store.dispatch(updateServer({
      patch: {
        authentication: {
          ...this.server?.authentication,
          params: {
            ...this.server?.authentication.params,
            ...patch
          }
        }
      }
    }));
  }

  copy(array: string[]): string[] {
    return [...array];
  }
}
