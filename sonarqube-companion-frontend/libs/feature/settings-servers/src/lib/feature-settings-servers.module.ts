import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ServersComponent} from './servers.component';
import {RouterModule} from '@angular/router';
import {serversRouting} from './servers.routing';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(serversRouting),
  ],
  declarations: [
    ServersComponent
  ]
})
export class FeatureSettingsServersModule {
}
