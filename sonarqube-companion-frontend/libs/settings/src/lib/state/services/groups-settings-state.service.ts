import {GroupsSettingsService} from '../../groups/groups-settings.service';
import {Injectable} from '@angular/core';
import {GroupDetails} from '@sonarqube-companion-frontend/group';
import {Observable} from 'rxjs';
import {Select, Store} from '@ngxs/store';
import {AddChild, GroupsSettingsState, LoadRootGroup} from '../groups-settings-state';
import {ActionsExecuting, actionsExecuting} from '@ngxs-labs/actions-executing';

@Injectable()
export class GroupsSettingsStateService implements GroupsSettingsService {
  @Select(GroupsSettingsState.rootGroup)
  rootGroup$!: Observable<GroupDetails>;

  @Select(actionsExecuting([AddChild, LoadRootGroup])) actionsExecuting$!: Observable<ActionsExecuting>;

  constructor(private store: Store) {
  }

  addChild(parent: GroupDetails): void {
    this.store.dispatch(new AddChild(parent.uuid));
  }

  groupsSettingsIsLoading(): Observable<any> {
    return this.actionsExecuting$;
  }

  loadRootGroup(): Observable<GroupDetails> {
    this.store.dispatch(new LoadRootGroup());
    return this.rootGroup$;
  }

}
