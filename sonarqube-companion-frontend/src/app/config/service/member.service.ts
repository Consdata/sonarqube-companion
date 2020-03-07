import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Member} from '../model/member';
import {HttpClient} from '@angular/common/http';


@Injectable()
export class MemberService {
  constructor(private http: HttpClient) {
  }

  all(): Observable<Member[]> {
    return this.http.get<Member[]>('/api/v1/settings/member/all');
  }

}
