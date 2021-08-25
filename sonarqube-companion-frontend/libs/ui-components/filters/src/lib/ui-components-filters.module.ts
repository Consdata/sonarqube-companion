import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UberFilterComponent } from './uber-filter/uber-filter.component';
import { PeriodFilterComponent } from './period-filter/period-filter.component';

@NgModule({
  imports: [CommonModule],
  declarations: [
    UberFilterComponent,
    PeriodFilterComponent
  ],
})
export class UiComponentsFiltersModule {}
