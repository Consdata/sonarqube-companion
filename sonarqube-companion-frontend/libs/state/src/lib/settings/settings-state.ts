import {Injectable} from '@angular/core';
import {State} from '@ngxs/store';

export interface Settings {
  servers: ServerConfig[];
  rootGroup: GroupConfig;
}

@State<Settings>({
  name: 'settings',
  children: [GroupsSettingsState, GroupSettingsState]
})
@Injectable({providedIn: 'root'})
export class SettingsState {

}
