import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {ApplicationVersion} from './application-version';

@Injectable()
export class VersionService {

  constructor(private http: Http) {
  }

  public getAppliactionVersion(): Observable<ApplicationVersion> {
    return this.http
      .get('api/v1/version')
      .map(response => response.json());
  }
}
