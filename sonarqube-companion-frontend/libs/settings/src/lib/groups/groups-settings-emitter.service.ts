import { GroupDetails } from '@sonarqube-companion-frontend/group';
import { InjectionToken } from '@angular/core';

export const GROUPS_SETTINGS_EMITTER_TOKEN =
  new InjectionToken<GroupsSettingsEmitterService>('groups-settings-emitter');

export interface GroupsSettingsEmitterService {
  addChild(parent: GroupDetails): void;
}
