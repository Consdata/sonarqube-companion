import {Route} from '@angular/router';
import {GroupsComponent} from './groups.component';

export const groupsRouting: Route[] = [
  {
    path: ``,
    component: GroupsComponent
  },
  {
    path: `:id`,
    component: GroupsComponent
  }
];
