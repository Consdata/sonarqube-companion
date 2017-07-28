import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Http} from '@angular/http';
import {GroupOverview} from './group-overview';

@Injectable()
export class OverviewService {

  constructor(private http: Http) {
  }

  getRootGroupOverview(): Observable<GroupOverview> {
    return this.http
      .get('api/v1/overview')
      .map(response => new GroupOverview(response.json()));
  }

}
