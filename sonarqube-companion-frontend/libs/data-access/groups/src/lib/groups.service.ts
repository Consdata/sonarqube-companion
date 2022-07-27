import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GroupLightModel} from './group-light-model';

@Injectable({providedIn: 'root'})
export class GroupsService {
  constructor(private http: HttpClient) {
  }

  public list(): Observable<GroupLightModel> {
    return this.http.get<GroupLightModel>(`/api/v1/groups/info/root`);
  }
}
