import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UiInputComponent} from './ui-input.component';
import {UiFrameModule} from '@sonarqube-companion-frontend/ui/frame';

@NgModule({
  imports: [
    CommonModule,
    UiFrameModule
  ],
  declarations: [
    UiInputComponent
  ],
  exports: [
    UiInputComponent
  ]
})
export class UiInputModule {
}
