import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {ProjectViolationsHistory} from './project-violations-history';
import {Http} from '@angular/http';

@Injectable()
export class ProjectViolationsHistoryService {

  constructor(private http: Http) {
  }

  getHistory(uuid?: string): Observable<ProjectViolationsHistory> {
    return this.http
      .get(`api/v1/violations/history/group${uuid ? '/' + uuid : ''}`)
      .map(response => new ProjectViolationsHistory(response.json()));
  }

}
