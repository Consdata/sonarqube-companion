import { Route } from '@angular/router';
import { SettingsComponent } from './settings.component';
import { GroupsComponent } from './groups/groups.component';
import { MembersComponent } from './members/members.component';
import { IntegrationsComponent } from './integrations/integrations.component';
import { GroupComponent } from './group/group.component';
import { ServersComponent } from './servers/servers.component';
import { ServerComponent } from './servers/server/server.component';

export const settingsRouting: Route[] = [
  {
    path: ``,
    component: SettingsComponent,
    children: [
      {
        path: 'groups',
        component: GroupsComponent,
      },
      {
        path: 'group/:parentId/:groupId',
        component: GroupComponent,
      },
      {
        path: 'members',
        component: MembersComponent,
      },
      {
        path: 'integrations',
        component: IntegrationsComponent,
      },
    ],
  },
  {
    path: `servers`,
    component: ServersComponent,
    children: [
      {
        path: `:serverId`,
        component: ServerComponent,
      },
    ],
  },
];
