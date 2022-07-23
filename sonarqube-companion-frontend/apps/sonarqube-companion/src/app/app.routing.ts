import { NotFoundComponent } from './not-found/not-found.component';

export const appRoutes = [
  {
    path: '',
    loadChildren: () =>
      import('@sonarqube-companion-frontend/overview').then(
        (module) => module.OverviewModule
      ),
  },
  {
    path: 'overview',
    loadChildren: () =>
      import('@sonarqube-companion-frontend/overview').then(
        (module) => module.OverviewModule
      ),
  },
  {
    path: 'group',
    loadChildren: () =>
      import('@sonarqube-companion-frontend/groups').then(
        (module) => module.GroupsModule
      ),
  },
  {
    path: 'settings',
    loadChildren: () =>
      import('@sonarqube-companion-frontend/feature/settings').then(
        (module) => module.FeatureSettingsModule
      ),
  },
  {
    path: '**',
    component: NotFoundComponent,
  },
];
