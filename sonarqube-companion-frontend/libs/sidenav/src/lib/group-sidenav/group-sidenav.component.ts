import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Observable } from 'rxjs';
import { GroupLightModel } from '@sonarqube-companion-frontend/group-overview';
import { OverviewService } from '../../../../overview/src/lib/overview.service';
import { Router } from '@angular/router';

@Component({
  selector: 'sqc-group-sidenav',
  template: `
    <mat-sidenav-container>
      <mat-sidenav opened mode="side" [disableClose]="true">
        <div class="wrapper">
          <mat-divider></mat-divider>
          <mat-nav-list>
            <sqc-side-groups-tree-wrapper
              [rootGroup]="rootGroup$"
              (groupSelect)="onSelect($event)"
            ></sqc-side-groups-tree-wrapper>
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
  rootGroup$: Observable<GroupLightModel> = this.overviewService.list();

  constructor(
    private overviewService: OverviewService,
    private router: Router
  ) {}

  onSelect(event: GroupLightModel) {
    this.router.navigate(['group', event.uuid]);
  }
}
