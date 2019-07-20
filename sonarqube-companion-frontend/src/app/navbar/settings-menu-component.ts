import {Component, EventEmitter, Output} from '@angular/core';
import {AppStateService} from 'app/app-state-service';

@Component({
  selector: 'sq-settings-menu',
  template: `
    <a class="menu-item" (click)="togglePresentationTheme()">
      <i class="fa fa-check" *ngIf="isPresentationMode()"></i>
      Presentation theme
    </a>
    <a class="menu-item" routerLink="/settings" (click)="toggle.emit();">
      Settings
    </a>
  `
})
export class SettingsMenuComponent {

  @Output()
  toggle = new EventEmitter<void>();

  constructor(private appStateService: AppStateService) {
  }

  togglePresentationTheme() {
    if (this.isPresentationMode()) {
      this.appStateService.theme = undefined;
    } else {
      this.appStateService.theme = 'presentation';
    }
  }

  isPresentationMode() {
    return this.appStateService.theme === 'presentation';
  }
}
