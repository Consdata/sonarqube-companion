import {AfterViewInit, Component} from '@angular/core';
import {combineLatest, ReplaySubject} from 'rxjs';
import {map, switchMap} from 'rxjs/operators';
import {ServersConfigService} from '../service/servers-config.service';
import {ServerConfig} from '../model/server-config';
import {SpinnerService} from '../../../../utils/src/lib/spinner.service';
import {Locks} from '@sonarqube-companion-frontend/utils';

@Component({
  selector: 'sqc-settings-servers',
  template: `
    <ng-container *ngIf="vm$ | async as vm">
      <div class="list" *ngFor="let server of vm.servers; trackBy:serverUid">
        <sqc-settings-server
          [server]="server"
          class="item"
          (onSave)="save()"
          (onDelete)="delete()"
        ></sqc-settings-server>
      </div>
      <div class="add">
        <span mat-ripple (click)="addServer()"
              *ngIf="isAddServerAllowed(); else spinner">+ new server</span>
      </div>
      <ng-template #spinner>
        <mat-spinner diameter="20"></mat-spinner>
      </ng-template>
    </ng-container>
  `,
  styleUrls: ['./servers.component.scss']
})
export class ServersComponent implements AfterViewInit {
  subject: ReplaySubject<void> = new ReplaySubject<void>();
  vm$ = this.subject.asObservable().pipe(
    switchMap(() =>
      combineLatest([
        this.configService.list()
      ]).pipe(
        map(([
               servers,
             ]) => ({
          servers: servers,
        }))
      )
    )
  )

  constructor(private configService: ServersConfigService, private spinnerService: SpinnerService) {
  }

  ngAfterViewInit(): void {
    this.subject.next();
  }

  serverUid(index: number, server: ServerConfig): string {
    return server.uuid;
  }

  addServer(): void {
    this.spinnerService.lock(Locks.ADD_SERVER);
    this.configService.create().subscribe(() => {
      this.subject.next();
      this.spinnerService.unlock(Locks.ADD_SERVER);
    });
  }

  save(): void {
    this.subject.next();
  }

  delete(): void {
    this.subject.next();
  }

  isAddServerAllowed(): boolean {
    return this.spinnerService.isUnlocked(Locks.ADD_SERVER);
  }
}
