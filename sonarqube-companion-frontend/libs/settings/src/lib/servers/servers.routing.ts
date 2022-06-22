import {Route} from '@angular/router';
import {ServersComponent} from './servers.component';

export const serversRouting: Route[] = [
  {
    path: ``,
    component: ServersComponent,
    children: [
      {

      }
    ]
  }
]
