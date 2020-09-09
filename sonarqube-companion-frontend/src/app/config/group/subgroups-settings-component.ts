import {Component, Input, ViewChild} from '@angular/core';
import {GroupDefinition} from '../model/group-definition';
import {ActivatedRoute, Router} from '@angular/router';
import {TreeComponent} from '@circlon/angular-tree-component';
import {GroupSettingsService} from '../service/group-settings-service';


interface Node {
  id: string;
  name: string;
  children: Node[];
}

@Component({
  selector: `sq-settings-subgroups`,
  template: `
    <sq-spinner *ngIf="!loaded"></sq-spinner>
    <div *ngIf="loaded" class="sq-settings-container">
      <div class="header">
        <div class="sq-settings-group-title">
          <div class="title">Groups</div>
          <div class="actions">
            <button class="action" (click)="addChildToSelectedNode()">New child</button>
            <button *ngIf="selectedNode" class="action"
                    [routerLink]="selectedNodeUrl">Edit
            </button>
            <button *ngIf="selectedNode" class="action" (click)="removeSelectedNode()">Remove</button>
          </div>
        </div>
        <hr>
      </div>
      <div class="sq-setting-error">{{errorMessage}}</div>
      <div class="tree-wrapper">
        <div class="tree-phony-root" [class.selected]="!this.selectedNode" (click)="onTreePhonyRootClick()"
             (dblclick)="onTreePhonyRootDblClick()">..
        </div>
        <tree-root #tree
                   [nodes]="groups"
                   [options]="options"
                   (deactivate)="deactivate($event)"
                   (activate)="activate($event)"
                   (initialized)="expandAll()"
                   (moveNode)="onMove($event)"></tree-root>
      </div>
    </div>
  `
})
export class SubgroupsSettingsComponent {
  @ViewChild('tree')
  tree: TreeComponent;
  errorMessage = '';
  loaded: boolean = false;
  groups: GroupDefinition[];
  selectedNode: any;
  selectedNodeUrl: string = '';
  rootNodeUrl: string = '';

  constructor(private router: Router, private settingsService: GroupSettingsService, private activatedRoute: ActivatedRoute) {
    this.activatedRoute.params.subscribe(params => {
      settingsService.getParent(params['parentUuid']).subscribe(id => {
        if (id !== '') {
          this.rootNodeUrl = `/settings/group/${id}/${params['parentUuid']}`
        } else {
          this.rootNodeUrl = '/settings'
        }
      })
    });
  }

  _uuid: string;

  options = {
    allowDrag: (node) => {
      return true;
    },
    allowDrop: (node) => {
      return true;
    },
    actionMapping: {
      mouse: {
        dblClick: (tree, node, $event) => {
          this.router.navigateByUrl(`/settings/group/${node.isRoot ? this._uuid : node.parent.id}/${node.id}`)
        }
      }
    },
    childrenField: 'groups',
    idField: 'uuid'
  };

  @Input()
  set uuid(id: string) {
    this._uuid = id;
    this.reload();
  }

  deactivate(item: any): void {
    this.selectedNode = undefined;
    this.selectedNodeUrl = '';
  }

  activate(item: any): void {
    this.selectedNode = item.node;
    this.selectedNodeUrl = `/settings/group/${this.selectedNode.isRoot ? this._uuid : this.selectedNode.parent.id}/${this.selectedNode.id}`
  }

  removeSelectedNode() {
    this.loaded = false;
    const parentId = this.selectedNode.isRoot ? this._uuid : this.selectedNode.parent.id;
    this.settingsService.deleteGroup(this.selectedNode.id, parentId).subscribe(
      validationMessage => {
        if (validationMessage.valid) {
          this.reload();
        } else {
          this.loaded = true;
        }
        this.errorMessage = validationMessage.message;
      });
  }

  onMove(event: any): void {
    this.tree.treeModel.update();
    this.loaded = false;
    this.settingsService.updateSubgroups(this._uuid, this.groups).subscribe(
      validationMessage => {
        if (validationMessage.valid) {
          this.reload();
        } else {
          this.loaded = true;
        }
        this.errorMessage = validationMessage.message;
      });
  }

  reload() {
    this.loaded = false;
    this.settingsService.getSubgroups(this._uuid).subscribe(data => {
      this.groups = data;
      if (this.groups.length === 0) {
        this.onTreePhonyRootClick();
      }
      this.loaded = true;
    });
  }

  onTreePhonyRootDblClick(): void {
    this.router.navigateByUrl(this.rootNodeUrl);
  }

  onTreePhonyRootClick(): void {
    this.selectedNode = undefined;
    if (this.tree && this.tree.treeModel.getActiveNode()) {
      this.tree.treeModel.getActiveNode().setIsActive(false);
    }
  }

  addChildToSelectedNode(): void {
    this.loaded = false;
    const defaultUuid = '';
    const group = new GroupDefinition({uuid: defaultUuid, name: defaultUuid});
    const parentId = this.selectedNode ? this.selectedNode.id : this._uuid;

    this.settingsService.createGroup(parentId, group).subscribe(validationMessage => {
      if (validationMessage.valid) {
        this.reload();
      } else {
        this.loaded = true;
      }
      this.errorMessage = validationMessage.message;
    });
  }

  expandAll() {
    setTimeout(() => {
      this.tree.treeModel.expandAll();
    });
  }
}
