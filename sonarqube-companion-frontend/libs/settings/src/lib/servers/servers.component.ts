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
      <sqc-settings-servers-sidenav>
        <router-outlet></router-outlet>
      </sqc-settings-servers-sidenav>
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
