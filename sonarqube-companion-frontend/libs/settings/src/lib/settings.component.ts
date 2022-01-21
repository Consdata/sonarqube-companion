import {Component} from '@angular/core';

@Component({
  selector: 'sqc-settings',
  template: `
    <div class="header">
      <div class="navigation">
        <div class="item" mat-ripple [routerLink]="'/settings/groups'"  *ngIf="false">Groups</div>
        <div class="item" mat-ripple [routerLink]="'/settings/members'" *ngIf="false">Members</div>
        <mat-divider vertical></mat-divider>
        <div class="item" mat-ripple [routerLink]="'/settings/servers'">Servers</div>
        <mat-divider vertical></mat-divider>
        <div class="item" mat-ripple [routerLink]="'/settings/integrations'" *ngIf="false">Integrations</div>
        <mat-divider vertical></mat-divider>
      </div>
      <mat-divider></mat-divider>
      <router-outlet></router-outlet>
    </div>
  `,
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent {

}
