import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {GroupDetails} from './group-details';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';

@Injectable()
export class GroupService {

  constructor(private http: HttpClient) {
  }

  getGroup(uuid?: string): Observable<GroupDetails> {
    return this.http
      .get<any>(`api/v1/groups${uuid ? '/' + uuid : ''}`)
      .pipe(
        map(data => new GroupDetails(data))
      );
  }

}
