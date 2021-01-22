import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ProjectSummary} from './project-summary';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';

@Injectable()
export class ProjectService {

  constructor(private http: HttpClient) {
  }

  getProject(uuid: string, projectKey: string): Observable<ProjectSummary> {
    return this.http
      .get<any>(`api/v1/projects/${uuid}/${encodeURIComponent(projectKey)}`)
      .pipe(
        map(data => new ProjectSummary(data))
      );
  }

  // TODO refactor
  getProject2(projectKey: string): Observable<ProjectSummary> {
    return this.http
      .get<any>(`api/v1/projects/${encodeURIComponent(projectKey)}`)
      .pipe(
        map(data => new ProjectSummary(data))
      );
  }

}
