import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {AppState} from 'app/app-state';

@Injectable()
export class AppStateService {

  private static readonly THEME = 'state.app.theme';

  constructor(private appState: AppState) {
  }

  set theme(theme: string) {
    this.appState.setValue(AppStateService.THEME, theme);
  }

  get theme(): string {
    return this.appState.getValue(AppStateService.THEME);
  }

  get themeObservable(): Observable<string> {
    return this.appState.getObservable(AppStateService.THEME);
  }

}
