import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ChipsComponent} from './chips/chips.component';
import {MatChipsModule} from '@angular/material/chips';
import {MatIconModule} from '@angular/material/icon';

@NgModule({
  imports: [
    CommonModule,
    MatChipsModule,
    MatIconModule
  ],
  declarations: [
    ChipsComponent
  ],
  exports: [
    ChipsComponent
  ]
})
export class UiComponentsChipsModule {
}
