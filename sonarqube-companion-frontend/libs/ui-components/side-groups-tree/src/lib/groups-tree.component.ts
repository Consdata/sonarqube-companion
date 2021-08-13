import {ChangeDetectionStrategy, Component, EventEmitter, Input, Output} from '@angular/core';
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from '@angular/material/tree';
import {GroupLightModel} from '@sonarqube-companion-frontend/group-overview';

@Component({
  selector: 'sqc-side-groups-tree',
  template: `
    <mat-tree [dataSource]="dataSource" [treeControl]="treeControl" class="tree">
      <mat-tree-node *matTreeNodeDef="let node" matTreeNodeToggle>
        <sqc-side-groups-tree-item [item]="node" mat-ripple (click)="groupSelect.emit(node)">
        </sqc-side-groups-tree-item>
      </mat-tree-node>
      <mat-nested-tree-node *matTreeNodeDef="let node; when: hasChild">
        <div class="mat-tree-node" >
          <mat-icon class="mat-icon-rtl-mirror expander" matTreeNodeToggle>
            {{treeControl.isExpanded(node) ? 'expand_more' : 'chevron_right'}}
          </mat-icon>
          <sqc-side-groups-tree-item [item]="node" mat-ripple  (click)="groupSelect.emit(node)">
          </sqc-side-groups-tree-item>
        </div>
        <div [class.tree-invisible]="!treeControl.isExpanded(node)"
             role="group">
          <ng-container matTreeNodeOutlet></ng-container>
        </div>
      </mat-nested-tree-node>
    </mat-tree>
  `,
  styleUrls: ['./groups-tree.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GroupsTreeComponent {

  treeControl = new NestedTreeControl<GroupLightModel>(node => node.groups);
  dataSource = new MatTreeNestedDataSource<GroupLightModel>();

  @Output()
  groupSelect: EventEmitter<GroupLightModel> = new EventEmitter<GroupLightModel>();

  constructor() {
  }

  @Input()
  set rootGroup(rootGroup: GroupLightModel) {
    this.dataSource.data = [rootGroup];
  }

  hasChild = (_: number, node: GroupLightModel) => !!node.groups && node.groups.length > 0;
}
