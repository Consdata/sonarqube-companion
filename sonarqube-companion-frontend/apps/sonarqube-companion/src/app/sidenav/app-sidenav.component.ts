import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'sqc-app-sidenav',
  template: `
    <mat-sidenav-container>
      <mat-sidenav opened mode="side" [disableClose]="true">
        <div class="wrapper">
          <mat-nav-list>
            <mat-list-item (click)="goToOverview()">
              <div class="item">
                <mat-icon class="material-icons-outlined">group_work</mat-icon>
              </div>
            </mat-list-item>
          </mat-nav-list>
          <div class="expander"></div>
          <mat-nav-list class="bottom-list">
            <mat-list-item (click)="goToSettings()">
              <div class="item">
                <mat-icon>settings</mat-icon>
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
  styleUrls: ['./app-sidenav.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AppSidenavComponent {
  constructor(private router: Router) {}

  goToSettings() {
    this.router.navigate(['settings']);
  }

  goToOverview() {
    this.router.navigate(['overview']);
  }
}
