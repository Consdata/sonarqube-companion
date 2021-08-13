import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SelectComponent} from './select/select.component';
import {MatSelectModule} from '@angular/material/select';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatBadgeModule} from '@angular/material/badge';
import {FormsModule} from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    MatSelectModule,
    MatIconModule,
    MatButtonModule,
    MatBadgeModule,
    FormsModule
  ],
  declarations: [
    SelectComponent

  ],
  exports: [
    SelectComponent
  ]
})
export class UiComponentsSelectModule {
}
