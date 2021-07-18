import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Series} from '@swimlane/ngx-charts';
import {curveBasis, CurveFactory} from 'd3-shape';

@Component({
  selector: 'sqc-timeline',
  template: `
    <div class="wrapper">
      <ngx-charts-line-chart
        [results]="data"
        [timeline]="false"
        [xAxis]="true"
        [yAxis]="true"
        [autoScale]="true"
        [curve]="curve"
      >
      </ngx-charts-line-chart>
    </div>
  `,
  styleUrls: ['./timeline.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TimelineComponent {

  @Input()
  data!: Series[];

  curve: CurveFactory = curveBasis;

  onSelect(data: any): void {
    //  console.log('Item clicked', JSON.parse(JSON.stringify(data)));
  }

  onActivate(data: any): void {
    //  console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  onDeactivate(data: any): void {
    //  console.log('Deactivate', JSON.parse(JSON.stringify(data)));
  }

}
