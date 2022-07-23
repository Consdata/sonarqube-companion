import { Injectable } from '@angular/core';
import { State } from '@ngxs/store';
import { GroupConfig } from '../model/group-config';
import { GroupsSettingsState } from './groups-settings-state';
import { GroupSettingsState } from './group-settings-state';
import { ServerConfig } from '../servers/server/server-config';

export interface Settings {
  servers: ServerConfig[];
  rootGroup: GroupConfig;
}

@State<Settings>({
  name: 'settings',
  children: [GroupsSettingsState, GroupSettingsState],
})
@Injectable({ providedIn: 'root' })
export class SettingsState {}
