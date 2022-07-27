import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {routerNavigationAction} from '@ngrx/router-store';
import {Store} from '@ngrx/store';
import {catchError, exhaustMap, filter, map, tap} from 'rxjs/operators';
import {of} from 'rxjs';
import {loadGroups, loadGroupsFailed, loadGroupsSuccess} from './groups.actions';
import {GroupsService} from '@sonarqube-companion-frontend/data-access/groups';

@Injectable({providedIn: 'root'})
export class GroupsEffects {
  static readonly url = '/settings/groups';

  constructor(private actions$: Actions, private groupsService: GroupsService, private store: Store) {
  }

  loadGroupsOnNavigation$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(routerNavigationAction),
        filter((action) => action.payload.routerState.url.startsWith(GroupsEffects.url)),
        tap(() => this.store.dispatch(loadGroups())),
        exhaustMap(() => this.groupsService.list()
          .pipe(
            map(group => loadGroupsSuccess({rootGroup: group})),
            catchError(() => of(loadGroupsFailed()))
          )
        )
      ),
    {dispatch: true}
  );

  loadGroups$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(loadGroups),
        exhaustMap(() => this.groupsService.list()
          .pipe(
            map(group => loadGroupsSuccess({rootGroup: group})),
            catchError(() => of(loadGroupsFailed()))
          )
        )
      ),
    {dispatch: true}
  );


}
