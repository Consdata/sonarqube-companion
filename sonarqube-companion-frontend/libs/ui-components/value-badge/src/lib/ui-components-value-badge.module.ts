import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ValueBadgeComponent} from './value-badge.component';
import {MatDividerModule} from '@angular/material/divider';
import {MatIconModule} from '@angular/material/icon';

@NgModule({
  imports: [
    CommonModule,
    MatIconModule,
    MatDividerModule
  ],
  declarations: [
    ValueBadgeComponent
  ],
  exports: [
    ValueBadgeComponent
  ]
})
export class UiComponentsValueBadgeModule {
}
