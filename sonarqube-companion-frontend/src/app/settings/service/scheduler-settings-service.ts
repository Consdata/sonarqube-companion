import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SchedulerConfig} from '../model/scheduler-config';
import {Observable} from 'rxjs/index';
import {ValidationResult} from '../common/settings-list-component';


@Injectable()
export class SchedulerSettingsService {

  constructor(private http: HttpClient) {
  }

  get(): Observable<SchedulerConfig> {
    return this.http
      .get<SchedulerConfig>('api/v1/settings/general/scheduler');
  }

  update(config: SchedulerConfig): Observable<ValidationResult> {
    return this.http
      .post<ValidationResult>('api/v1/settings/general/scheduler', config);
  }

}
