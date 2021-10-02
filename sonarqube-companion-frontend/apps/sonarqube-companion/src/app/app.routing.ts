import {NotFoundComponent} from './not-found/not-found.component';

export const appRoutes = [
  {
    path: '', redirectTo: '/overview', pathMatch: 'full'
  },
  {
    path: 'overview', pathMatch: 'full',
    loadChildren: () =>
      import('@sonarqube-companion-frontend/overview').then(
        (module) => module.OverviewModule
      ),
  },
  {
    path: 'group/:groupId',
    loadChildren: () =>
      import('@sonarqube-companion-frontend/group-overview').then(
        (module) => module.GroupOverviewModule
      ),
  },
  {
    path: 'settings',
    loadChildren: () =>
      import('@sonarqube-companion-frontend/settings').then(
        (module) => module.SettingsModule
      ),
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];
