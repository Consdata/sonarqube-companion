import {Observable} from "rxjs/Observable";
import {Http} from "@angular/http";
import {Injectable} from "@angular/core";

@Injectable()
export class UserStatisticsService {

  constructor(private http: Http) {
  }

  getUserStatistics(uuid: string, fromDate: string, toDate: string): Observable<any[]> {
    return this.http
      .get(`api/v1/statistics/users/aggregate/${uuid}/${fromDate}/${toDate}`)
      .map(response => response.json());
  }

}
