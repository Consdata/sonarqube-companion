import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {SidenavModule} from '@sonarqube-companion-frontend/sidenav';
import {ServersComponent} from './servers.component';
import {ServersSidenavComponent} from './servers-sidenav.component';
import {serversRouting} from './servers.routing';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(serversRouting),
    SidenavModule
  ],
  declarations: [
    ServersComponent,
    ServersSidenavComponent
  ],
  exports: [
    ServersComponent
  ],
})

export class ServersModule {
}
