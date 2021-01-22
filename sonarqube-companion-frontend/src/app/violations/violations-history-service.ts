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

  getProjectHistory(daysLimit: number, uuid: string, projectKey: string): Observable<ViolationsHistory> {
    return this.http
      .get<any>(`api/v1/violations/history/project/${uuid}/${encodeURIComponent(projectKey)}?daysLimit=${daysLimit}`)
      .pipe(
        map(data => new ViolationsHistory(data))
      );
  }

  //TODO rename
  getProjectHistoryDiff(daysLimit: number,  projectKey: string): Observable<ViolationsHistory> {
    return this.http
      .get<any>(`api/v1/projects/diff/${encodeURIComponent(projectKey)}?daysLimit=${daysLimit}`)
      .pipe(
        map(data => new ViolationsHistory(data))
      );
  }

  //TODO rename
  getProjectsHistory(fromDate: string, toDate: string): Observable<GroupViolationsHistoryDiff> {
    return this.http
      .get<any>(`api/v1/projects/diff/${fromDate}/${toDate}`)
      .pipe(
        map(data => new GroupViolationsHistoryDiff(data))
      );
  }


  //TODO rename
  getProjectHistory2(projectKey: string, fromDate: string, toDate: string): Observable<GroupViolationsHistoryDiff> {
    return this.http
      .get<any>(`api/v1/projects/diff2/${encodeURIComponent(projectKey)}/${fromDate}/${toDate}`)
      .pipe(
        map(data => new GroupViolationsHistoryDiff(data))
      );
  }
}
