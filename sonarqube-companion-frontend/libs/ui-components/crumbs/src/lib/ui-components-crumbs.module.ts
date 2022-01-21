import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CrumbsComponent} from './crumbs/crumbs.component';
import {MatDividerModule} from '@angular/material/divider';

@NgModule({
  imports: [
    CommonModule,
    MatDividerModule
  ],
  declarations: [
    CrumbsComponent
  ],
  exports: [
    CrumbsComponent
  ]
})
export class UiComponentsCrumbsModule {
}
