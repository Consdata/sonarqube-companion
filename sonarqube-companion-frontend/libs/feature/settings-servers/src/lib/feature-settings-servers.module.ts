import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ServersComponent} from './servers.component';
import {RouterModule} from '@angular/router';
import {serversRouting} from './servers.routing';
import {UiSideListModule} from '@sonarqube-companion-frontend/ui/side-list';
import {MatListModule} from '@angular/material/list';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {ServerComponent} from './server.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(serversRouting),
    UiSideListModule,
    MatListModule,
    MatIconModule,
    MatButtonModule
  ],
  declarations: [
    ServersComponent,
    ServerComponent
  ]
})
export class FeatureSettingsServersModule {
}
