import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UiButtonComponent } from './ui-button.component';
import {MatRippleModule} from '@angular/material/core';

@NgModule({
  imports: [
    CommonModule,
    MatRippleModule
  ],
  declarations: [UiButtonComponent],
  exports: [
    UiButtonComponent
  ]
})
export class UiButtonModule {}
