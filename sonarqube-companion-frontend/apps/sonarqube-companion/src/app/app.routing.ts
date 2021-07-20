import {NotFoundComponent} from './not-found/not-found.component';

export const appRoutes = [
  {path: '', redirectTo: '/overview', pathMatch: 'full'},
  {
    path: 'group/:groupId',
    loadChildren: () =>
      import('@sonarqube-companion-frontend/group-overview').then(
        (module) => module.GroupOverviewModule
      ),
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];
