import {GroupDetails} from '@sonarqube-companion-frontend/group';
import {Observable} from 'rxjs';
import {InjectionToken} from '@angular/core';

export const GROUPS_SETTINGS_SERVICE_TOKEN =
  new InjectionToken<GroupsSettingsService>('groups-settings-service');

export interface GroupsSettingsService {
  addChild(parent: GroupDetails): void;

  loadRootGroup(): Observable<GroupDetails>;

  groupsSettingsIsLoading(): Observable<any>;
}
