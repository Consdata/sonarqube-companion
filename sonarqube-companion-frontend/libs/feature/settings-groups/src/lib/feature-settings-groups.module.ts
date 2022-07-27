import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GroupComponent} from './group.component';
import {GroupsComponent} from './groups.component';
import {RouterModule} from '@angular/router';
import {groupsRouting} from './groups.routing';
import {UiSideTreeModule} from '@sonarqube-companion-frontend/ui/side-tree';
import {DataAccessSettingsModule} from '@sonarqube-companion-frontend/data-access/settings';
import {DataAccessGroupsModule} from '@sonarqube-companion-frontend/data-access/groups';
import {StoreModule} from '@ngrx/store';
import {FEATURE, reducer} from './state/groups.reducer';
import {GroupsEffects} from './state/groups.effects';
import {EffectsModule} from '@ngrx/effects';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(groupsRouting),
    UiSideTreeModule,
    DataAccessSettingsModule,
    DataAccessGroupsModule,
    StoreModule.forFeature(FEATURE, reducer),
    DataAccessSettingsModule,
    EffectsModule.forFeature([GroupsEffects]),
  ],
  declarations: [
    GroupComponent,
    GroupsComponent
  ]
})
export class FeatureSettingsGroupsModule {
}
