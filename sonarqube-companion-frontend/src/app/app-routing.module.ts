import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OverviewComponent} from './overview/overview-component';
import {NotFoundComponent} from './not-found/not-found-component';
import {GroupComponent} from './group/group-component';
import {ProjectComponent} from './project/project-component';
import {SettingsComponent} from './config/settings-component';
import {GroupDetailSettingsComponent} from './config/group/group-detail-settings-component';
import {ProjectsOverviewComponent} from './project/projects-overview-component';
import {ProjectOverviewComponent} from './project/project-overview-component';

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
    path: 'project/:projectKey',
    component: ProjectOverviewComponent
  },
  {
    path: 'project/:uuid/:projectKey',
    component: ProjectComponent
  },
  {
    path: 'projects',
    component: ProjectsOverviewComponent
  },
  {
    path: 'settings',
    component: SettingsComponent
  },
  {
    path: 'settings/group/:parentUuid/:uuid',
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
