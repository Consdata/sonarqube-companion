import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {SynchronizationState} from './synchronization-state';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class SynchronizationService {
  constructor(private http: HttpClient) {

  }

  startSynchronization(): Observable<SynchronizationState> {
    return this.http
      .post<SynchronizationState>('api/v1/sync/start', {});
  }

  synchronizationState(): Observable<SynchronizationState> {
    return this.http
      .get<SynchronizationState>('api/v1/sync/state');
  }

}
