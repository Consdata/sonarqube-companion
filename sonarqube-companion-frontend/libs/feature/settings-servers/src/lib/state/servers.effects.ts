import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {ServersSettingsService} from '@sonarqube-companion-frontend/data-access/settings';
import {routerNavigationAction} from '@ngrx/router-store';
import {
  createServer,
  createServerFailed,
  createServerSuccess,
  deleteServer,
  deleteServerFailed,
  deleteServerSuccess,
  loadServers,
  loadServersFailed,
  loadServersSuccess,
  saveServer,
  saveServerFailed,
  saveServerSuccess
} from './servers.actions';
import {Store} from '@ngrx/store';
import {catchError, exhaustMap, filter, map, tap} from 'rxjs/operators';
import {of} from 'rxjs';

@Injectable({providedIn: 'root'})
export class ServersEffects {
  static readonly url = '/settings/servers';

  constructor(private actions$: Actions, private serversService: ServersSettingsService, private store: Store) {
  }

  loadServersOnNavigation$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(routerNavigationAction),
        filter((action) => action.payload.routerState.url.startsWith(ServersEffects.url)),
        tap(() => this.store.dispatch(loadServers())),
        exhaustMap(() => this.serversService.list()
          .pipe(
            map(servers => loadServersSuccess({servers})),
            catchError(() => of(loadServersFailed()))
          )
        )
      ),
    {dispatch: true}
  );

  loadServers$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(loadServers, saveServerSuccess, createServerSuccess, deleteServerSuccess),
        exhaustMap(() => this.serversService.list()
          .pipe(
            map(servers => loadServersSuccess({servers})),
            catchError(() => of(loadServersFailed()))
          )
        )
      ),
    {dispatch: true}
  );

  createServer$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(createServer),
        exhaustMap(() => this.serversService.create()
          .pipe(
            map(() => createServerSuccess()),
            catchError(() => of(createServerFailed()))
          )
        )
      ),
    {dispatch: true}
  );

  deleteServer$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(deleteServer),
        exhaustMap((action) => this.serversService.delete(action.server)
          .pipe(
            map(() => deleteServerSuccess()),
            catchError(() => of(deleteServerFailed()))
          )
        )
      ),
    {dispatch: true}
  );

  saveServer$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(saveServer),
        exhaustMap((action) => this.serversService.save(action.server)
          .pipe(
            map((result) => saveServerSuccess({validationResult: result})),
            catchError((e) => of(saveServerFailed({validationResult: e.error})))
          )
        )
      ),
    {dispatch: true}
  );
}
