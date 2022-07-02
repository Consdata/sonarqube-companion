import {Route} from '@angular/router';
import {SettingsComponent} from './settings.component';

export const settingsRouting: Route[] = [
  {
    path: ``,
    component: SettingsComponent,
    children: [
      // {
      //   path: 'groups',
      //   component: GroupsComponent
      // },
      // {
      //   path: 'group/:parentId/:groupId',
      //   component: GroupComponent
      // },
      // {
      //   path: 'members',
      //   component: MembersComponent
      // },
      // {
      //   path: 'integrations',
      //   component: IntegrationsComponent
      // }
    ]
  },
  {
    path: `servers`,
    loadChildren: () =>
      import('@sonarqube-companion-frontend/feature/settings-servers').then(
        (module) => module.FeatureSettingsServersModule
      ),
  },
];
