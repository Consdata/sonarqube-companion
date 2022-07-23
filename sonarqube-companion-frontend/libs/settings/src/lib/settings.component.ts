import { Component } from '@angular/core';

@Component({
  selector: 'sqc-settings',
  template: `
    <sqc-settings-sidenav>
      <router-outlet></router-outlet>
    </sqc-settings-sidenav>
  `,
  styleUrls: ['./settings.component.scss'],
})
export class SettingsComponent {}
