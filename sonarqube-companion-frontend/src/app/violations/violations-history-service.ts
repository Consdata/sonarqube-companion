import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ViolationsHistory} from './violations-history';
import {GroupViolationsHistoryDiff} from './group-violations-history-diff';
import {ProjectViolationsHistoryDiff} from './project-violations-history-diff';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';

@Injectable()
export class ViolationsHistoryService {

  constructor(private http: HttpClient) {
  }

  getGroupHistory(daysLimit: number, uuid?: string): Observable<ViolationsHistory> {
    return this.http
      .get<any>(`api/v1/violations/history/group${uuid ? '/' + uuid : ''}?daysLimit=${daysLimit}`)
      .pipe(
        map(data => new ViolationsHistory(data))
      );
  }

  getGroupHistoryDiff(uuid: string, fromDate: string, toDate: string): Observable<GroupViolationsHistoryDiff> {
    return this.http
      .get<GroupViolationsHistoryDiff>(`api/v1/violations/history/group/${uuid}/${fromDate}/${toDate}`)
      .pipe(
        map(data => new GroupViolationsHistoryDiff(data))
      );
  }

  getGroupProjectHistory(daysLimit: number, uuid: string, projectKey: string): Observable<ViolationsHistory> {
    return this.http
      .get<ViolationsHistory>(`api/v1/violations/history/group/project/${uuid}/${encodeURIComponent(projectKey)}?daysLimit=${daysLimit}`)
      .pipe(
        map(data => new ViolationsHistory(data))
      );
  }

  getProjectHistory(daysLimit: number, projectKey: string): Observable<ViolationsHistory> {
    return this.http
      .get<ViolationsHistory>(`api/v1/violations/history/project/${encodeURIComponent(projectKey)}?daysLimit=${daysLimit}`)
      .pipe(
        map(data => new ViolationsHistory(data))
      );
  }


  getGroupProjectHistoryDiff(group: string, project: string, fromDate: string, toDate: string): Observable<ProjectViolationsHistoryDiff> {
    return this.http
      .get<ProjectViolationsHistoryDiff>(`api/v1/violations/history/group/${group}/project/${project}/${fromDate}/${toDate}`)
      .pipe(
        map(data => new ProjectViolationsHistoryDiff(data))
      );
  }

  getAllProjectsHistoryDiff(fromDate: string, toDate: string): Observable<GroupViolationsHistoryDiff> {
    return this.http
      .get<GroupViolationsHistoryDiff>(`api/v1/violations/history/project/${fromDate}/${toDate}`)
      .pipe(
        map(data => new GroupViolationsHistoryDiff(data))
      );
  }


  getProjectHistoryDiff(projectKey: string, fromDate: string, toDate: string): Observable<ProjectViolationsHistoryDiff> {
    return this.http
      .get<ProjectViolationsHistoryDiff>(`api/v1/violations/history/project/${encodeURIComponent(projectKey)}/${fromDate}/${toDate}`)
      .pipe(
        map(data => new ProjectViolationsHistoryDiff(data))
      );
  }
}
