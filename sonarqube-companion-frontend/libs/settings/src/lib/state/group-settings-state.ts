import { Action, Selector, State, StateContext } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { GroupConfig, ProjectLink } from '../model/group-config';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ValidationResult } from '../model/validation-result';
import { GroupsConfigService } from '../service/groups-config.service';

export class LoadGroup {
  static readonly type = '[Group] LoadGroup';

  constructor(public uuid: string) {}
}

export class DeleteGroup {
  static readonly type = '[Group] DeleteGroup';

  constructor(public parentUuid: string, public uuid: string) {}
}

@State<GroupConfig>({
  name: 'group',
  defaults: undefined,
})
@Injectable({ providedIn: 'root' })
export class GroupSettingsState {
  constructor(private groupsConfigService: GroupsConfigService) {}

  @Selector()
  static group(state: GroupConfig): GroupConfig {
    return state;
  }

  @Selector()
  static projects(state: GroupConfig): ProjectLink[] {
    return state.projectLinks;
  }

  @Action(LoadGroup)
  loadGroup(
    ctx: StateContext<GroupConfig>,
    action: LoadGroup
  ): Observable<GroupConfig> {
    return this.groupsConfigService
      .get(action.uuid)
      .pipe(tap((group) => ctx.setState(group)));
  }

  @Action(DeleteGroup)
  deleteGroup(
    ctx: StateContext<GroupConfig>,
    action: DeleteGroup
  ): Observable<ValidationResult> {
    return this.groupsConfigService.delete(action.parentUuid, action.uuid);
  }
}
