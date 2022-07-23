import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'sqc-groups',
  template: `
    <sqc-group-sidenav>
      <router-outlet></router-outlet>
    </sqc-group-sidenav>
  `,
  styleUrls: ['./groups.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GroupsComponent {}
