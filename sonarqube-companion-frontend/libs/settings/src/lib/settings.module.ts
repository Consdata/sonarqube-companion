import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {settingsRouting} from './settings.routing';
import {SettingsComponent} from './settings.component';
import {MatDividerModule} from '@angular/material/divider';
import {MatRippleModule} from '@angular/material/core';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(settingsRouting),
    MatDividerModule,
    MatRippleModule
  ],
  declarations: [
    SettingsComponent
  ],
  exports: [
    SettingsComponent
  ]
})
export class SettingsModule {
}
