import { GroupDetails } from '@sonarqube-companion-frontend/group';
import { Observable } from 'rxjs';
import { InjectionToken } from '@angular/core';

export const GROUPS_SETTINGS_RESOLVER_TOKEN =
  new InjectionToken<GroupsSettingsResolverService>('groups-settings-resolver');

export interface GroupsSettingsResolverService {
  resolveRootGroup(): Observable<GroupDetails>;
}
