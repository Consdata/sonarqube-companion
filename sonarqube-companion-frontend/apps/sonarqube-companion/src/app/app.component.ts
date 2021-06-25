import {Component} from '@angular/core';
import {SidebarItem} from '../../../../libs/ui-components/sidebar/src/lib/sidebar/sidebar.component';

@Component({
  selector: 'sqc-root',
  template: `
    <sqc-sidebar name="sqcompoanion" shortName="sqc" [items]="sidebarItems">
      <router-outlet></router-outlet>
    </sqc-sidebar>
  `,
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'sonarqube-companion';

  sidebarItems: SidebarItem[] = [
    {icon: 'home', label: 'Overview', path: '/overview'},
    {icon: 'group_work', label: 'Groups', path: '/groups'},
    {icon: 'code', label: 'Projects', path: '/projects'},
  ];
}
