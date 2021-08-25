import {ChangeDetectionStrategy, Component} from '@angular/core';
import {combineLatest, Observable} from 'rxjs';
import {GroupDetails, GroupService} from '@sonarqube-companion-frontend/group';
import {ActivatedRoute} from '@angular/router';
import {distinctUntilChanged, map, switchMap} from 'rxjs/operators';
import {GroupViolationsHistory} from '../../../group/src/lib/group-violations-history';
import {ProjectViolationsHistoryDiff} from '@sonarqube-companion-frontend/project';
import {ViolationsTableItem} from '../../../ui-components/table/src/lib/table/violations-table.component';
import {Member, MemberViolationsHistoryDiff} from '@sonarqube-companion-frontend/member';
import {SelectItem} from '@sonarqube-companion-frontend/ui-components/select';
import {Violations} from '@sonarqube-companion-frontend/group-overview';
import {TimelineSeries} from '../../../ui-components/timeline/src/lib/timeline';
import {Event, EventService} from '@sonarqube-companion-frontend/event';

interface GroupOverviewModel {
  details: GroupDetails;
  violationsHistory: GroupViolationsHistory;
  projectsDiff: ProjectViolationsHistoryDiff[];
  membersDiff: MemberViolationsHistoryDiff[];
  members: Member[];
  violations: Violations;
  events: Event[];
}

// TODO pociąć ###################SDJLADSJDLKASJLKDJASLDJLKASJDLAKJSDLKSAJKLDJASLDJLAKSJDLKASJDLASJDLJAS <+++++++++++++++++++++++++++++++
@Component({
  selector: 'sqc-group-overview',
  template: `
    <div class="wrapper" *ngIf="vm$ | async as vm">
      <div class="header-container">
        <div class="header">
          <div class="label">{{vm.details.name}}</div>
          <div class="more" *ngIf="!drawer.opened">
            <mat-divider vertical></mat-divider>
            <button mat-button (click)="drawerSelector='projects'; drawer.toggle()" matTooltip="Projects">
              <div class="item">
                <span class="label">{{vm.details.projects}}</span>
                <mat-icon class="settings">code</mat-icon>
              </div>
            </button>
            <mat-divider vertical></mat-divider>
            <button mat-button (click)="drawerSelector='members'; drawer.toggle()" matTooltip="Members">
              <div class="item">
                <span class="label">{{vm.details.members}}</span>
                <mat-icon class="settings">group</mat-icon>
              </div>
            </button>
            <mat-divider vertical></mat-divider>
            <button mat-button (click)="drawerSelector='events'; drawer.toggle()" matTooltip="Events">
              <div class="item">
                <span class="label">{{vm.details.events}}</span>
                <mat-icon class="settings">event</mat-icon>
              </div>
            </button>
          </div>
          <div class="more" *ngIf="drawer.opened">
            <mat-divider vertical></mat-divider>
            <button mat-button (click)="drawerSelector=''; drawer.toggle()">
              <div class="item">
                <mat-icon class="settings">close</mat-icon>
              </div>
            </button>
          </div>
        </div>
        <mat-divider class="top-bar-divider"></mat-divider>
      </div>
      <div class="group">
        <mat-drawer-container autosize [hasBackdrop]="false">
          <mat-drawer #drawer class="example-sidenav" mode="over" position="end">

            <div class="projects" *ngIf="drawerSelector === 'projects'">
              <sqc-violations-table [nameAlias]="'Project'"
                                    [data]="asViolationsTableItems(vm.projectsDiff)"></sqc-violations-table>
            </div>
            <div class="members" *ngIf="drawerSelector === 'members'">
              <sqc-violations-table [nameAlias]="'Member'"
                                    [data]="membersDiffAsViolationsTableItems(vm.membersDiff)"></sqc-violations-table>
            </div>
            <div class="events" *ngIf="drawerSelector === 'events'">
              <sqc-violations-table [nameAlias]="'Member'"
                                    [data]="membersDiffAsViolationsTableItems(vm.membersDiff)"></sqc-violations-table>
            </div>
          </mat-drawer>
          <div class="overview">
            <div class="values">
              <sqc-value-badge [priority]="'urgent'" [label]="'blockers'">{{vm.violations.blockers}}</sqc-value-badge>
              <sqc-value-badge [priority]="'warning'"
                               [label]="'criticals'">{{vm.violations.criticals}}</sqc-value-badge>
              <sqc-value-badge [priority]="''" [label]="'majors'">{{vm.violations.majors}}</sqc-value-badge>
              <sqc-value-badge [priority]="''" [label]="'minors'">{{vm.violations.minors}}</sqc-value-badge>
              <sqc-value-badge [priority]="''" [label]="'infos'">{{vm.violations.infos}}</sqc-value-badge>
            </div>
            <div class="timeline">
              <mat-divider></mat-divider>
              <div class="filters">
                <sqc-select [text]="'severity'" [items]="[]"></sqc-select>
                <sqc-select [text]="'project'" [items]="[]"></sqc-select>
                <sqc-select [text]="'member'" [items]="membersAsSelectItems(vm.members)"></sqc-select>
              </div>
              <sqc-timeline [series]="asSeries(vm.violationsHistory, vm.events || [])"></sqc-timeline>
            </div>
          </div>
        </mat-drawer-container>
      </div>
    </div>
  `,
  styleUrls: ['./group-overview.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GroupOverviewComponent {
  drawerSelector: string = ''
  //@ts-ignore //TODO fix
  vm$: Observable<GroupOverviewModel> = this.route.params.pipe(
    map(params => params['groupId']),
    distinctUntilChanged(),
    switchMap(id =>
      combineLatest([
        this.groupService.groupDetails(id),
        this.groupService.groupViolationsHistory(id, 30),
        this.groupService.groupProjectsHistoryDiff(id, 30),
        this.groupService.groupMembersHistoryDiff(id, 30),
        this.groupService.members(id),
        this.groupService.violations(id),
        this.eventService.getByGroup(id, 30)
      ]).pipe(
        map(([
               details,
               violationsHistory,
               projectsDiff,
               membersDiff,
               members,
               violations,
               events
             ]) => ({
          details: details,
          violationsHistory: violationsHistory,
          projectsDiff: projectsDiff,
          membersDiff: membersDiff,
          members: members,
          violations: violations,
          events: events
        }))
      )
    )
  );

  constructor(private groupService: GroupService, private route: ActivatedRoute, private eventService: EventService) {
  }

  membersAsSelectItems(members: Member[]): SelectItem[] {
    if (members) {
      return members.map(item => ({id: item.uuid, text: `${item.firstName} ${item.lastName}`}));
    } else {
      return [];
    }
  }

  asViolationsTableItems(projectsHistory: ProjectViolationsHistoryDiff[]): ViolationsTableItem[] {
    return projectsHistory.map(entry => ({
      uuid: entry.projectKey,
      name: entry.projectKey,
      blockers: {count: entry.violationsDiff.blockers, diff: 0},
      criticals: {count: entry.violationsDiff.criticals, diff: 0},
      majors: {count: entry.violationsDiff.majors, diff: 0},
      minors: {count: entry.violationsDiff.minors, diff: 0},
      infos: {count: entry.violationsDiff.infos, diff: 0},
    }));
  }

  membersDiffAsViolationsTableItems(projectsHistory: MemberViolationsHistoryDiff[]): ViolationsTableItem[] {
    return projectsHistory.map(entry => ({
      uuid: entry.uuid,
      name: entry.name,
      blockers: {count: entry.violationsDiff.blockers, diff: 0},
      criticals: {count: entry.violationsDiff.criticals, diff: 0},
      majors: {count: entry.violationsDiff.majors, diff: 0},
      minors: {count: entry.violationsDiff.minors, diff: 0},
      infos: {count: entry.violationsDiff.infos, diff: 0},
    }));
  }

  asSeries(violationsHistory: GroupViolationsHistory, events: Event[]): TimelineSeries {
    if (violationsHistory) {
      return {
        events: events.map(item => ({name: item.name, fromDate: new Date(item.dateString)})),
        data: violationsHistory.violationHistoryEntries.map(entry => ({
          date: new Date(entry.dateString),
          value: entry.violations.blockers + entry.violations.criticals + entry.violations.majors + entry.violations.minors + entry.violations.infos
        }))
      }
    }
    return {};
  }
}
