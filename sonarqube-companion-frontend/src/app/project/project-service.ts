import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Http} from '@angular/http';
import {ProjectSummary} from './project-summary';

@Injectable()
export class ProjectService {

  constructor(private http: Http) {
  }

  getProject(uuid: string, projectKey: string): Observable<ProjectSummary> {
    return this.http
      .get(`api/v1/projects/${uuid}/${encodeURIComponent(projectKey)}`)
      .map(response => new ProjectSummary(response.json()));
  }

}
