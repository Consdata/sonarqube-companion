import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GroupViolationsHistory} from './group-violations-history';
import {GroupDetails} from './group-details';
import {ProjectViolationsHistoryDiff} from '@sonarqube-companion-frontend/project';
import {Member, MemberViolationsHistoryDiff} from '@sonarqube-companion-frontend/member';
import {Violations} from '@sonarqube-companion-frontend/group-overview';


@Injectable({providedIn: 'root'})
export class GroupService {
  constructor(private http: HttpClient) {
  }

  public groupViolationsHistory(uuid: string, daysLimit: number): Observable<GroupViolationsHistory> {
    return this.http.get<GroupViolationsHistory>(`/api/v1/violations/history/group/${uuid}`);
  }

  public groupDetails(uuid: string): Observable<GroupDetails> {
    return this.http.get<GroupDetails>(`/api/v1/groups/${uuid}`);
  }

  public groupProjectsHistoryDiffRange(uuid: string, fromDate: Date, toDate: Date): Observable<ProjectViolationsHistoryDiff[]> {
    return this.http.get<ProjectViolationsHistoryDiff[]>(`/api/v1/groups/${uuid}/projects/${fromDate}/${toDate}`);
  }

  public groupProjectsHistoryDiff(uuid: string, daysLimit: number): Observable<ProjectViolationsHistoryDiff[]> {
    return this.http.get<ProjectViolationsHistoryDiff[]>(`/api/v1/violations/history/group/${uuid}/projects`, {params: {daysLimit: daysLimit}});
  }

  public groupMembersHistoryDiff(uuid: string, daysLimit: number): Observable<MemberViolationsHistoryDiff[]> {
    return this.http.get<MemberViolationsHistoryDiff[]>(`/api/v1/violations/history/group/${uuid}/members`, {params: {daysLimit: daysLimit}});
  }

  public members(uuid: string): Observable<Member[]> {
    return this.http.get<Member[]>(`/api/v1/groups/${uuid}/members`);
  }

  public violations(uuid: string): Observable<Violations> {
    return this.http.get<Violations>(`/api/v1/groups/${uuid}/violations`);
  }
}
