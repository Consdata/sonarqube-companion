import {Component, OnInit} from '@angular/core';

import {BaseComponent} from '../base-component';
import {GroupDetails} from './group-details';
import {GroupService} from './group-service';
import {ActivatedRoute} from '@angular/router';
import {ProjectViolationsHistoryService} from '../violations/project-violations-history-service';
import {ProjectViolationsHistory} from '../violations/project-violations-history';

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
        <div *ngIf="violationsHistory">
          <pre>{{violationsHistory | json}}</pre>
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
          <tr *ngFor="let project of group.projects" [attr.health-status]="project.healthStatusString" class="project-row">
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
export class GroupComponent implements OnInit {

  group: GroupDetails;
  violationsHistory: ProjectViolationsHistory;

  constructor(
    private route: ActivatedRoute,
    private groupService: GroupService,
    private projectViolationsHistoryService: ProjectViolationsHistoryService) {
  }

  ngOnInit(): void {
    const parmasSnapshot = this.route.snapshot.params;
    this.groupService
      .getGroup(parmasSnapshot.uuid)
      .subscribe(group => {
        this.group = group
        this.projectViolationsHistoryService
          .getHistory(this.group.uuid)
          .subscribe(violationsHistory => this.violationsHistory = violationsHistory);
      });
  }

}
