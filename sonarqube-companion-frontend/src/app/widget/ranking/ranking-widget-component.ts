import {Component} from "@angular/core";
import {Widget} from "../widget-service";
import {RankingEntry, RankingModel} from "./ranking-model";
import {UserStatisticsService} from "../../statistics/user-statistics-service";
import {Observable} from "rxjs/Observable";
import {isNullOrUndefined} from "util";


@Component({
  selector: 'ranking-widget',
  template: `
    <h2>{{title}}</h2>
    <div class="ranking-widget">
      <div class="sk-rotating-plane" *ngIf="!entries"></div>
      <div class="ranking-wrapper" *ngIf="!!entries">
        <div class="ranking-header">
          <div class="ranking-header-element name">
            <div>Name</div>
          </div>
          <div *ngFor="let header of model.severity" class="ranking-header-element counter">
            <div>{{header}}</div>
          </div>
        </div>
        <div class="ranking-body"></div>
        <div *ngFor="let entry of entries;  let i=index"
             class="ranking-row"
             [class.blocker]="entry.blockers > 0"
             [class.critical]="entry.criticals > 0"
             [class.major]="entry.majors > 0"
             [class.minor]="entry.minors > 0"
             [class.info]="entry.infos > 0"
        >
          <a target="_blank" href="{{getSonarLink(entry.name)}}" *ngIf="!model.limit || i<model.limit">
            <div class="ranking-row-element name">
              <div>{{entry.name}}</div>
            </div>
            <div *ngFor="let header of model.severity" class="ranking-header-element counter">
              <div>{{entry[header]}}</div>
            </div>
          </a>
        </div>
      </div>
    </div>
  `,
})
export class RankingWidgetComponent extends Widget<RankingModel> {

  entries: RankingEntry[];
  title: string;

  compareEntries = function (a, b) {
    let result = 0;
    this.model.sort.forEach((value, index, array) => {
      const asc = value.startsWith("+") ? -1 : 1;
      const key = value.slice(1);
      if (result === 0) {
        if (a[key] > b[key]) {
          result = -1 * asc;
        } else if (a[key] < b[key]) {
          result = 1 * asc;
        }
      }
    });
    return result;
  };

  constructor(private userStatistics: UserStatisticsService) {
    super();
  }

  getStats(from: string, to: string): Observable<any[]> {
    return this.userStatistics.getUserStatistics(this.model.uuid, from, to);
  }

  getSonarLink(name: string): string {
    if (!isNullOrUndefined(this.model.server)) {
      return this.model.server +
        '/issues?authors=' + name +
        '&createdAfter=' + this.model.from +
        '&createdBefore=' + this.model.to +
        '&severities=' + this.getSeveritySqFilter(this.model.severity) +
        '&statuses=OPEN';
    }
  }

  getSeveritySqFilter(severities: string[]) {
    return severities.join(',').toUpperCase().replace(/S/g, '');
  }

  toRankingEntries(data: any) {
    this.entries = [];
    data.entries.forEach((entry, index, array) => {
      if (this.shouldIncludeEntry(entry)) {
        this.entries.push(new RankingEntry(entry));
      }
    });
    this.sortEntries();
  }

  shouldIncludeEntry(entry: any) {
    return (isNullOrUndefined(this.model.include) || this.model.include.length === 0 || this.model.include.includes(entry.name))
      && (isNullOrUndefined(this.model.exclude) || this.model.exclude.length === 0 || !this.model.exclude.includes(entry.name));
  }

  sortEntries() {
    this.entries.sort(this.compareEntries.bind(this));
  }

  resolveVariables(base: string): string {
    if (!isNullOrUndefined(base)) {
      return base.replace(/\${([^{]*)}/g,
        (match: string, mapping: string): string => {
          return this.model[mapping];
        });
    } else {
      return "";
    }
  }

  onEvent(event: any) {
    this.model.to = event.toDate;
    if (this.model.customFromDateEnabled) {
      this.model.from = event.fromDate;
    }
    this.title = this.resolveVariables(this.model.title);
    this.getStats(this.model.from, this.model.to).subscribe(stats => this.toRankingEntries(stats));
  }
}

