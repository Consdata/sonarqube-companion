import {Route} from '@angular/router';
import {GroupsComponent} from './groups.component';

export const groupsRouting: Route[] = [
  {
    path: ``,
    component: GroupsComponent,
    children: [
      {
        path: `:groupId`,
        loadChildren: () =>
          import('@sonarqube-companion-frontend/group-overview').then(
            (module) => module.GroupOverviewModule
          ),
      }
    ]
  }
]
