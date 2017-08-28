import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {SynchronizationState} from './synchronization-state';

@Injectable()
export class SynchronizationService {
  constructor(private http: Http) {

  }

  startSynchronization(): Observable<SynchronizationState> {
    return this.http
      .post('api/v1/sync/start', {})
      .map(response => response.json());
  }

  synchronizationState(): Observable<SynchronizationState> {
    return this.http
      .get('api/v1/sync/state')
      .map(response => response.json());
  }

}
