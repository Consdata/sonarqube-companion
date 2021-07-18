import {ChangeDetectionStrategy, Component} from '@angular/core';
import {SidebarItem} from '../../../../libs/ui-components/sidebar/src/lib/sidebar/sidebar.component';

@Component({
  selector: 'sqc-root',
  template: `
    <sqc-group-sidenav name="sqcompoanion">
      <router-outlet></router-outlet>
    </sqc-group-sidenav>
  `,
  styleUrls: ['./app.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent {

}
