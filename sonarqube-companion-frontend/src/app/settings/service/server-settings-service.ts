import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ValidationResult} from '../common/settings-list-component';
import {ServerDefinition} from '../model/server-definition';
import {Observable} from 'rxjs/index';


@Injectable()
export class ServerSettingsService {
  constructor(private http: HttpClient) {
  }

  get(): Observable<ServerDefinition[]> {
    return this.http
      .get<ServerDefinition[]>('/api/v1/settings/general/server/');
  }

  save(config: ServerDefinition, newServer: boolean = false): Observable<ValidationResult> {
    if (newServer) {
      return this.http
        .post<ValidationResult>('/api/v1/settings/general/server/create', config);
    } else {
      return this.http
        .post<ValidationResult>('/api/v1/settings/general/server/update', config);
    }
  }

  delete(config: ServerDefinition): Observable<ValidationResult> {
    return this.http
      .delete<ValidationResult>(`/api/v1/settings/general/server/${config.id}`);
  }

}
