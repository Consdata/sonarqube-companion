import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ApplicationVersion} from './application-version';
import {HttpClient} from "@angular/common/http";

@Injectable()
export class VersionService {

  constructor(private http: HttpClient) {
  }

  public getAppliactionVersion(): Observable<ApplicationVersion> {
    return this.http
      .get<ApplicationVersion>('api/v1/version');
  }
}
