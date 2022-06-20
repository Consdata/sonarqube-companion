import {AfterViewInit, Component} from '@angular/core';
import {Observable} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {GroupConfig} from '../model/group-config';
import {Select, Store} from '@ngxs/store';
import {ActionsExecuting, actionsExecuting} from '@ngxs-labs/actions-executing';
import {DeleteGroup, GroupSettingsState, LoadGroup} from '../state/group-settings-state';
import {GroupsConfigService} from '../service/groups-config.service';

@Component({
  selector: 'sqc-group',
  template: `
    <div *ngIf="actionsExecuting$ | async">
      <mat-spinner></mat-spinner>
    </div>
    <ng-container *ngIf="!(actionsExecuting$ | async)">
      <ng-container *ngIf="group$ | async as group">
        <div class="crumbs">
          <span>{{group.name}}</span>
          <div class="delete">
            <mat-divider [vertical]="true"></mat-divider>
            <mat-icon class="icon" (click)="delete(group)">save</mat-icon>
            <mat-icon class="icon" (click)="delete(group)">delete</mat-icon>
          </div>
        </div>
        <mat-divider></mat-divider>
        <div class="group">
          <div class="name">
            <input [value]="group.name" [placeholder]="'name'"/>
          </div>
          <mat-divider></mat-divider>
          <div class="description">
            <textarea [value]="group.description" [placeholder]="'description'"></textarea>
          </div>
          <mat-divider></mat-divider>
        </div>
        <cdk-accordion class="example-accordion">
          <cdk-accordion-item
            #projects="cdkAccordionItem"
            class="example-accordion-item"
            role="button"
            tabindex="0"
            [attr.id]="'projects-header'"
            [attr.aria-expanded]="projects.expanded"
            [attr.aria-controls]="'projects-body'">
            <div class="example-accordion-item-header" (click)="projects.toggle()">
              Projects
              <span class="example-accordion-item-description">
        Click to {{ projects.expanded ? 'close' : 'open' }}
      </span>
            </div>
            <div
              class="example-accordion-item-body"
              role="region"
              [style.display]="projects.expanded ? '' : 'none'"
              [attr.id]="'projects-body'"
              [attr.aria-labelledby]="'projects-header'">
              <sqc-group-projects></sqc-group-projects>
            </div>
          </cdk-accordion-item>
        </cdk-accordion>
      </ng-container>
    </ng-container>
  `,
  styleUrls: ['./group.component.scss']
})
export class GroupComponent implements AfterViewInit {
  items = ['Projects', 'Members', 'Events'];

  @Select(GroupSettingsState.group)
  group$!: Observable<GroupConfig>;

  @Select(actionsExecuting([DeleteGroup, LoadGroup]))
  actionsExecuting$!: Observable<ActionsExecuting>;

  parentUuid: string = '';

  constructor(private route: ActivatedRoute, private store: Store, private groupService: GroupsConfigService, private router: Router) {
  }

  ngAfterViewInit(): void {
    this.route.params.subscribe(params => {
      this.parentUuid = params['parentId'];
      this.store.dispatch(new LoadGroup(params['groupId']))
    });
  }

  delete(group: GroupConfig): void {
    this.store.dispatch(new DeleteGroup(this.parentUuid, group.uuid)).subscribe(() =>
      this.router.navigate(['settings', 'groups']))
  }
}
