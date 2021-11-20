import {ChangeDetectionStrategy, Component, ViewChild} from '@angular/core';
import {Observable} from 'rxjs';
import {GroupService} from '@sonarqube-companion-frontend/group';
import {ActivatedRoute} from '@angular/router';
import {map} from 'rxjs/operators';
import {Member} from '@sonarqube-companion-frontend/member';
import {EventService} from '@sonarqube-companion-frontend/event';
import {DateRange} from '@sonarqube-companion-frontend/ui-components/time-select';
import {MatDrawer} from '@angular/material/sidenav';
import {DEFAULT_DATE_RANGE} from '../../../ui-components/time-select/src/lib/time-select/time-select.component';


@Component({
  selector: 'sqc-group-overview',
  template: `
    <ng-container *ngIf="(uuid$ | async) as uuid">
      <div class="wrapper">
        <div class="header-container">
          <div class="header">
            <div class="more">
              <button mat-button *ngIf="drawer.opened">
                <mat-icon class="settings" (click)="drawer.close()">arrow_back</mat-icon>
              </button>
              <sqc-group-structure-buttons *ngIf="!drawer.opened" [uuid]="(uuid$ | async) || ''" [range]="range"
                                           (select)="structureButtonClicked($event)"></sqc-group-structure-buttons>
            </div>
            <sqc-group-name [uuid]="uuid"></sqc-group-name>
            <div class="filters">
              <sqc-time-select (rangeChanged)="onRangeChanged($event)"></sqc-time-select>
            </div>
          </div>
          <mat-divider class="top-bar-divider"></mat-divider>
        </div>
        <div class="group">
          <mat-drawer-container [hasBackdrop]="false">
            <mat-drawer #drawer class="left-drawer" mode="over" position="start">
              <div class="projects" *ngIf="drawerSelector === 'projects'">
                <sqc-group-projects-summary [uuid]="uuid" [range]="range"></sqc-group-projects-summary>
              </div>
              <div class="members" *ngIf="drawerSelector === 'members'">
                <sqc-group-members-summary [uuid]="uuid" [range]="range"></sqc-group-members-summary>
              </div>
            </mat-drawer>
            <div class="overview">
              <div class="values">
                <sqc-group-severities [uuid]="uuid" [range]="range"></sqc-group-severities>
              </div>
              <div class="timeline">
                <mat-divider></mat-divider>
                <sqc-group-timeline [uuid]="uuid" [range]="range"></sqc-group-timeline>

              </div>
            </div>
          </mat-drawer-container>
        </div>
      </div>
    </ng-container>


  `,
  styleUrls: ['./group-overview.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GroupOverviewComponent {
  @ViewChild('drawer')
  drawer?: MatDrawer;

  range: DateRange = DEFAULT_DATE_RANGE;
  drawerSelector: string = ''
  items = ['Item 1', 'Item 2', 'Item 3', 'Item 4', 'Item 5'];
  uuid$: Observable<string> = this.route.params.pipe(map(params => params['groupId']));

  constructor(private groupService: GroupService, private route: ActivatedRoute, private eventService: EventService) {
  }


  onRangeChanged($event: DateRange): void {
    this.range = $event;
  }

  structureButtonClicked($event: string): void {
    this.drawerSelector = $event;
    this.drawer?.toggle();
  }
}
