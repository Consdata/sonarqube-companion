import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationVersion } from './app-version';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class VersionService {
  constructor(private http: HttpClient) {}

  public get(): Observable<ApplicationVersion> {
    return this.http.get<ApplicationVersion>('/api/v1/version');
  }
}
