import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {ViolationsHistory} from './violations-history';
import {Http} from '@angular/http';
import {GroupViolationsHistoryDiff} from './group-violations-history-diff';
import {ProjectViolationsHistoryDiff} from './project-violations-history-diff';

@Injectable()
export class ViolationsHistoryService {

  constructor(private http: Http) {
  }

  getGroupHistory(daysLimit: number, uuid?: string): Observable<ViolationsHistory> {
    return this.http
      .get(`api/v1/violations/history/group${uuid ? '/' + uuid : ''}?daysLimit=${daysLimit}`)
      .map(response => new ViolationsHistory(response.json()));
  }

  getGroupHistoryDiff(uuid: string, fromDate: string, toDate: string): Observable<GroupViolationsHistoryDiff> {
    return this.http
      .get(`api/v1/violations/history/group/${uuid}/${fromDate}/${toDate}`)
      .map(response => new GroupViolationsHistoryDiff(response.json()));
  }

  getProjectHistory(daysLimit: number, uuid: string, projectKey: string): Observable<ViolationsHistory> {
    return this.http
      .get(`api/v1/violations/history/project/${uuid}/${encodeURIComponent(projectKey)}?daysLimit=${daysLimit}`)
      .map(response => new ViolationsHistory(response.json()));
  }

  getProjectHistoryDiff(uuid: string, projectKey: string, fromDate: string, toDate: string): Observable<ProjectViolationsHistoryDiff> {
    return this.http
      .get(`api/v1/violations/history/project/${uuid}/${encodeURIComponent(projectKey)}/${fromDate}/${toDate}`)
      .map(response => new ProjectViolationsHistoryDiff(response.json()));
  }

}
