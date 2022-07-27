import {ChangeDetectionStrategy, Component, EventEmitter, Input, Output} from '@angular/core';
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from '@angular/material/tree';
import {SideTreeItem} from './side-tree-item';


@Component({
  selector: 'sqc-side-tree',
  template: `
    <div class="wrapper">
      <div class="header">
        <div class="title">{{ title }}</div>
        <button
          mat-button
          class="back"
          *ngIf="showBackButton"
        >
          <mat-icon>arrow_back</mat-icon>
        </button>
      </div>
      <mat-divider></mat-divider>
      <mat-tree
        [dataSource]="dataSource"
        [treeControl]="treeControl"
        class="tree"
      >
        <mat-tree-node *matTreeNodeDef="let node" matTreeNodeToggle>
          <sqc-side-tree-item
            [item]="node"
            (click)="itemClick.emit(node)"
          >
          </sqc-side-tree-item>
        </mat-tree-node>
        <mat-nested-tree-node *matTreeNodeDef="let node; when: hasChild">
          <div class="mat-tree-node">
            <mat-icon class="mat-icon-rtl-mirror expander" matTreeNodeToggle>
              {{ treeControl.isExpanded(node) ? 'expand_more' : 'chevron_right' }}
            </mat-icon>
            <sqc-side-tree-item
              [item]="node"
              (click)="itemClick.emit(node)"
            >
            </sqc-side-tree-item>
          </div>
          <div
            [class.tree-invisible]="!treeControl.isExpanded(node)"
            role="group"
          >
            <ng-container matTreeNodeOutlet></ng-container>
          </div>
        </mat-nested-tree-node>
      </mat-tree>
    </div>
  `,
  styleUrls: ['./side-tree.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class SideTreeComponent {
  treeControl = new NestedTreeControl<SideTreeItem>((node) => node.children);
  dataSource = new MatTreeNestedDataSource<SideTreeItem>();

  @Input()
  title = '';

  @Input()
  showBackButton = false;

  @Output()
  itemClick = new EventEmitter<SideTreeItem>();

  @Input()
  set root(rootItem: SideTreeItem) {
    this.dataSource.data = [rootItem];
  }

  hasChild = (_: number, node: SideTreeItem) =>
    !!node.children && node.children.length > 0;
}
