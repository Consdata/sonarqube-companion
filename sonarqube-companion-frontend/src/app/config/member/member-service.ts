import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GroupLightModel} from '../model/group-light-model';

@Injectable()
export class MemberService {

  constructor(private http: HttpClient) {
  }

  getGroups(uuid: string): Observable<GroupLightModel[]> {
    return this.http
      .get<GroupLightModel[]>(`api/v1/members/${uuid}/groups`);
  }
}
