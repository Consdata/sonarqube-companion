import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'sqc-side-list',
  template: `
    <div class="list">
      <div class="header">
        <div class="title">{{title}}</div>
        <button mat-button class="back" (click)="back.emit()" *ngIf="showBackButton">
          <mat-icon>arrow_back</mat-icon>
        </button>
      </div>
      <mat-divider></mat-divider>
      <div class="items">
        <ng-content select="[sqc-side-list-items]"></ng-content>
      </div>
      <div class="bottom-bar">
        <ng-content select="[sqc-side-list-bottom-bar]"></ng-content>
      </div>
    </div>
    <div class="content">
      <ng-content select="[sqc-side-list-content]"></ng-content>
    </div>
  `,
  styleUrls: ['side-list.component.scss']
})
export class SideListComponent {

  @Input()
  title = '';

  @Input()
  showBackButton = false;

  @Output()
  back = new EventEmitter<void>();
}
