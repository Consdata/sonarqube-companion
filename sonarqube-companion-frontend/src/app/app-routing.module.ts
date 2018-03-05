import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OverviewComponent} from './overview/overview-component';
import {NotFoundComponent} from './not-found/not-found-component';
import {GroupComponent} from './group/group-component';
import {ProjectComponent} from './project/project-component';
import {ExtDispatcherComponent} from "./ext/ext.dispatcher.component";

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
  },
  {
    path: 'ext/:id',
    component: ExtDispatcherComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
