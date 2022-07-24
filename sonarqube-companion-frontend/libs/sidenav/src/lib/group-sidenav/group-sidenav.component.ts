import {ChangeDetectionStrategy, Component} from '@angular/core';
import {Observable} from 'rxjs';
import {GroupLightModel} from '@sonarqube-companion-frontend/group-overview';
import {OverviewService} from '../../../../overview/src/lib/overview.service';
import {Router} from '@angular/router';
import {map} from 'rxjs/operators';
import {SideTreeItem} from '@sonarqube-companion-frontend/ui/side-tree';

@Component({
  selector: 'sqc-group-sidenav',
  template: `
    <mat-sidenav-container>
      <mat-sidenav opened mode="side" [disableClose]="true">
        <div class="wrapper">
          <mat-divider></mat-divider>
          <mat-nav-list>
            <sqc-side-tree
              *ngIf="rootGroup$ | async as root"
              [root]="root"
              [title]="'Groups'"
              (itemClick)="onSelect($event)"
            ></sqc-side-tree>
            <mat-divider></mat-divider>
            <!--            <mat-list-item>-->
            <!--              <div class="section">-->
            <!--                <mat-icon class="icon">code</mat-icon>-->
            <!--                <div class="label">Projects</div>-->
            <!--                <mat-divider></mat-divider>-->
            <!--              </div>-->
            <!--            </mat-list-item>-->
            <!--            <mat-list-item>-->
            <!--              <div class="section">-->
            <!--                <mat-icon class="icon">people</mat-icon>-->
            <!--                <div class="label">People</div>-->
            <!--                <mat-divider></mat-divider>-->
            <!--              </div>-->
            <!--            </mat-list-item>-->
          </mat-nav-list>
        </div>
      </mat-sidenav>
      <mat-sidenav-content>
        <ng-content></ng-content>
      </mat-sidenav-content>
    </mat-sidenav-container>
  `,
  styleUrls: ['./group-sidenav.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GroupSidenavComponent {
  rootGroup$: Observable<SideTreeItem> = this.overviewService.list().pipe(map(model => this.toSidTreeItem(model)));

  constructor(
    private overviewService: OverviewService,
    private router: Router
  ) {
  }

  onSelect(event: SideTreeItem) {
    const group = <GroupLightModel>event.data;
    this.router.navigate(['group', group.uuid]);
  }

  toSidTreeItem(model: GroupLightModel): SideTreeItem {
    const item: SideTreeItem = {
      data: model,
      children: [],
      label: model.name
    };
    if (model.groups) {
      model.groups.forEach(group => item.children.push(this.toSidTreeItem(group)))
    }
    return item;
  }
}
