import {Component} from '@angular/core';

@Component({
  selector: 'sq-navbar',
  template: `
    <div class="app-name">
      <a routerLink="/">{{appName}}</a>
    </div>
    <div class="menu">
      <a class="menu-item" routerLink="/overview" routerLinkActive="active">Overview</a>
      <a class="menu-item" routerLink="/groups" routerLinkActive="active">Groups</a>
      <a class="menu-item" routerLink="/projects" routerLinkActive="active">Projects</a>
    </div>
    <div class="expander">
    </div>
    <div class="user-menu">
      <a class="menu-item settings-icon" (click)="toggleSettingsMenu()">
        <i class="fa fa-cog"></i>
      </a>
    </div>
    <sq-settings-menu *ngIf="settingsMenuVisible">
    </sq-settings-menu>
  `
})
export class NavbarComponent {

  appName = 'SQCompanion';
  settingsMenuVisible = false;

  toggleSettingsMenu() {
    this.settingsMenuVisible = !this.settingsMenuVisible;
  }

}
