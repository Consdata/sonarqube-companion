import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Http} from '@angular/http';
import {GroupDetails} from './group-details';

@Injectable()
export class GroupService {

  constructor(private http: Http) {
  }

  getGroup(uuid?: string): Observable<GroupDetails> {
    return this.http
      .get(`api/v1/groups${uuid ? '/' + uuid : ''}`)
      .map(response => new GroupDetails(response.json()));
  }

}
