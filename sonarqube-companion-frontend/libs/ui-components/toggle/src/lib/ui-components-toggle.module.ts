import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ToggleComponent} from './toggle/toggle.component';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';

@NgModule({
  imports: [
    CommonModule,
    MatSlideToggleModule
  ],
  declarations: [
    ToggleComponent
  ],
  exports: [
    ToggleComponent
  ]
})
export class UiComponentsToggleModule {
}
