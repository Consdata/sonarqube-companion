import {AfterViewInit, Component} from '@angular/core';
import {Observable, ReplaySubject} from 'rxjs';
import {GroupLightModel} from '@sonarqube-companion-frontend/group-overview';
import {Select, Store} from '@ngxs/store';
import {AddChild, GroupsSettingsState, LoadRootGroup} from '../state/groups-settings-state';
import {Router} from '@angular/router';
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from '@angular/material/tree';
import {CdkDragDrop} from '@angular/cdk/drag-drop';
import {GroupDetails} from '@sonarqube-companion-frontend/group';
import {ActionsExecuting, actionsExecuting} from '@ngxs-labs/actions-executing';

@Component({
  selector: 'sqc-groups',
  template: `
    <div *ngIf="actionsExecuting$ | async">
      asddasasdasdadsdas
      <mat-spinner></mat-spinner>
    </div>
    <ng-container *ngIf="!(actionsExecuting$ | async)">
      <ng-container *ngIf="vm$ | async as vm">
        <mat-tree [dataSource]="getDataSource(vm)" [treeControl]="treeControl" class="example-tree" cdkDropList
                  (cdkDropListDropped)="drop($event)">
          <mat-tree-node *matTreeNodeDef="let node" matTreeNodeToggle cdkDrag [cdkDragData]="node"
                         hover-class="hover">
            <ng-container *ngTemplateOutlet="nodeTpl; context: {node: node}"></ng-container>
          </mat-tree-node>
          <mat-nested-tree-node *matTreeNodeDef="let node; when: hasChild" cdkDrag [cdkDragData]="node"
          >
            <div class="mat-tree-node" hover-class="hover">
              <button mat-icon-button matTreeNodeToggle
                      [attr.aria-label]="'Toggle ' + node.name">
                <mat-icon class="mat-icon-rtl-mirror">
                  {{treeControl.isExpanded(node) ? 'expand_more' : 'chevron_right'}}
                </mat-icon>
              </button>
              <ng-container *ngTemplateOutlet="nodeTpl; context: {node: node}"></ng-container>
            </div>
            <div [class.example-tree-invisible]="!treeControl.isExpanded(node)"
                 role="group">
              <ng-container matTreeNodeOutlet></ng-container>
            </div>
          </mat-nested-tree-node>

          <ng-template #nodeTpl let-node='node'>
            <div class="item" (click)="navigateToGroup(node)">
              <div class="left">
                <div class="name">{{node.name}}</div>
                <div class="description" *ngIf="node.description">
                  <mat-divider [vertical]="true" class="sep"></mat-divider>
                  <div>{{node.description}}</div>
                </div>
              </div>
              <div class="right">
                <mat-divider [vertical]="true" class="sep"></mat-divider>
                <div class="projects stat">
                  <div class="count">{{node.projects}}</div>
                  <mat-icon>code</mat-icon>
                </div>
                <div class="members stat">
                  <div class="count">{{node.members}}</div>
                  <mat-icon>group</mat-icon>
                </div>
                <div class="events stat">
                  <div class="count">{{node.events}}</div>
                  <mat-icon>event</mat-icon>
                </div>
                <mat-divider [vertical]="true" class="sep"></mat-divider>
              </div>
            </div>
            <div class="actions">
              <div [matTooltip]="'Add child'" (click)="addChild(node)">
                <mat-icon>add</mat-icon>
              </div>
            </div>
          </ng-template>
        </mat-tree>
      </ng-container>
    </ng-container>
  `,
  styleUrls: ['./groups.component.scss']
})
export class GroupsComponent implements AfterViewInit {
  subject: ReplaySubject<void> = new ReplaySubject<void>();

  @Select(GroupsSettingsState.rootGroup)
  vm$!: Observable<GroupDetails>;

  @Select(actionsExecuting([AddChild, LoadRootGroup])) actionsExecuting$!: Observable<ActionsExecuting>;


  treeControl = new NestedTreeControl<GroupDetails>(node => node.groups);
  dataSource = new MatTreeNestedDataSource<GroupDetails>();

  constructor(private store: Store, private router: Router) {
  }

  hasChild = (_: number, node: GroupDetails) => !!node.groups && node.groups.length > 0;

  ngAfterViewInit(): void {
    this.store.dispatch(new LoadRootGroup());
  }

  getDataSource(config: GroupDetails): MatTreeNestedDataSource<GroupDetails> {
    this.dataSource.data = [config];
    return this.dataSource;
  }

  uuid(index: number, group: GroupLightModel): string {
    return group.uuid;
  }

  addChild(parent: GroupDetails): void {
    this.store.dispatch(new AddChild(parent.uuid));
  }

  save(): void {
    this.subject.next();
  }

  delete(): void {
    this.subject.next();
  }

  isAddGroupAllowed(): boolean {
    // return this.spinnerService.isUnlocked(Locks.ADD_SERVER);
    return false;
  }

  onSelect(group: GroupLightModel) {

  }

  navigate(group: GroupLightModel): void {
    this.router.navigate(['settings', 'groups', group.uuid])
  }

  addGroup(): void {

  }

  drop($event: CdkDragDrop<GroupDetails, any>) {

  }

  navigateToGroup(node: GroupDetails): void {
    this.router.navigate(['settings', 'group', node.parentUuid, node.uuid])
  }

}
