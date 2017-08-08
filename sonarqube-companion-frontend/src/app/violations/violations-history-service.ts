import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {ViolationsHistory} from './violations-history';
import {Http} from '@angular/http';
import {GroupViolationsHistoryDiff} from './group-violations-history-diff';

@Injectable()
export class ViolationsHistoryService {

  constructor(private http: Http) {
  }

  getHistory(daysLimit: number, uuid?: string): Observable<ViolationsHistory> {
    return this.http
      .get(`api/v1/violations/history/group${uuid ? '/' + uuid : ''}?daysLimit=${daysLimit}`)
      .map(response => new ViolationsHistory(response.json()));
  }

  getHistoryDiff(uuid: string, fromDate: string, toDate: string): Observable<GroupViolationsHistoryDiff> {
    return this.http
      .get(`api/v1/violations/history/group/${uuid}/${fromDate}/${toDate}`)
      .map(response => new GroupViolationsHistoryDiff(response.json()));
  }

}
