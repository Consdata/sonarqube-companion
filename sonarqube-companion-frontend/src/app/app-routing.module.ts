import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OverviewComponent} from './overview/overview-component';
import {NotFoundComponent} from './not-found/not-found-component';
import {GroupComponent} from './group/group-component';

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
