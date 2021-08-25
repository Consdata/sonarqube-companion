import {ChangeDetectionStrategy, Component, ElementRef, HostListener, Input, ViewChild} from '@angular/core';
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
  data?: TimelineSeries;
  private timeline?: Timeline;

  @ViewChild('timeline')
  set timelineElement(element: ElementRef) {
    this.timeline = new Timeline(element);
  }

  @Input()
  set series(_series: TimelineSeries) {
    this.data = _series;
    if (this.data) {
      this.redraw(this.data);
    }
  }

  @HostListener('window:resize')
  onResize() {
    if (this.data) {
      this.redraw(this.data)
    }
  }

  private redraw(series: TimelineSeries): void {
    if (this.timeline) {
      this.timeline.clear();
      this.timeline.update(series);
    }
  }
}

