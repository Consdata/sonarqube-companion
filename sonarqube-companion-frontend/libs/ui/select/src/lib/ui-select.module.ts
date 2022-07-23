import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UiSelectComponent} from './ui-select.component';
import {UiFrameModule} from '@sonarqube-companion-frontend/ui/frame';
import {MatIconModule} from '@angular/material/icon';

@NgModule({
  imports: [
    CommonModule,
    UiFrameModule,
    MatIconModule
  ],
  declarations: [
    UiSelectComponent
  ],
  exports: [
    UiSelectComponent
  ]
})
export class UiSelectModule {
}
