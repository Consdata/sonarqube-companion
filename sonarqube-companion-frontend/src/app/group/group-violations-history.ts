import {Component, EventEmitter, Input, NgZone, OnChanges, OnDestroy, Output} from '@angular/core';

import {ViolationsHistoryService} from '../violations/violations-history-service';
import {ViolationsHistory} from '../violations/violations-history';
import {AmChartsService} from '@amcharts/amcharts3-angular';
import {Subject} from 'rxjs/Subject';
import {Observable} from 'rxjs/Observable';
import {GroupDetails} from './group-details';
import {GroupEvent} from './group-event';

@Component({
  selector: 'sq-group-violations-history',
  template: `
    <div *ngIf="!chart">
      <sq-spinner></sq-spinner>
    </div>
    <div [class.hidden]="!chart">
      <div class="violations-history-chart-issues-menu">
        <span (click)="changeChartIssue('all')" [class.active]="'all' === currentGraph">all</span>
        |
        <span (click)="changeChartIssue('relevant')" [class.active]="'relevant' === currentGraph">relevant</span>
        |
        <span (click)="changeChartIssue('nonrelevant')"
              [class.active]="'nonrelevant' === currentGraph">non relevant</span>
        |
        <span (click)="changeChartIssue('blockers')" [class.active]="'blockers' === currentGraph">blockers</span>
        |
        <span (click)="changeChartIssue('criticals')" [class.active]="'criticals' === currentGraph">criticals</span>
        |
        <span (click)="changeChartIssue('majors')" [class.active]="'majors' === currentGraph">majors</span>
        |
        <span (click)="changeChartIssue('minors')" [class.active]="'minors' === currentGraph">minors</span>
        |
        <span (click)="changeChartIssue('infos')" [class.active]="'infos' === currentGraph">infos</span>
      </div>
      <hr/>
      <div id="violations-history-chart"></div>
    </div>
  `
})
export class GroupViolationsHistoryComponent implements OnChanges, OnDestroy {

  static readonly DEFAULT_GRAPH = 'all';
  static readonly SERIES = [
    'all', 'blockers', 'criticals', 'majors', 'minors', 'infos', 'relevant', 'nonrelevant'
  ];
  static readonly PALETTE = [
    {fill: 'rgba(52, 152, 219, .5)', line: 'rgba(41, 128, 185, 1.0)'},
    {fill: 'rgba(231, 76, 60, .5)', line: 'rgba(192, 57, 43, 1.0)'},
    {fill: 'rgba(52, 73, 94, .5)', line: 'rgba(44, 62, 80, 1.0)'},
    {fill: 'rgba(46, 204, 113, .5)', line: 'rgba(39, 174, 96, 1.0)'},
    {fill: 'rgba(241, 196, 15, .5)', line: 'rgba(243, 156, 18, 1.0)'},
    {fill: 'rgba(155, 89, 182, .5)', line: 'rgba(142, 68, 173, 1.0)'},
  ];

  @Input() group: GroupDetails;
  @Output() zoomed = new EventEmitter();
  chart: any;
  currentGraph: string;
  colorCounter = 0;

  constructor(private service: ViolationsHistoryService,
              private amCharts: AmChartsService,
              private ngZone: NgZone) {
  }

  ngOnChanges(): void {
    this.chart = null;
    this.service
      .getHistory(90, this.group.uuid)
      .subscribe((violationsHistory: ViolationsHistory) => {
        this.initializeChart(violationsHistory);
      });
  }

  ngOnDestroy(): void {
    if (this.chart) {
      this.amCharts.destroyChart(this.chart);
    }
  }

  changeChartIssue(issueType: string): void {
    this.amCharts.updateChart(this.chart, () => {
      // hide old graph
      this.chart.hideGraph(this.chart.getGraphById(this.currentGraph));

      // change graph references to requested graph
      const graphToSelect = this.chart.getGraphById(issueType);
      this.chart.showGraph(graphToSelect);
      this.chart.chartScrollbar.graph = graphToSelect;

      // store requsted graph as current
      this.currentGraph = issueType;
    });
  }

  private initializeChart(violationsHistory: ViolationsHistory) {
    if (this.chart) {
      this.amCharts.destroyChart(this.chart);
    }
    this.currentGraph = GroupViolationsHistoryComponent.DEFAULT_GRAPH;
    this.chart = this.amCharts.makeChart('violations-history-chart', {
      ...this.standardLinearChart(this.currentGraph),
      dataProvider: violationsHistory.history,
      graphs: GroupViolationsHistoryComponent.SERIES.map(this.graphDefinition.bind(this)),
      ...{
        categoryAxis: {
          parseDates: true,
          dashLength: 1,
          minorGridEnabled: true,
          guides: this.group.events.map(this.mapEventToGuide.bind(this))
        }
      }
    });

    GroupViolationsHistoryComponent.SERIES
      .filter(series => series !== GroupViolationsHistoryComponent.DEFAULT_GRAPH)
      .forEach(series => this.chart.hideGraph(this.chart.getGraphById(series)));

    const zoomedSubject = new Subject<any>();
    this.chart.addListener('zoomed', (ev) => {
      // use fully visible day as start date
      const fromDate = this.asLocalDateString(
        ev.startDate.getHours() > 12 ? this.addDays(ev.startDate, 1) : ev.startDate
      );
      // use end day as end date
      const toDate = this.asLocalDateString(
        ev.endDate.getHours() < 12 ? this.addDays(ev.endDate, -1) : ev.endDate
      );
      zoomedSubject.next({fromDate, toDate});
    });
    zoomedSubject
      .asObservable()
      .debounce(() => Observable.timer(500))
      .subscribe(ev => this.ngZone.run(() => this.zoomed.emit(ev)));

    let zoomInitialized = false;
    this.chart.addListener('dataUpdated', ev => {
      if (!zoomInitialized) {
        zoomInitialized = true;
        const historyLength = ev.chart.dataProvider.length;
        this.chart.zoomToIndexes(historyLength - 8, historyLength - 1);
        this.chart.zoomOutValueAxes();
      }
    });

    this.chart.validateData();
  }

  private graphDefinition(fieldId: string): any {
    return {
      id: fieldId,
      balloon: {
        drop: true,
        adjustBorderColor: false,
        color: '#ffffff'
      },
      bullet: 'round',
      bulletBorderAlpha: 1,
      bulletColor: '#fff',
      bulletSize: 5,
      hideBulletsCount: 50,
      lineThickness: 2,
      useLineColorForBulletBorder: true,
      valueField: fieldId
    };
  }

  private standardLinearChart(chartScrollbarGraph: string) {
    return {
      language: 'pl',
      type: 'serial',
      fontFamily: 'inherit',
      fontSize: '12',
      marginRight: 40,
      marginLeft: 40,
      creditsPosition: 'top-left',
      graphs: [],
      chartScrollbar: {
        graph: chartScrollbarGraph,
        oppositeAxis: true,
        offset: 30,
        scrollbarHeight: 40,
        backgroundAlpha: 0,
        selectedBackgroundAlpha: 1,
        selectedBackgroundColor: '#f5f5f5',
        graphFillAlpha: 0,
        graphLineAlpha: 0.5,
        selectedGraphFillAlpha: 0,
        selectedGraphLineAlpha: 1,
        autoGridCount: true,
        color: '#aaaaaa'
      },
      chartCursor: {
        pan: true,
        valueLineEnabled: true,
        valueLineBalloonEnabled: true,
        cursorAlpha: 1,
        cursorColor: '#258cbb',
        valueLineAlpha: 0.2,
        valueZoomable: true
      },
      categoryField: 'date'
    };
  }

  private mapEventToGuide(event: GroupEvent) {
    this.colorCounter = (this.colorCounter + 1) % GroupViolationsHistoryComponent.PALETTE.length;
    return {
      date: event.type === 'period' ? event.data.startDate : event.data.date,
      toDate: event.type === 'period' ? event.data.endDate : undefined,
      lineColor: GroupViolationsHistoryComponent.PALETTE[this.colorCounter].line,
      lineThickness: 1.2,
      lineAlpha: 1,
      boldLabel: event.type === 'period',
      fillAlpha: 0.2,
      fillColor: GroupViolationsHistoryComponent.PALETTE[this.colorCounter].fill,
      dashLength: 5,
      inside: true,
      labelRotation: event.type === 'period' ? 360 : 90,
      label: event.name
    };
  }

  private addDays(date: Date, days: number) {
    const plusDate = new Date(date);
    plusDate.setDate(plusDate.getDate() + days);
    return plusDate;
  }

  private asLocalDateString(date: Date): string {
    return date.toISOString().slice(0, 10);
  }

}
