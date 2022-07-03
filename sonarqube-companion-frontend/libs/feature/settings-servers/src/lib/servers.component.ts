import {Component} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'sqc-settings-servers',
  template: `

    <sqc-side-list
      [title]="'Servers'"
      [showBackButton]="true"
      (back)="back()"
    >
      <div sqc-side-list-items>
        <mat-nav-list>
          <mat-list-item>
            <div class="item">
              <div class="title">Servers</div>
              <div class="description">Data sources</div>
            </div>
          </mat-list-item>
        </mat-nav-list>
      </div>
      <div sqc-side-list-content>
        <router-outlet></router-outlet>
      </div>
      <div sqc-side-list-bottom-bar>
        <button mat-button class="add"><mat-icon>add</mat-icon>new server</button>
      </div>
    </sqc-side-list>
  `,
  styleUrls: ['./servers.component.scss']
})
export class ServersComponent {

  constructor(private router: Router) {
  }

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
  back():void {
    this.router.navigate(['settings'])
  }
}
