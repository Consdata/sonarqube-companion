import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Member} from '../model/member';
import {HttpClient} from '@angular/common/http';
import {ValidationResult} from '../common/settings-list/settings-list-component';


@Injectable()
export class MemberConfigService {
  constructor(private http: HttpClient) {
  }

  all(): Observable<Member[]> {
    return this.http.get<Member[]>('/api/v1/settings/member/all');
  }

  integrationsSummary(): Observable<{ [key: string]: string; }> {
    return this.http.get<{ [key: string]: string; }>('/api/v1/settings/member/integrations');
  }

  save(member: Member, newMember: boolean = false): Observable<ValidationResult> {
    if (newMember) {
      return this.http
        .post<ValidationResult>('/api/v1/settings/member/create', member);
    } else {
      return this.http
        .post<ValidationResult>('/api/v1/settings/member/update', member);
    }
  }

  delete(member: Member): Observable<ValidationResult> {
    return this.http
      .delete<ValidationResult>(`/api/v1/settings/member/${member.uuid}`);
  }
}
