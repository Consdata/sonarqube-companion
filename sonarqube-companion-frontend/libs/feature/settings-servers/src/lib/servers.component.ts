import {ChangeDetectionStrategy, Component, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Store} from '@ngrx/store';
import {State} from './state/servers.reducer';
import {selectedServer, selectServerErrors, selectServers, selectServersLoading} from './state/servers.selectors';
import {ServerConfig} from '@sonarqube-companion-frontend/data-access/settings';
import {createServer, selectServerById} from './state/servers.actions';
import {tap} from 'rxjs/operators';

// TODO na zapis jak jest sukces to reducer. Kręcioł na serverze.
// TODO czy produce jest dobrze użyty w state


@Component({
  selector: 'sqc-settings-servers',
  template: `
    <sqc-side-list [title]="'Servers'" [showBackButton]="true" (back)="back()" [loading]="loading$ | async">
      <div sqc-side-list-items *ngIf="servers$ | async as servers">
        <mat-nav-list>
          <mat-list-item *ngFor="let server of servers" (click)="toServer(server)"
                         [ngClass]="{selected: (selectedServer$ | async)?.id === server.id}">
            <div class="item">
              <div class="title">{{ server.id }}</div>
            </div>
          </mat-list-item>
        </mat-nav-list>
      </div>
      <div sqc-side-list-content class="content">
        <sqc-settings-server
          [server]="selectedServer$ | async"
          [errors]="errors$ | async"
        ></sqc-settings-server>
      </div>
      <div sqc-side-list-bottom-bar>
        <button mat-button class="add" (click)="addServer()">
          <mat-icon>add</mat-icon>
          new server
        </button>
      </div>
    </sqc-side-list>
  `,
  styleUrls: ['./servers.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ServersComponent implements OnDestroy {

  selectedServer$ = this.store.select(selectedServer);
  servers$ = this.store.select(selectServers).pipe(
    tap(_ => this.store.dispatch(selectServerById({id: this.route.snapshot.paramMap.get('id') ?? ''})))
  );
  errors$ = this.store.select(selectServerErrors)
  loading$ = this.store.select(selectServersLoading);
  subscription = this.route.paramMap
    .subscribe(params => this.store.dispatch(selectServerById({id: params.get('id') ?? ''})));

  constructor(private router: Router, private store: Store<State>, private route: ActivatedRoute) {
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  // serverUid(index: number, server: ServerConfig): string {
  //   return server.uuid;
  // }
  //
  addServer(): void {
    this.store.dispatch(createServer());
  }

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
  back(): void {
    this.router.navigate(['settings']);
  }

  toServer(server: ServerConfig) {
    this.router.navigate(['settings', 'servers', server.id]);
  }
}
