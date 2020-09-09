import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LdapConfig} from './ldap-config';
import {ValidationResult} from '../../common/settings-list/settings-list-component';

@Injectable()
export class LdapIntegrationService {

  constructor(private http: HttpClient) {
  }

  public getLdapConfig(): Observable<LdapConfig> {
    return this.http.get<LdapConfig>('/api/v1/settings/integrations/ldap');
  }

  public updateLdapConfig(config: LdapConfig): Observable<ValidationResult> {
    return this.http.post<ValidationResult>('/api/v1/settings/integrations/ldap', config);
  }

}
