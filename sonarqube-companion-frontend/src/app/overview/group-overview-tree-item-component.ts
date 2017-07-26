import {Component, HostBinding, Input} from '@angular/core';
import {BaseComponent} from '../base-component';
import {GroupOverview} from '../group/group-overview';

@Component({
  selector: 'sq-overview-tree-item',
  template: `
    <a routerLink="/groups/{{group.uuid}}">
      <div class="group">
        <div class="name">{{group.name}}</div>
        <div class="description" *ngIf="group.description">{{group.description}}</div>
        <div class="expander"></div>
        <div>
          <i class="fa fa-arrow-circle-right"></i>
        </div>
      </div>
    </a>
    <div class="subgroups">
      <sq-overview-tree-item [group]="subGroup" *ngFor="let subGroup of group.groups"></sq-overview-tree-item>
    </div>
  `,
  styles: [
    BaseComponent.DISPLAY_BLOCK
  ]
})
export class GroupOverviewTreeItemComponent {
  @Input() group: GroupOverview;

  @HostBinding('attr.health-status')
  get attrHealthStatus() {
    return this.group.healthStatusString;
  }
}
