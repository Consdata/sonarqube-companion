import {Component, OnDestroy, OnInit} from '@angular/core';

import {BaseComponent} from '../base-component';
import {GroupDetails} from './group-details';
import {GroupService} from './group-service';
import {ActivatedRoute} from '@angular/router';
import {ProjectViolationsHistoryService} from '../violations/project-violations-history-service';
import {ProjectViolationsHistory} from '../violations/project-violations-history';
import {AmChartsService} from '@amcharts/amcharts3-angular';

@Component({
  selector: 'sq-group',
  template: `
    <sq-spinner *ngIf="!group"></sq-spinner>
    <div *ngIf="group" class="group-sections">
      <h1>{{group.name}}</h1>
      <hr/>
      <div class="overview-cards">
        <div [class]="group.healthStatusString">
          <div class="overview-card">
            <div class="icon">
              <i class="fa fa-thumbs-o-up" *ngIf="!group.violations.hasRelevant()"></i>
              <i class="fa fa-thumbs-o-down" *ngIf="group.violations.hasRelevant()"></i>
            </div>
            <div class="details">
              <div class="value">{{group.healthStatusString}}</div>
              <div class="metric">overall score</div>
            </div>
          </div>
        </div>
        <div [class.success]="group.violations.blockers === 0" [class.danger]="group.violations.blockers > 0">
          <div class="overview-card">
            <div class="icon">
              <i class="fa fa-ban"></i>
            </div>
            <div class="details">
              <div class="value">{{group.violations.blockers}}</div>
              <div class="metric">blockers</div>
            </div>
          </div>
        </div>
        <div [class.success]="group.violations.criticals === 0" [class.warning]="group.violations.criticals > 0">
          <div class="overview-card">
            <div class="icon">
              <i class="fa fa-exclamation-circle"></i>
            </div>
            <div class="details">
              <div class="value">{{group.violations.criticals}}</div>
              <div class="metric">criticals</div>
            </div>
          </div>
        </div>
        <div class="gray">
          <div class="overview-card">
            <div class="icon">
              <i class="fa fa-info-circle"></i>
            </div>
            <div class="details">
              <div class="value">{{group.violations.nonRelevant}}</div>
              <div class="metric">other issues</div>
            </div>
          </div>
        </div>
        <div class="gray">
          <div class="overview-card">
            <div class="icon">
              <i class="fa fa-briefcase"></i>
            </div>
            <div class="details">
              <div class="value">{{group.projects.length}}</div>
              <div class="metric">projects</div>
            </div>
          </div>
        </div>
        <div class="gray">
          <div class="overview-card">
            <div class="icon">
              <i class="fa fa-folder-open-o"></i>
            </div>
            <div class="details">
              <div class="value">{{group.groups.length}}</div>
              <div class="metric">groups</div>
            </div>
          </div>
        </div>
      </div>
      <div>
        <h2>Violations</h2>
        <div *ngIf="!violationsHistory">
          <sq-spinner></sq-spinner>
        </div>
        <div [class.hidden]="!violationsHistory || violationsHistory.isEmpty()">
          <div>
            <span (click)="changeChartIssue('all')">all</span>
            |
            <span (click)="changeChartIssue('relevant')">relevant</span>
            |
            <span (click)="changeChartIssue('nonrelevant')">non relevant</span>
            |
            <span (click)="changeChartIssue('blockers')">blockers</span>
            |
            <span (click)="changeChartIssue('criticals')">criticals</span>
            |
            <span (click)="changeChartIssue('majors')">majors</span>
            |
            <span (click)="changeChartIssue('minors')">minors</span>
            |
            <span (click)="changeChartIssue('infos')">infos</span>
          </div>
          <div id="violations-history-chart"></div>
        </div>
      </div>
      <div>
        <h2>Groups</h2>
        <div *ngFor="let subGroup of group.groups">
          <a routerLink="/groups/{{subGroup.uuid}}">{{subGroup.name}}</a>
        </div>
      </div>
      <div>
        <h2>Projects</h2>
        <table class="projects-table">
          <tr class="project-header">
            <td>Name</td>
            <td>Key</td>
            <td>Blockers</td>
            <td>Criticals</td>
            <td>Other</td>
            <td>SonarQube</td>
          </tr>
          <tr *ngFor="let project of group.projects" [attr.health-status]="project.healthStatusString"
              class="project-row">
            <td>{{project.name}}</td>
            <td>{{project.key}}</td>
            <td>{{project.violations.blockers}}</td>
            <td>{{project.violations.criticals}}</td>
            <td>{{project.violations.nonRelevant}}</td>
            <td>{{project.serverId}}</td>
          </tr>
        </table>
      </div>
    </div>
  `,
  styles: [
    BaseComponent.DISPLAY_BLOCK
  ]
})
export class GroupComponent implements OnInit, OnDestroy {

  static readonly DEFAULT_GRAPH = 'all';

  group: GroupDetails;
  violationsHistory: ProjectViolationsHistory;
  private violationsHistoryChart: any;
  private currentGraph;

  constructor(private route: ActivatedRoute,
              private groupService: GroupService,
              private projectViolationsHistoryService: ProjectViolationsHistoryService,
              private amCharts: AmChartsService) {
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
      valueField: fieldId,
      balloonText: '<span class="history-chart-bullet">[[value]]</span>'
    };
  }

  ngOnInit(): void {
    const parmasSnapshot = this.route.snapshot.params;
    this.groupService
      .getGroup(parmasSnapshot.uuid)
      .subscribe(group => {
        this.group = group;
        this.projectViolationsHistoryService
          .getHistory(this.group.uuid)
          .subscribe(violationsHistory => {
            this.violationsHistory = violationsHistory;
            this.violationsHistoryChart = this.amCharts.makeChart('violations-history-chart', {
              language: 'pl',
              type: 'serial',
              fontFamily: 'inherit',
              fontSize: '12',
              marginRight: 40,
              marginLeft: 40,
              mouseWheelZoomEnabled: true,
              creditsPosition: 'bottom-right',
              graphs: [
                this.graphDefinition('all'),
                this.graphDefinition('blockers'),
                this.graphDefinition('criticals'),
                this.graphDefinition('majors'),
                this.graphDefinition('minors'),
                this.graphDefinition('infos'),
                this.graphDefinition('relevant'),
                this.graphDefinition('nonrelevant')
              ],
              chartScrollbar: {
                graph: GroupComponent.DEFAULT_GRAPH,
                oppositeAxis: false,
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
                color: '#AAAAAA'
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
              categoryField: 'date',
              categoryAxis: {
                parseDates: true,
                dashLength: 1,
                minorGridEnabled: true
              },
              dataProvider: this.violationsHistory.history
            });

            this.currentGraph = GroupComponent.DEFAULT_GRAPH;
            this.violationsHistoryChart.hideGraph(this.violationsHistoryChart.getGraphById('blockers'));
            this.violationsHistoryChart.hideGraph(this.violationsHistoryChart.getGraphById('criticals'));
            this.violationsHistoryChart.hideGraph(this.violationsHistoryChart.getGraphById('majors'));
            this.violationsHistoryChart.hideGraph(this.violationsHistoryChart.getGraphById('minors'));
            this.violationsHistoryChart.hideGraph(this.violationsHistoryChart.getGraphById('infos'));
            this.violationsHistoryChart.hideGraph(this.violationsHistoryChart.getGraphById('relevant'));
            this.violationsHistoryChart.hideGraph(this.violationsHistoryChart.getGraphById('nonrelevant'));

            this.violationsHistoryChart.addListener('dataUpdated', (ev) => {
              const historyLength = this.violationsHistory.history.length;
              this.violationsHistoryChart.zoomToIndexes(historyLength - 8, historyLength - 1);
            });
            this.violationsHistoryChart.validateData();
          });
      });
  }

  changeChartIssue(issueType: string): void {
    if (this.violationsHistoryChart) {
      this.amCharts.updateChart(this.violationsHistoryChart, () => {
        // hide old graph
        this.violationsHistoryChart.hideGraph(this.violationsHistoryChart.getGraphById(this.currentGraph));

        // change graph references to requested graph
        const graphToSelect = this.violationsHistoryChart.getGraphById(issueType);
        this.violationsHistoryChart.showGraph(graphToSelect);
        this.violationsHistoryChart.chartScrollbar.graph = graphToSelect;

        // store requsted graph as current
        this.currentGraph = issueType;
      });
    }
  }

  ngOnDestroy(): void {
    if (this.violationsHistoryChart) {
      this.amCharts.destroyChart(this.violationsHistoryChart);
    }
  }

}
