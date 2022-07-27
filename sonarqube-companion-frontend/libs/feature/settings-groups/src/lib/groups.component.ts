import {ChangeDetectionStrategy, Component} from '@angular/core';
import {Store} from '@ngrx/store';
import {map} from 'rxjs/operators';
import {GroupLightModel} from '@sonarqube-companion-frontend/data-access/groups';
import {SideTreeItem} from '@sonarqube-companion-frontend/ui/side-tree';
import {State} from './state/groups.reducer';
import {selectRootGroup} from './state/groups.selectors';

@Component({
  selector: 'sqc-groups',
  template: `
    <sqc-side-tree *ngIf="rootGroup$ | async as rootGroup" [root]="rootGroup">

    </sqc-side-tree>
  `,
  styleUrls: ['./groups.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class GroupsComponent {
  rootGroup$ = this.store.select(selectRootGroup).pipe(map((group) => this.asSideTreeRoot(group)));

  constructor(private store: Store<State>) {
  }

  private asSideTreeRoot(group: GroupLightModel): SideTreeItem | null {
    if (group) {
      const children: SideTreeItem[] = group.groups ? group.groups.map(g => this.asSideTreeItem(g)).filter(g => g != null) : [];
      return {
        data: group,
        label: group.name,
        children: children
      }
    } else {
      return null;
    }
  }

  private asSideTreeItem(group: GroupLightModel): SideTreeItem {
    const children: SideTreeItem[] = group && group.groups ? group.groups.map(g => this.asSideTreeItem(g)).filter(g => g != null) : [];
    return {
      data: group,
      label: group.name,
      children: children
    }
  }
}
