import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GroupViolationsHistory} from './group-violations-history';
import {GroupDetails} from './group-details';
import {ProjectViolationsSummary} from '@sonarqube-companion-frontend/project';
import {Member, MemberViolationsSummary} from '@sonarqube-companion-frontend/member';
import {Violations} from '@sonarqube-companion-frontend/group-overview';
import {DateRange} from '@sonarqube-companion-frontend/ui-components/time-select';


@Injectable({providedIn: 'root'})
export class GroupService {
  constructor(private http: HttpClient) {
  }

  public groupViolationsHistory(uuid: string, daysLimit: number): Observable<GroupViolationsHistory> {
    return this.http.get<GroupViolationsHistory>(`/api/v1/violations/history/group/${uuid}`);
  }

  public groupViolationsHistoryRange(uuid: string, range: DateRange): Observable<GroupViolationsHistory> {
    console.log('#### ', uuid)
    return this.http.get<GroupViolationsHistory>(`/api/v1/violations/history/group/${uuid}/${range.fromString}/${range.toString}`);
  }


  public groupDetails(uuid: string, range: DateRange): Observable<GroupDetails> {
    return this.http.get<GroupDetails>(`/api/v1/groups/${uuid}`);
  }

  public groupProjectsViolationsSummary(uuid: string, range: DateRange): Observable<ProjectViolationsSummary[]> {
    return this.http.get<ProjectViolationsSummary[]>(`/api/v1/violations/summary/group/${uuid}/projects/${range.fromString}/${range.toString}`);
  }

  public groupMembersViolationsSummary(uuid: string, range: DateRange): Observable<MemberViolationsSummary[]> {
    return this.http.get<MemberViolationsSummary[]>(`/api/v1/violations/summary/group/${uuid}/members/${range.fromString}/${range.toString}`);
  }

  public members(uuid: string): Observable<Member[]> {
    return this.http.get<Member[]>(`/api/v1/groups/${uuid}/members`);
  }

  public violations(uuid: string, range: DateRange): Observable<Violations> {
    return this.http.get<Violations>(`/api/v1/groups/${uuid}/violations/${range.toString}`);
  }

  public violationsDiff(uuid: string, range: DateRange): Observable<Violations> {
    return this.http.get<Violations>(`/api/v1/groups/${uuid}/violations/${range.fromString}/${range.toString}`);
  }
}
