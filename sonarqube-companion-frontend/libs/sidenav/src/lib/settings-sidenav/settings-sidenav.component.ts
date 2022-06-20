import {ChangeDetectionStrategy, Component} from '@angular/core';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {GroupLightModel} from '@sonarqube-companion-frontend/group-overview';
import {OverviewService} from '../../../../overview/src/lib/overview.service';

@Component({
  selector: 'sqc-settings-sidenav',
  template: `
    <mat-sidenav-container>
      <mat-sidenav opened mode="side" [disableClose]="true">
        <div class="wrapper">
          <mat-divider></mat-divider>
          <mat-nav-list *ngIf="state === 'groups'">
            <sqc-side-groups-tree-wrapper [rootGroup]="rootGroup$"></sqc-side-groups-tree-wrapper>
            <mat-divider></mat-divider>
          </mat-nav-list>
          <mat-nav-list *ngIf="state === ''">
<!--            <mat-list-item>-->
<!--              <div class="item" (click)="goToGroups()">-->
<!--                <div class="title">Groups</div>-->
<!--                <div class="description">Edit groups hierarchy & groups data</div>-->
<!--              </div>-->
<!--            </mat-list-item>-->
            <mat-list-item>
              <div class="item" (click)="goToServers()">
                <div class="title">Servers</div>
                <div class="description">Data sources</div>
              </div>
            </mat-list-item>
          </mat-nav-list>
        </div>
      </mat-sidenav>
      <mat-sidenav-content>
        <ng-content></ng-content>
      </mat-sidenav-content>
    </mat-sidenav-container>
  `,
  styleUrls: ['./settings-sidenav.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SettingsSidenavComponent {
  rootGroup$: Observable<GroupLightModel> = this.overviewService.list();
  state: string = '';

  constructor(private overviewService: OverviewService, private router: Router) {
  }

  goToServers() {
    this.router.navigate(['settings', 'servers'])
  }

  goToGroups() {
    this.state = 'groups';
    this.router.navigate(['settings', 'groups'])
  }
}
