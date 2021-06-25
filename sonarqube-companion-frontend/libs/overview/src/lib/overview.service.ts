import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GroupOverview} from '@sonarqube-companion-frontend/ui-components/group-overview';

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
}
