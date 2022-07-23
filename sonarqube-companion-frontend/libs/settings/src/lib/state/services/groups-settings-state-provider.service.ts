import { Injectable } from '@angular/core';
import { GroupDetails } from '@sonarqube-companion-frontend/group';
import { Observable } from 'rxjs';
import { Select, Store } from '@ngxs/store';
import {
  AddChild,
  GroupsSettingsState,
  LoadRootGroup,
} from '../groups-settings-state';
import {
  ActionsExecuting,
  actionsExecuting,
} from '@ngxs-labs/actions-executing';
import { GroupsSettingsProviderService } from '../../groups/groups-settings-provider.service';

@Injectable()
export class GroupsSettingsStateProviderService
  implements GroupsSettingsProviderService
{
  @Select(GroupsSettingsState.rootGroup)
  rootGroup$!: Observable<GroupDetails>;

  @Select(actionsExecuting([AddChild, LoadRootGroup]))
  actionsExecuting$!: Observable<ActionsExecuting>;

  constructor(private store: Store) {}

  groupsSettingsIsLoading(): Observable<any> {
    return this.actionsExecuting$;
  }

  getRootGroup(): Observable<GroupDetails> {
    return this.rootGroup$;
  }
}
