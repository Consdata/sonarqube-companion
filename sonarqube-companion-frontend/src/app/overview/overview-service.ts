import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {GroupOverview} from './group-overview';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';

@Injectable()
export class OverviewService {

  constructor(private http: HttpClient) {
  }

  getRootGroupOverview(): Observable<GroupOverview> {
    return this.http
      .get<any>('api/v1/overview')
      .pipe(
        map(data => new GroupOverview(data))
      );
  }

}
