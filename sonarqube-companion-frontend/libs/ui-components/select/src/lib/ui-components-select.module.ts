import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SelectComponent} from './select/select.component';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatBadgeModule} from '@angular/material/badge';
import {OverlayModule} from '@angular/cdk/overlay';
import { SelectItemComponent } from './select-item/select-item.component';

@NgModule({
  imports: [
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatBadgeModule,
    OverlayModule
  ],
  declarations: [
    SelectComponent,
    SelectItemComponent
  ],
  exports: [
    SelectComponent,
    SelectItemComponent
  ]
})
export class UiComponentsSelectModule {
}
