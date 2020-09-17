import {Component, Input, OnInit} from '@angular/core';
import {BehaviorSubject, combineLatest, Observable} from 'rxjs';
import {filter, map} from 'rxjs/operators';
import {GroupDetails} from '../group/group-details';
import {ViolationsHistory} from '../violations/violations-history';
import {ViolationsHistorySerie} from './violations-history-serie';

@Component({
  selector: 'sq-violations-history',
  template: `
    <div class="graph-wrapper" *ngIf="data$ | async as data">
      <ngx-charts-line-chart [results]="data"
                             [timeline]="false"
                             [xAxis]="true"
                             [yAxis]="true"
                             [autoScale]="true"
      >
      </ngx-charts-line-chart>
    </div>
  `,
  styleUrls: [
    './violations-history-component.scss'
  ]
})
export class ViolationsHistoryComponent implements OnInit {

  data$: Observable<ViolationsHistorySerie[]>;
  violationsFilter$: BehaviorSubject<string> = new BehaviorSubject<string>(undefined);

  @Input() set violationsFilter(violationsFilter: string) {
    this.violationsFilter$.next(violationsFilter);
  }

  @Input() group: GroupDetails;
  @Input() violationsHistoryProvider: () => Observable<ViolationsHistory>;

  ngOnInit(): void {
    this.data$ = combineLatest([this.violationsHistoryProvider(), this.violationsFilter$]).pipe(
      filter(([history, violations]) => !!history && !!violations),
      map(([history, violations]) => [{
        name: violations,
        series: history.history.map(entry => ({
          name: entry.date,
          value: entry[violations]
        }))
      }])
    );
  }

}
