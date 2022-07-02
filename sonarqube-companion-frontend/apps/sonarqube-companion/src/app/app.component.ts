import {ChangeDetectionStrategy, Component} from '@angular/core';

@Component({
  selector: 'sqc-root',
  template: `
    <sqc-topbar></sqc-topbar>
    <div class="sidenav">
      <sqc-app-sidenav>
        <router-outlet></router-outlet>
      </sqc-app-sidenav>
    </div>
  `,
  styleUrls: ['./app.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent {

}
