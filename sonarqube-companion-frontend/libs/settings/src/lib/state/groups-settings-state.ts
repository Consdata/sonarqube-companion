import {Injectable} from '@angular/core';
import {Action, NgxsOnInit, Selector, State, StateContext} from '@ngxs/store';
import {GroupConfig} from '../model/group-config';
import {tap} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {GroupDetails, GroupService} from '@sonarqube-companion-frontend/group';
import {GroupsConfigService} from '../service/groups-config.service';
import {ValidationResult} from '../model/validation-result';

export class AddChild {
  static readonly type = '[Groups] AddChild';

  constructor(public parent: string) {
  }
}

export class LoadRootGroup {
  static readonly type = '[Groups] LoadRootGroup';

  constructor() {
  }
}

@State<GroupDetails>({
  name: 'rootGroup'
})
@Injectable({providedIn: 'root'})
export class GroupsSettingsState implements NgxsOnInit {

  constructor(private groupService: GroupService, private groupsConfigService: GroupsConfigService) {
  }

  @Selector()
  static rootGroup(state: GroupConfig): GroupConfig {
    return state;
  }

  ngxsOnInit(ctx: StateContext<GroupConfig>): any {
    ctx.dispatch(new LoadRootGroup());
  }

  @Action(LoadRootGroup)
  loadRootGroup(ctx: StateContext<GroupDetails>, action: LoadRootGroup): Observable<GroupDetails> {
    return this.groupService.getRootGroup().pipe(tap(group => ctx.setState(group)));
  }

  @Action(AddChild)
  addChild(ctx: StateContext<GroupDetails>, action: AddChild): Observable<ValidationResult>  {
    return this.groupsConfigService.create(action.parent).pipe(tap(() => ctx.dispatch(new LoadRootGroup())));
  }
}
