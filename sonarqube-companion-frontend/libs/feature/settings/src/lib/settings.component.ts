import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'sqc-settings',
  template: `
    <sqc-side-list [title]="'Settings'">
      <div sqc-side-list-items>
        <mat-nav-list>
          <mat-list-item (click)="goToGroups()">
            <div class="item">
              <div class="title">Groups</div>
              <div class="description">Organizations groups</div>
            </div>
          </mat-list-item>
<!--          <mat-list-item (click)="goToServers()">-->
<!--            <div class="item">-->
<!--              <div class="title">Members</div>-->
<!--              <div class="description">Organization members</div>-->
<!--            </div>-->
<!--          </mat-list-item>-->
          <mat-list-item (click)="goToServers()">
            <div class="item">
              <div class="title">Servers</div>
              <div class="description">Data sources</div>
            </div>
          </mat-list-item>
<!--          <mat-list-item (click)="goToServers()">-->
<!--            <div class="item">-->
<!--              <div class="title">Integrations</div>-->
<!--              <div class="description">Third party integrations</div>-->
<!--            </div>-->
<!--          </mat-list-item>-->
        </mat-nav-list>
      </div>
      <div sqc-side-list-content>
        <router-outlet></router-outlet>
      </div>
    </sqc-side-list>
  `,
  styleUrls: ['./settings.component.scss'],
})
export class SettingsComponent {
  constructor(private router: Router) {}

  goToServers(): void {
    this.router.navigate(['settings', 'servers']);
  }

  goToGroups(): void {
    this.router.navigate(['settings', 'groups']);
  }
}
