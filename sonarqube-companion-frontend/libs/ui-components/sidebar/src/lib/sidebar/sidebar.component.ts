import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Router} from '@angular/router';
import {animate, state, style, transition, trigger} from '@angular/animations';

export interface SidebarItem {
  icon: string;
  label: string;
  path: string;
}

@Component({
  selector: 'sqc-sidebar',
  animations: [
    trigger('rotatedFoldButton', [
      state('folded', style({transform: 'rotate(0)'})),
      state('unfolded', style({transform: 'rotate(-180deg)'})),
      transition('unfolded => folded', animate('200ms ease-out')),
      transition('folded => unfolded', animate('200ms ease-in'))
    ])
  ],
  template: `
    <mat-sidenav-container>
      <mat-sidenav opened mode="side">
        <div class="wrapper">
          <div class="title">
            <span>{{ folded ? shortName : name }}</span>
          </div>
          <mat-selection-list [multiple]="false">
            <mat-list-option *ngFor="let item of items" (click)="navigate(item.path)" [value]="item.path">
              <mat-icon mat-list-icon>{{item.icon}}</mat-icon>
              <span *ngIf="!folded">{{item.label | lowercase}}</span>
            </mat-list-option>
          </mat-selection-list>
          <div class="expander"></div>
          <mat-nav-list>
            <mat-list-item>
              <div class="item"><a class="git" href="https://github.com/Consdata/sonarqube-companion" target="_blank"></a></div>
            </mat-list-item>
            <mat-list-item>
              <div class="item"><mat-icon>sync</mat-icon></div>
            </mat-list-item>
            <mat-divider></mat-divider>
            <mat-list-item class="fold-button" (click)="folded = !folded">
              <mat-icon mat-list-icon [@rotatedFoldButton]="folded ? 'folded' : 'unfolded'">chevron_right</mat-icon>
            </mat-list-item>
          </mat-nav-list>
        </div>
      </mat-sidenav>
      <mat-sidenav-content>
        <ng-content></ng-content>
      </mat-sidenav-content>
    </mat-sidenav-container>
  `,
  styleUrls: ['./sidebar.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SidebarComponent {
  folded: boolean = false;

  @Input()
  name: string = '';
  @Input()
  shortName: string = '';
  @Input()
  items: SidebarItem[] = [];

  constructor(private router: Router) {
  }

  navigate(target: string) {
    this.router.navigate([target]);
  }
}
