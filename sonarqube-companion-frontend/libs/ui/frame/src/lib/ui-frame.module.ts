import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UiFrameComponent} from './ui-frame.component';

@NgModule({
  imports: [CommonModule],
  declarations: [UiFrameComponent],
  exports: [UiFrameComponent]
})
export class UiFrameModule {}
