import {Route} from '@angular/router';
import {SettingsComponent} from './settings.component';
import {ServersComponent} from './servers/servers.component';
import {GroupsComponent} from './groups/groups.component';
import {MembersComponent} from './members/members.component';
import {IntegrationsComponent} from './integrations/integrations.component';
import {GroupComponent} from './group/group.component';

export const settingsRouting: Route[] = [
  {
    path: ``,
    component: SettingsComponent,
    children: [
      {
        path: 'servers',
        component: ServersComponent
      },
      {
        path: 'groups',
        component: GroupsComponent
      },
      {
        path: 'group/:parentId/:groupId',
        component: GroupComponent
      },
      {
        path: 'members',
        component: MembersComponent
      },
      {
        path: 'integrations',
        component: IntegrationsComponent
      }
    ]
  },

];
