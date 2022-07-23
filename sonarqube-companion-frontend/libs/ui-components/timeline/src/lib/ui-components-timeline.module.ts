import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TimelineComponent } from './timeline.component';

@NgModule({
  imports: [CommonModule],
  declarations: [TimelineComponent],
  exports: [TimelineComponent],
})
export class UiComponentsTimelineModule {}
