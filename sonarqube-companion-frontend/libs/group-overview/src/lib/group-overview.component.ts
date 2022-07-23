import { ChangeDetectionStrategy, Component, ViewChild } from '@angular/core';
import { Observable } from 'rxjs';
import { GroupService } from '@sonarqube-companion-frontend/group';
import { ActivatedRoute } from '@angular/router';
import { map } from 'rxjs/operators';
import { EventService } from '@sonarqube-companion-frontend/event';
import { DateRange } from '@sonarqube-companion-frontend/ui-components/time-select';
import { MatDrawer } from '@angular/material/sidenav';
import { DEFAULT_DATE_RANGE } from '../../../ui-components/time-select/src/lib/time-select/time-select.component';

@Component({
  selector: 'sqc-group-overview',
  template: `
    <ng-container *ngIf="uuid$ | async as uuid">
      <div class="group">
        <div class="header-container">
          <div class="header">
            <sqc-group-name [uuid]="uuid"></sqc-group-name>
            <div class="filters">
              <sqc-time-select
                (rangeChanged)="onRangeChanged($event)"
              ></sqc-time-select>
            </div>
          </div>
        </div>
        <div class="tabs">
          <mat-tab-group>
            <mat-tab label="Overview">
              <div class="overview">
                <div class="values">
                  <sqc-group-severities
                    [uuid]="uuid"
                    [range]="range"
                  ></sqc-group-severities>
                </div>
                <div class="timeline">
                  <sqc-group-timeline
                    [uuid]="uuid"
                    [range]="range"
                  ></sqc-group-timeline>
                </div>
              </div>
            </mat-tab>
            <mat-tab label="Members">
              <sqc-group-members-summary
                [uuid]="uuid"
                [range]="range"
              ></sqc-group-members-summary>
            </mat-tab>
            <mat-tab label="Projects">
              <sqc-group-projects-summary
                [uuid]="uuid"
                [range]="range"
              ></sqc-group-projects-summary>
            </mat-tab>
          </mat-tab-group>
        </div>
      </div>
    </ng-container>
  `,
  styleUrls: ['./group-overview.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GroupOverviewComponent {
  @ViewChild('drawer')
  drawer?: MatDrawer;

  range: DateRange = DEFAULT_DATE_RANGE;
  drawerSelector: string = '';
  uuid$: Observable<string> = this.route.params.pipe(
    map((params) => params['groupId'])
  );

  constructor(
    private groupService: GroupService,
    private route: ActivatedRoute,
    private eventService: EventService
  ) {}

  onRangeChanged($event: DateRange): void {
    this.range = $event;
  }

  structureButtonClicked($event: string): void {
    this.drawerSelector = $event;
    this.drawer?.toggle();
  }
}
