import {ChangeDetectionStrategy, Component, ElementRef, Input, ViewChild} from '@angular/core';
import {Timeline, TimelineSeries} from './timeline';


@Component({
  selector: 'sqc-timeline',
  template: `
    <div class="wrapper" #timeline></div>
  `,
  styleUrls: ['./timeline.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TimelineComponent {
  private timeline?: Timeline;

  @ViewChild('timeline')
  set timelineElement(element: ElementRef) {
    this.timeline = new Timeline(element);
  }

  @Input()
  set series(_series: TimelineSeries) {
    this.redraw(_series);
    window.addEventListener('resize', () => this.redraw(_series));
  }

  private redraw(series: TimelineSeries): void {
    if (this.timeline) {
      this.timeline.clear();
      this.timeline.update(series);
    }
  }
}

