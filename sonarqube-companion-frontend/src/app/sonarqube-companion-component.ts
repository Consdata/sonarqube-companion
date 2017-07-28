import {Component} from '@angular/core';

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
}
