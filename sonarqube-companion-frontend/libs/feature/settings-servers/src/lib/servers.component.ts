import {AfterViewInit, Component} from '@angular/core';

@Component({
  selector: 'sqc-settings-servers',
  template: `
    <ng-container>asdasdasdasd
<!--      <sqc-settings-servers-sidenav (add)="addServer()">-->
        <router-outlet></router-outlet>
<!--      </sqc-settings-servers-sidenav>-->
    </ng-container>
  `,
  styleUrls: ['./servers.component.scss']
})
export class ServersComponent {
  // subject: ReplaySubject<void> = new ReplaySubject<void>();
  // vm$ = this.subject.asObservable().pipe(
  //   switchMap(() =>
  //     combineLatest([
  //       this.configService.list()
  //     ]).pipe(
  //       map(([
  //              servers,
  //            ]) => ({
  //         servers: servers,
  //       }))
  //     )
  //   )
  // )
  //
  // constructor(private configService: ServersConfigService, private spinnerService: SpinnerService) {
  // }
  //
  // ngAfterViewInit(): void {
  //   this.subject.next();
  // }
  //
  // serverUid(index: number, server: ServerConfig): string {
  //   return server.uuid;
  // }
  //
  // addServer(): void {
  //   this.spinnerService.lock(Locks.ADD_SERVER);
  //   this.configService.create().subscribe(() => {
  //     this.subject.next();
  //     this.spinnerService.unlock(Locks.ADD_SERVER);
  //   });
  // }
  //
  // save(): void {
  //   this.subject.next();
  // }
  //
  // delete(): void {
  //   this.subject.next();
  // }
  //
  // isAddServerAllowed(): boolean {
  //   return this.spinnerService.isUnlocked(Locks.ADD_SERVER);
  // }
}
