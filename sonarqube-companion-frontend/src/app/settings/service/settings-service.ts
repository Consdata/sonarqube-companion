import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {ServerDefinition} from '../model/server-definition';
import {Observable} from 'rxjs/Observable';
import {SchedulerConfig} from '../model/scheduler-config';
import {GroupDefinition} from '../model/group-definition';

@Injectable()
export class SettingsService {

  constructor(private http: Http) {

  }

  getServers(): Observable<ServerDefinition[]> {
    return this.http
      .get('api/v1/settings/servers')
      .map(response => response.json().map(entry => new ServerDefinition(entry)))

  }

  saveServers(config: ServerDefinition[]): Observable<ServerDefinition[]> {
    return this.http
      .post('api/v1/settings/servers', config)
      .map(response => response.json().map(entry => new ServerDefinition(entry)))

  }

  getScheduler(): Observable<SchedulerConfig> {
    return this.http
      .get('api/v1/settings/scheduler')
      .map(response => new SchedulerConfig(response.json()));
  }

  saveScheduler(config: SchedulerConfig): Observable<SchedulerConfig> {
    return this.http
      .post('api/v1/settings/scheduler', config)
      .map(response => new SchedulerConfig(response.json()));
  }

  getRootGroup(): Observable<GroupDefinition> {
    return this.http
      .get('api/v1/settings/group/root')
      .map(response => new GroupDefinition(response.json()));
  }

  getGroup(uuid: string): Observable<GroupDefinition> {
    return this.http
      .get(`api/v1/settings/group/${uuid}`)
      .map(response => new GroupDefinition(response.json()));
  }

  //TODO replace with getGroup and setGroup
  setRootGroup(config: GroupDefinition): Observable<GroupDefinition> {
    return this.http
      .post('api/v1/settings/group/root', config)
      .map(response => new GroupDefinition(response.json()));
  }

  restoreDefaultSettings(): Observable<boolean> {
    return this.http
      .get('api/v1/settings/restore')
      .map(response => response.json());
  }
}
