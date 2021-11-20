import {ChangeDetectionStrategy, ChangeDetectorRef, Component, EventEmitter, Input, Output} from '@angular/core';
import {ServerConfig} from '../model/server-config';
import {ServersConfigService} from '../service/servers-config.service';
import {ValidationResult} from '../model/validation-result';
import {SpinnerService} from '../../../../utils/src/lib/spinner.service';
import {Locks} from '@sonarqube-companion-frontend/utils';

@Component({
  selector: 'sqc-settings-server',
  template: `
    <ng-container *ngIf="server">
      <div class="preview" *ngIf="!expanded">
        <div class="wrapper">
          <div class="row">
            <div class="id item">
              <div class="value">
                <sqc-input [value]="server.uuid" [label]="'SERVER ID'" [disabled]="true"></sqc-input>
              </div>
            </div>
            <div class="url item">
              <div class="value">
                <sqc-input [value]="server.url" [label]="'URL'" [disabled]="true"></sqc-input>
              </div>
            </div>
          </div>
          <div class="actions" *ngIf="areActionsAllowed(); else spinner">
            <sqc-select
              [icon]="'more_vert'"
              [menu]="true"
            >
              <sqc-select-item (click)="expanded = true">
                <mat-icon>edit</mat-icon>
                <span>Edit</span>
              </sqc-select-item>
              <sqc-select-item (click)="delete()">
                <mat-icon>delete</mat-icon>
                <span>Delete</span>
              </sqc-select-item>
            </sqc-select>
          </div>
        </div>
        <ng-template #spinner>
          <div class="actions-spinner">
            <mat-spinner diameter="20"></mat-spinner>
          </div>
        </ng-template>
      </div>
      <div class="container" *ngIf="expanded" [class.error]="message">
        <div class="row error-msg" *ngIf="message">
          <div class="item">
            <div class="value">
              {{message}}
            </div>
          </div>
        </div>
        <div class="row">
          <div class="id item">
            <div class="value">
              <sqc-input [value]="server.uuid" [label]="'SERVER ID'"></sqc-input>
            </div>
          </div>
          <div class="url item">
            <div class="value">
              <sqc-input [value]="server.url" [label]="'URL'"></sqc-input>
            </div>
          </div>
          <div class="actions" *ngIf="areActionsAllowed(); else spinner">
            <sqc-select
              [icon]="'more_vert'"
              [menu]="true"
            >
              <sqc-select-item (click)="save()">
                <mat-icon>save</mat-icon>
                <span>Save</span>
              </sqc-select-item>
              <sqc-select-item (click)="cancel()">
                <mat-icon>cancel</mat-icon>
                <span>Cancel</span>
              </sqc-select-item>
              <sqc-select-item (click)="delete()">
                <mat-icon>delete</mat-icon>
                <span>Delete</span>
              </sqc-select-item>
            </sqc-select>
          </div>
          <ng-template #spinner>
            <div class="actions-spinner">
              <mat-spinner diameter="20"></mat-spinner>
            </div>
          </ng-template>
        </div>
        <div class="row">
          <div class="item">
            <sqc-select [text]="'Authorization:'" [default]="server.authentication.type"
                        (onSelect)="authChange($event)">
              <sqc-select-item [id]="'none'">
                <span>Disabled</span>
              </sqc-select-item>
              <sqc-select-item [id]="'basic'">
                <span>Basic</span>
              </sqc-select-item>
              <sqc-select-item [id]="'token'">
                <span>Token</span>
              </sqc-select-item>
            </sqc-select>
          </div>
        </div>
        <div class="row" *ngIf="server.authentication.type === 'token'">
          <div class="item">
            <div class="value">
              <sqc-input [label]="'TOKEN'" [(value)]="server.authentication.params['token']"></sqc-input>
            </div>
          </div>
        </div>
        <div class="row" *ngIf="server.authentication.type === 'basic'">
          <div class="item">
            <div class="value">
              <sqc-input [label]="'USERNAME'" class="username"
                         [(value)]="server.authentication.params['user']"></sqc-input>
            </div>
          </div>
          <div class="item">
            <div class="value">
              <sqc-input [label]="'PASSWORD'" class="password"
                         [(value)]="server.authentication.params['password']"></sqc-input>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="aliases item">
            <sqc-chips [label]="'MEMBERS ALIASES'" [placeholder]="'alias'" [items]="server.aliases"></sqc-chips>
          </div>
          <div class="blacklist item">
            <sqc-chips [label]="'MEMBERS BLACKLIST'" [placeholder]="'member id'"
                       [items]="server.blacklistUsers"></sqc-chips>
          </div>

        </div>
      </div>
    </ng-container>

  `,
  styleUrls: ['./server.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ServerComponent {
  expanded: boolean = false;
  message: string = '';
  @Output()
  onDelete: EventEmitter<ServerConfig> = new EventEmitter();
  @Output()
  onSave: EventEmitter<ServerConfig> = new EventEmitter();

  @Input()
  server?: ServerConfig;

  constructor(private configService: ServersConfigService, private changeDetector: ChangeDetectorRef, private spinnerService: SpinnerService) {
  }

  delete(): void {
    if (this.server) {
      this.spinnerService.lock(Locks.SERVER_ACTION + this.server.uuid)
      this.configService.delete(this.server).subscribe(() => {
        this.onDelete.emit(this.server);
        this.spinnerService.unlock(Locks.SERVER_ACTION + this.server?.uuid);
      });
    }
  }

  save(): void {
    if (this.server) {
      this.spinnerService.lock(Locks.SERVER_ACTION + this.server.uuid)
      this.configService.save(this.server).subscribe((response: ValidationResult) => {
        if (response.valid) {
          this.onSave.emit(this.server)
          this.expanded = false;
          this.message = '';
        } else {
          this.message = response.message;
        }
        this.spinnerService.unlock(Locks.SERVER_ACTION + this.server?.uuid);
        this.changeDetector.detectChanges();
      });
    }
  }

  cancel(): void {
    this.expanded = false;
  }

  authChange(id: string): void {
    if (this.server) {
      if (id === 'token' && this.server.authentication.type !== 'token') {
        this.server.authentication.params['token'] = '';
      }

      if (id === 'basic' && this.server.authentication.type !== 'basic') {
        this.server.authentication.params['user'] = '';
        this.server.authentication.params['password'] = '';
      }
      this.server.authentication.type = id;
    }

  }

  areActionsAllowed(): boolean {
    if (this.server) {
      return this.spinnerService.isUnlocked(Locks.SERVER_ACTION + this.server.uuid);
    } else {
      return false;
    }
  }
}
