import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class AppState {

  private appState: any = {};
  private subjects: {[key: string]: Subject<any>} = {};

  setValue(key: string, value: any) {
    this.appState[key] = value;
    this.getSubject(key).next(value);
  }

  getValue(key: string): any {
    return this.appState[key];
  }

  getObservable(key: string): Observable<any> {
    return this.getSubject(key).asObservable();
  }

  private getSubject(key: string): Subject<any> {
    if (!this.subjects[key]) {
      this.subjects[key] = new Subject<any>();
    }
    return this.subjects[key];
  }

}
