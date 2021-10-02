import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TimeSelectComponent} from './time-select/time-select.component';
import {OverlayModule} from '@angular/cdk/overlay';
import {MatDividerModule} from '@angular/material/divider';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {FormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatNativeDateModule} from '@angular/material/core';

@NgModule({
  imports: [
    CommonModule,
    OverlayModule,
    MatDividerModule,
    MatIconModule,
    MatButtonModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatNativeDateModule
  ],
  declarations: [
    TimeSelectComponent
  ],
  exports: [
    TimeSelectComponent
  ]
})
export class UiComponentsTimeSelectModule {
}
