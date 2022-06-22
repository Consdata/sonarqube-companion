import {AfterViewInit, Component, EventEmitter, Output} from '@angular/core';
import {combineLatest, ReplaySubject} from 'rxjs';
import {map, switchMap} from 'rxjs/operators';
import {SpinnerService} from '../../../../utils/src/lib/spinner.service';
import {ServersConfigService} from './servers-config.service';
import {Router} from '@angular/router';
import {ServerConfig} from './server/server-config';

@Component({
  selector: 'sqc-settings-servers-sidenav',
  template: `
    <ng-container *ngIf="vm$ | async as vm">
      <mat-sidenav-container>
        <mat-sidenav opened mode="side" [disableClose]="true">
          <div class="wrapper">
            <div class="header">
              <div class="back" (click)="back()">
                <mat-icon>arrow_back_ios</mat-icon>
              </div>
              <div class="title">Servers</div>
              <div class="add" (click)="addServer()">
                <mat-icon>add</mat-icon>
              </div>
            </div>
            <mat-divider></mat-divider>
            <mat-nav-list>
              <mat-list-item *ngFor="let server of vm.servers; trackBy:serverUid">
                <div class="item" (click)="goToServer(server.uuid)">
                  <div class="title">{{server.id}}</div>
                </div>
              </mat-list-item>
            </mat-nav-list>
          </div>
        </mat-sidenav>
        <mat-sidenav-content>
          <ng-content></ng-content>
        </mat-sidenav-content>
      </mat-sidenav-container>
    </ng-container>
    <!--      <div class="list" *ngFor="let server of vm.servers; trackBy:serverUid">-->
    <!--        <sqc-settings-server-->
    <!--          [server]="server"-->
    <!--          class="item"-->
    <!--          (onSave)="save()"-->
    <!--          (onDelete)="delete()"-->
    <!--        ></sqc-settings-server>-->
    <!--      </div>-->
    <!--      <div class="add">-->
    <!--        <span mat-ripple (click)="addServer()"-->
    <!--              *ngIf="isAddServerAllowed(); else spinner">+ new server</span>-->
    <!--      </div>-->
    <!--      <ng-template #spinner>-->
    <!--        <mat-spinner diameter="20"></mat-spinner>-->
    <!--      </ng-template>-->
  `,
  styleUrls: ['./servers-sidenav.component.scss']
})
export class ServersSidenavComponent implements AfterViewInit {
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

  constructor(private configService: ServersConfigService, private spinnerService: SpinnerService, private router: Router) {
  }

  @Output()
  private add: EventEmitter<void> = new EventEmitter<void>();


  ngAfterViewInit(): void {
    this.subject.next();
  }

  back(): void {
    this.router.navigate(['settings'])
  }

  addServer(): void {
    this.add.next();
  }


  serverUid(index: number, server: ServerConfig): string {
    return server.uuid;
  }

  goToServer(uuid: string): void {
    this.router.navigate(['settings', 'servers', uuid])
  }
}
