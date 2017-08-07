import {Component, HostBinding} from '@angular/core';
import {AppStateService} from 'app/app-state-service';

@Component({
  selector: 'sq-sonarqube-companion',
  template: `
    <nav>
      <sq-navbar></sq-navbar>
    </nav>
    <!--<aside>-->
    <!--<sq-sidebar></sq-sidebar>-->
    <!--</aside>-->
    <main>
      <router-outlet></router-outlet>
    </main>
    <footer>
      <sq-footer></sq-footer>
    </footer>
  `,
  styles: []
})
export class SonarQubeCompanionComponent {

  constructor(private appState: AppStateService) {
  }

  // noinspection JSUnusedGlobalSymbols - used by @HostBinding
  @HostBinding('attr.theme')
  get attrThemeBinding(): string {
    return this.appState.theme;
  }

}
