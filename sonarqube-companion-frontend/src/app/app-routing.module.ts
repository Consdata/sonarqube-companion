import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OverviewComponent} from './overview/overview-component';
import {NotFoundComponent} from './not-found/not-found-component';
import {GroupComponent} from './group/group-component';
import {ProjectComponent} from './project/project-component';
import {SettingsComponent} from "./settings/settings-component";
import {GroupDetailSettingsComponent} from "./settings/group/group-detail-settings-component";

const routes: Routes = [
  {
    path: 'overview',
    component: OverviewComponent
  },
  {
    path: 'groups',
    component: GroupComponent
  },
  {
    path: 'groups/:uuid',
    component: GroupComponent
  },
  {
    path: 'project/:uuid/:projectKey',
    component: ProjectComponent
  },
  {
    path: 'settings',
    component: SettingsComponent
  },
  {
    path: 'settings/group/:uuid',
    component: GroupDetailSettingsComponent
  },
  {
    path: '404',
    component: NotFoundComponent
  },
  {
    path: '',
    redirectTo: '/overview',
    pathMatch: 'full'
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
