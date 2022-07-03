import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {settingsRouting} from './settings.routing';
import {SettingsComponent} from './settings.component';
import {UiSideListModule} from '@sonarqube-companion-frontend/ui/side-list';
import {MatListModule} from '@angular/material/list';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(settingsRouting),
    UiSideListModule,
    MatListModule
  ],
  declarations: [
    SettingsComponent
  ]
})
export class FeatureSettingsModule {
}
