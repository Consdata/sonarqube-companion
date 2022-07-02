import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {settingsRouting} from './settings.routing';
import {SettingsComponent} from './settings.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(settingsRouting)
  ],
  declarations: [
    SettingsComponent
  ]
})
export class FeatureSettingsModule {
}
