import {Component} from '@angular/core';
import {combineLatest, ReplaySubject} from 'rxjs';
import {map, switchMap} from 'rxjs/operators';
import {ServersConfigService} from '../service/servers-config.service';
import {SpinnerService} from '../../../../utils/src/lib/spinner.service';

@Component({
  selector: 'sqc-settings-servers-sidenav',
  template: `
    <ng-container *ngIf="vm$ | async as vm">
      <mat-sidenav-container>
        <mat-sidenav opened mode="side" [disableClose]="true">
          <div class="wrapper">
            <mat-nav-list>
              <!--            <mat-list-item>-->
              <!--              <div class="item" (click)="goToGroups()">-->
              <!--                <div class="title">Groups</div>-->
              <!--                <div class="description">Edit groups hierarchy & groups data</div>-->
              <!--              </div>-->
              <!--            </mat-list-item>-->
              <mat-list-item>
                <div class="item">
                  <div class="title">Servers</div>
                  <div class="description">Data sources</div>
                </div>
              </mat-list-item>
            </mat-nav-list>
          </div>
        </mat-sidenav>
        <mat-sidenav-content>
          <ng-content></ng-content>
        </mat-sidenav-content>
      </mat-sidenav-container>
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
    </ng-container>
  `,
  styleUrls: ['./servers-sidenav.component.scss']
})
export class ServersSidenavComponent {
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
}
