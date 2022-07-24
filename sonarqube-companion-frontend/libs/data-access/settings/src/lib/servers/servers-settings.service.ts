import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ServerConfig } from './server-config';
import { ValidationResult } from '../validation/validation-result';

@Injectable({ providedIn: 'root' })
export class ServersSettingsService {
  constructor(private http: HttpClient) {}

  public list(): Observable<ServerConfig[]> {
    return this.http.get<ServerConfig[]>(`/api/v1/settings/general/server/`);
  }

  public get(uuid: string): Observable<ServerConfig> {
    return this.http.get<ServerConfig>(
      `/api/v1/settings/general/server/${uuid}`
    );
  }

  public create(): Observable<void> {
    return this.http.post<void>(`/api/v1/settings/general/server/create`, {});
  }

  public save(server: ServerConfig): Observable<ValidationResult> {
    return this.http.post<ValidationResult>(
      `/api/v1/settings/general/server/update`,
      server
    );
  }

  public delete(server: ServerConfig): Observable<void> {
    return this.http.delete<void>(
      `/api/v1/settings/general/server/${server.uuid}`
    );
  }
}