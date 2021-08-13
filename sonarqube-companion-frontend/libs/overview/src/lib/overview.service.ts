import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GroupLightModel, GroupOverview} from '@sonarqube-companion-frontend/group-overview';

@Injectable({
  providedIn: 'root'
})
export class OverviewService {
  readonly url = '/api/v1/overview'

  constructor(private http: HttpClient) {
  }

  public overview(): Observable<GroupOverview> {
    return this.http.get<GroupOverview>(this.url);
  }

  public list(): Observable<GroupLightModel> {
    return this.http.get<GroupLightModel>(`${this.url}/list`);
  }
}
