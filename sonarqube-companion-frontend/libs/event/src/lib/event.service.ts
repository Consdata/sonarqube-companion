import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Event} from './event';

@Injectable({providedIn: 'root'})
export class EventService {

  constructor(private http: HttpClient) {
  }

  public getByGroup(groupId: string, daysLimit: number): Observable<Event[]> {
    return this.http.get<Event[]>(`/api/v1/events/group/${groupId}`, {params: {daysLimit: daysLimit}});
  }

}
