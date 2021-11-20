import {Route} from '@angular/router';
import {SettingsComponent} from './settings.component';
import {ServersComponent} from './servers/servers.component';

export const settingsRouting: Route[] = [
  {
    path: ``,
    component: SettingsComponent,
    children: [
      {
        path: 'servers',
        component: ServersComponent
      }
    ]
  },

];
