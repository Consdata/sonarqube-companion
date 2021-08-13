import {Component} from '@angular/core';

@Component({
  selector: 'sqc-settings',
  template: `
    <div class="header">
      <div class="navigation">
        <div class="item" mat-ripple>Groups</div>
        <mat-divider vertical></mat-divider>
        <div class="item" mat-ripple>Members</div>
        <mat-divider vertical></mat-divider>
        <div class="item" mat-ripple>Servers</div>
        <mat-divider vertical></mat-divider>
        <div class="item" mat-ripple>Integrations</div>
        <mat-divider vertical></mat-divider>
        <div class="item" mat-ripple>Synchronization</div>
        <mat-divider vertical></mat-divider>
      </div>
      <mat-divider></mat-divider>
    </div>
  `,
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent {

}
