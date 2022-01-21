import {Component} from '@angular/core';
import {Observable} from 'rxjs';
import {map, switchMap} from 'rxjs/operators';
import {ActivatedRoute} from '@angular/router';
import {GroupConfig} from '../model/group-config';
import {GroupsConfigService} from '../service/groups-config.service';

@Component({
  selector: 'sqc-group',
  template: `
    <ng-container *ngIf="group$ | async as group">
      <div class="crumbs">
        <div class="span">Path: </div>
        <sqc-crumbs [uuid]="group.uuid"></sqc-crumbs>
      </div>
      <mat-divider></mat-divider>
      <div class="group">
        <div class="name">
          <sqc-input [value]="group.name"></sqc-input>
        </div>
        <div class="description">
          <sqc-input [value]="group.description"></sqc-input>
        </div>
        <div class="projects">
        </div>
        <div class="members"></div>
        <div class="events"></div>
        <div class="subgroups"></div>
      </div>
    </ng-container>
  `,
  styleUrls: ['./group.component.scss']
})
export class GroupComponent {
  group$: Observable<GroupConfig> = this.route.params.pipe(
    map(params => params['groupId']),
    switchMap(uuid => this.groupService.get(uuid))
  );

  constructor(private route: ActivatedRoute, private groupService: GroupsConfigService) {
  }


}
