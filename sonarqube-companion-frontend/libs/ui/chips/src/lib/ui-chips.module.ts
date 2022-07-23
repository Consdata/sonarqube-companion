import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UiChipsComponent} from './ui-chips.component';
import {MatChipsModule} from '@angular/material/chips';
import {UiFrameModule} from '@sonarqube-companion-frontend/ui/frame';
import {MatIconModule} from '@angular/material/icon';

@NgModule({
  imports: [
    CommonModule,
    MatChipsModule,
    UiFrameModule,
    MatIconModule
  ],
  declarations: [
    UiChipsComponent
  ],
  exports: [
    UiChipsComponent
  ]
})
export class UiChipsModule {
}
