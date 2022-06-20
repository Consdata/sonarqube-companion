import {ChangeDetectionStrategy, Component} from '@angular/core';

@Component({
  selector: 'sqc-root',
  template: `
    <sqc-topbar></sqc-topbar>
    <div class="sidenav">
      <sqc-main-sidenav>
        <router-outlet></router-outlet>
      </sqc-main-sidenav>
    </div>
  `,
  styleUrls: ['./app.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent {

}
