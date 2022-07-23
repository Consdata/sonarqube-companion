import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { GroupConfig } from '../model/group-config';
import { Observable } from 'rxjs';
import { GroupLightModel } from '@sonarqube-companion-frontend/group-overview';
import { ValidationResult } from '../model/validation-result';

@Injectable({ providedIn: 'root' })
export class GroupsConfigService {
  constructor(private http: HttpClient) {}

  public get(uuid: string): Observable<GroupConfig> {
    return this.http.get<GroupConfig>(
      `/api/v1/settings/group/${uuid ? uuid : ''}`
    );
  }

  public create(parentUuid: string): Observable<ValidationResult> {
    return this.http.post<ValidationResult>(
      `/api/v1/settings/group/${parentUuid}/create`,
      {}
    );
  }

  public root(): Observable<GroupConfig> {
    return this.http.get<GroupConfig>(`/api/v1/settings/group/`);
  }

  public all(): Observable<GroupLightModel[]> {
    return this.http.get<GroupLightModel[]>(`/api/v1/settings/group/all`);
  }

  // public create(): Observable<void> {
  //   return this.http.post<void>(`/api/v1/settings/general/server/create`, {});
  // }
  //
  // public save(server: GroupConfig): Observable<ValidationResult> {
  //   return this.http.post<ValidationResult>(`/api/v1/settings/general/server/update`, server);
  // }
  //
  // public delete(server: GroupConfig): Observable<void> {
  //   return this.http.delete<void>(`/api/v1/settings/general/server/${server.uuid}`)
  // }
  public crumbs(uuid: string): Observable<GroupLightModel[]> {
    return this.http.get<GroupLightModel[]>(
      `/api/v1/settings/group/crumbs/${uuid ? uuid : ''}`
    );
  }

  delete(parentUuid: string, uuid: string): Observable<ValidationResult> {
    return this.http.delete<ValidationResult>(
      `/api/v1/settings/group/${parentUuid}/${uuid}`
    );
  }
}
