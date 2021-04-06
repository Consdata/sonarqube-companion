import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ProjectSummary} from './project-summary';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';

@Injectable()
export class ProjectService {

  constructor(private http: HttpClient) {
  }

  getGroupProjectSummary(uuid: string, projectKey: string): Observable<ProjectSummary> {
    return this.http
      .get<ProjectSummary>(`/api/v1/violations/group/${uuid}/summary/project/${encodeURIComponent(projectKey)}`)
      .pipe(
        map(data => new ProjectSummary(data))
      );
  }

  getProjectSummary(projectKey: string): Observable<ProjectSummary> {
    return this.http
      .get<ProjectSummary>(`/api/v1/violations/project/summary/${encodeURIComponent(projectKey)}`)
      .pipe(
        map(data => new ProjectSummary(data))
      );
  }

}
