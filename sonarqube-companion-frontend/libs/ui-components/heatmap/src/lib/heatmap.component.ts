import {ChangeDetectionStrategy, Component} from '@angular/core';
import {Series} from '@swimlane/ngx-charts';

@Component({
  selector: 'sqc-heatmap',
  template: `
    <div class="wrapper">
      <ngx-charts-heat-map
        [scheme]="colorScheme"
        [showXAxisLabel]="showXAxisLabel"
        [showYAxisLabel]="showYAxisLabel"
        [xAxis]="xAxis"
        [yAxis]="yAxis"
        [results]="multi"
        (select)="onSelect($event)"
        (activate)="onActivate($event)"
        (deactivate)="onDeactivate($event)"
      >
      </ngx-charts-heat-map>
    </div>
  `,
  styleUrls: ['./heatmap.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeatmapComponent {
  multi: Series[] = [
    {
      'name': 'Jun',
      'series': [
        {
          'name': 'Monday',
          'value': -62000000
        },
        {
          'name': 'Tuesday',
          'value': -73000000
        },
        {
          'name': 'Wednesday',
          'value': 89400000
        },
        {
          'name': 'Thursday',
          'value': 72000000
        },
        {
          'name': 'Friday',
          'value': 72000000
        },
        {
          'name': 'Saturday',
          'value': 72000000
        },
        {
          'name': 'Sunday',
          'value': 72000000
        }
      ]
    },
    {
      'name': ' ',
      'series': [
        {
          'name': 'Monday',
          'value': 62000000
        },
        {
          'name': 'Tuesday',
          'value': 73000000
        },
        {
          'name': 'Wednesday',
          'value': 89400000
        },
        {
          'name': 'Thursday',
          'value': 72000000
        },
        {
          'name': 'Friday',
          'value': 72000000
        },
        {
          'name': 'Saturday',
          'value': 72000000
        },
        {
          'name': 'Sunday',
          'value': 72000000
        }
      ]
    },
    {
      'name': '  ',
      'series': [
        {
          'name': 'Monday',
          'value': 62000000
        },
        {
          'name': 'Tuesday',
          'value': 73000000
        },
        {
          'name': 'Wednesday',
          'value': 89400000
        },
        {
          'name': 'Thursday',
          'value': 72000000
        },
        {
          'name': 'Friday',
          'value': 72000000
        },
        {
          'name': 'Saturday',
          'value': 72000000
        },
        {
          'name': 'Sunday',
          'value': 72000000
        }
      ]
    },
    {
      'name': '   ',
      'series': [
        {
          'name': 'Monday',
          'value': 62000000
        },
        {
          'name': 'Tuesday',
          'value': 73000000
        },
        {
          'name': 'Wednesday',
          'value': 89400000
        },
        {
          'name': 'Thursday',
          'value': 72000000
        },
        {
          'name': 'Friday',
          'value': 72000000
        },
        {
          'name': 'Saturday',
          'value': 72000000
        },
        {
          'name': 'Sunday',
          'value': 72000000
        }
      ]
    },
    {
      'name': 'Jul',
      'series': [
        {
          'name': 'Monday',
          'value': 62000000
        },
        {
          'name': 'Tuesday',
          'value': 73000000
        },
        {
          'name': 'Wednesday',
          'value': 89400000
        },
        {
          'name': 'Thursday',
          'value': 72000000
        },
        {
          'name': 'Friday',
          'value': 72000000
        },
        {
          'name': 'Saturday',
          'value': 72000000
        },
        {
          'name': 'Sunday',
          'value': 72000000
        }
      ]
    },
    {
      'name': '     ',
      'series': [
        {
          'name': 'Monday',
          'value': 62000000
        },
        {
          'name': 'Tuesday',
          'value': 73000000
        },
        {
          'name': 'Wednesday',
          'value': 89400000
        },
        {
          'name': 'Thursday',
          'value': 72000000
        },
        {
          'name': 'Friday',
          'value': 72000000
        },
        {
          'name': 'Saturday',
          'value': 72000000
        },
        {
          'name': 'Sunday',
          'value': 72000000
        }
      ]
    },
    {
      'name': '        ',
      'series': [
        {
          'name': 'Monday',
          'value': 62000000
        },
        {
          'name': 'Tuesday',
          'value': 73000000
        },
        {
          'name': 'Wednesday',
          'value': 89400000
        },
        {
          'name': 'Thursday',
          'value': 72000000
        },
        {
          'name': 'Friday',
          'value': 72000000
        },
        {
          'name': 'Saturday',
          'value': 72000000
        },
        {
          'name': 'Sunday',
          'value': 72000000
        }
      ]
    },
    {
      'name': '        ',
      'series': [
        {
          'name': 'Monday',
          'value': 62000000
        },
        {
          'name': 'Tuesday',
          'value': 73000000
        },
        {
          'name': 'Wednesday',
          'value': 89400000
        },
        {
          'name': 'Thursday',
          'value': 72000000
        },
        {
          'name': 'Friday',
          'value': 72000000
        },
        {
          'name': 'Saturday',
          'value': 72000000
        },
        {
          'name': 'Sunday',
          'value': 72000000
        }
      ]
    },
    {
      'name': '                    ',
      'series': [
        {
          'name': 'Monday',
          'value': 62000000
        },
        {
          'name': 'Tuesday',
          'value': 73000000
        },
        {
          'name': 'Wednesday',
          'value': 89400000
        },
        {
          'name': 'Thursday',
          'value': 72000000
        },
        {
          'name': 'Friday',
          'value': 72000000
        },
        {
          'name': 'Saturday',
          'value': 72000000
        },
        {
          'name': 'Sunday',
          'value': 72000000
        }
      ]
    },
    {
      'name': 'Aug',
      'series': [
        {
          'name': 'Monday',
          'value': 62
        },
        {
          'name': 'Tuesday',
          'value': 73000000
        },
        {
          'name': 'Wednesday',
          'value': 89400000
        },
        {
          'name': 'Thursday',
          'value': 72000000
        },
        {
          'name': 'Friday',
          'value': 72000000
        },
        {
          'name': 'Saturday',
          'value': 72000000
        },
        {
          'name': 'Sunday',
          'value': 72000000
        }
      ]
    },
    {
      'name': '         ',
      'series': [
        {
          'name': 'Monday',
          'value': 6200
        },
        {
          'name': 'Tuesday',
          'value': 73000000
        },
        {
          'name': 'Wednesday',
          'value': 89400000
        },
        {
          'name': 'Thursday',
          'value': 72000
        },
        {
          'name': 'Friday',
          'value': 72000000
        },
        {
          'name': 'Saturday',
          'value': 72000000
        },
        {
          'name': 'Sunday',
          'value': 0
        }
      ]
    },
    {
      'name': '          ',
      'series': [
        {
          'name': 'Monday',
          'value': 62000000
        },
        {
          'name': 'Tuesday',
          'value': 7300
        },
        {
          'name': 'Wednesday',
          'value': 89400000
        },
        {
          'name': 'Thursday',
          'value': 72000000
        },
        {
          'name': 'Friday',
          'value': 72000000
        },
        {
          'name': 'Saturday',
          'value': 72000000
        },
        {
          'name': 'Sunday',
          'value': 72000000
        }
      ]
    },
    {
      'name': '           ',
      'series': [
        {
          'name': 'Monday',
          'value': 62000000
        },
        {
          'name': 'Tuesday',
          'value': 73000000
        },
        {
          'name': 'Wednesday',
          'value': 89400000
        },
        {
          'name': 'Thursday',
          'value': 72000000
        }
      ]
    },

  ];

  view: any = [0, 0];

  // options
  legend: boolean = true;
  showLabels: boolean = true;
  animations: boolean = true;
  xAxis: boolean = true;
  yAxis: boolean = true;
  showYAxisLabel: boolean = true;
  showXAxisLabel: boolean = true;
  xAxisLabel: string = 'Country';
  yAxisLabel: string = 'Year';

  colorScheme = {
    domain: ['#c8f4d9', '#ddf5e2', '#eeeeee', '#f8dada', '#f3c5c5']
  };

  constructor() {
    //  Object.assign(this, {multi});
  }

  onSelect(data: any): void {
  //  console.log('Item clicked', JSON.parse(JSON.stringify(data)));
  }

  onActivate(data: any): void {
  //  console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  onDeactivate(data: any): void {
 //   console.log('Deactivate', JSON.parse(JSON.stringify(data)));

  }
}
