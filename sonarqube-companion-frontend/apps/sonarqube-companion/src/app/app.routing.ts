import {NotFoundComponent} from './not-found/not-found.component';

export const appRoutes = [
  {path: '', redirectTo: '/overview', pathMatch: 'full'},
  {
    path: 'overview',
    loadChildren: () =>
      import('@sonarqube-companion-frontend/overview').then(
        (module) => module.OverviewModule
      ),
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];
