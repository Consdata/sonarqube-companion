import {GroupDetails} from '@sonarqube-companion-frontend/group';
import {Observable} from 'rxjs';
import {InjectionToken} from '@angular/core';

export const GROUPS_SETTINGS_PROVIDER_TOKEN =
  new InjectionToken<GroupsSettingsProviderService>('groups-settings-provider');

export interface GroupsSettingsProviderService {
  getRootGroup(): Observable<GroupDetails>;

  groupsSettingsIsLoading(): Observable<any>;
}
