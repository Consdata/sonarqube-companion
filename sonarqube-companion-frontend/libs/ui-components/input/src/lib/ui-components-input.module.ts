import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InputComponent } from './input/input.component';

@NgModule({
  imports: [CommonModule],
  declarations: [InputComponent],
  exports: [InputComponent],
})
export class UiComponentsInputModule {}
