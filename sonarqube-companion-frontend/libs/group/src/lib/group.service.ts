import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GroupViolationsHistory} from './group-violations-history';
import {GroupDetails} from './group-details';



@Injectable({providedIn: 'root'})
export class GroupService {
  constructor(private http: HttpClient) {
  }

  public groupViolationsHistory(uuid: string, daysLimit: number): Observable<GroupViolationsHistory> {
    return this.http.get<GroupViolationsHistory>(`/api/v1/violations/history/group/${uuid}`, {params: {daysLimit: daysLimit}});
  }

  public groupDetails(uuid: string): Observable<GroupDetails> {
    return this.http.get<GroupDetails>(`/api/v1/groups/${uuid}`);
  }
}
