import {Component} from '@angular/core';

import {BaseComponent} from '../base-component';
import {GroupDetails} from './group-details';
import {GroupService} from './group-service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'sq-group',
  template: `
    <sq-spinner *ngIf="!group"></sq-spinner>
    <div *ngIf="group" class="group-sections">
      <h1>{{group.name}}</h1>
      <hr/>
      <div class="overview-cards">
        <sq-group-overview-cards [group]="group"></sq-group-overview-cards>
      </div>
      <div>
        <h2>Violations</h2>
        <sq-group-violations-history [uuid]="group.uuid"></sq-group-violations-history>
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
            <td><a [href]="project.url">{{project.serverId}}</a></td>
          </tr>
        </table>
      </div>
    </div>
  `,
  styles: [
    BaseComponent.DISPLAY_BLOCK
  ]
})
export class GroupComponent {

  group: GroupDetails;

  constructor(route: ActivatedRoute, groupService: GroupService) {
    route
      .paramMap
      .switchMap(params => groupService.getGroup(params.get('uuid')))
      .subscribe(group => this.group = group);
  }

}
