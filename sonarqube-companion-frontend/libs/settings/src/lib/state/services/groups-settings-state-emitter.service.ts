import { Injectable } from '@angular/core';
import { GroupDetails } from '@sonarqube-companion-frontend/group';
import { Store } from '@ngxs/store';
import { AddChild } from '../groups-settings-state';
import { GroupsSettingsEmitterService } from '../../groups/groups-settings-emitter.service';

@Injectable()
export class GroupsSettingsStateEmitterService
  implements GroupsSettingsEmitterService
{
  constructor(private store: Store) {}

  addChild(parent: GroupDetails): void {
    this.store.dispatch(new AddChild(parent.uuid));
  }
}
