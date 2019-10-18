import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AppState} from 'app/app-state';

@Injectable()
export class AppStateService {

  private static readonly THEME = 'state.app.theme';

  constructor(private appState: AppState) {
  }

  get theme(): string {
    return this.appState.getValue(AppStateService.THEME);
  }

  set theme(theme: string) {
    this.appState.setValue(AppStateService.THEME, theme);
  }

  get themeObservable(): Observable<string> {
    return this.appState.getObservable(AppStateService.THEME);
  }

}
