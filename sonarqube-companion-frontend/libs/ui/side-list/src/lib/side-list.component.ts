import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'sqc-side-list',
  template: `
    <div class="list">
      <div class="header">
        <div class="title">{{ title }}</div>
        <button
          mat-button
          class="back"
          (click)="back.emit()"
          *ngIf="showBackButton"
        >
          <mat-icon>arrow_back</mat-icon>
        </button>
      </div>
      <mat-divider></mat-divider>
      <ng-container *ngIf="!loading; else spinner">
        <div class="items">
          <ng-content select="[sqc-side-list-items]"></ng-content>
        </div>
        <div class="bottom-bar">
          <ng-content select="[sqc-side-list-bottom-bar]"></ng-content>
        </div>
      </ng-container>
    </div>
    <div class="content">
      <ng-content select="[sqc-side-list-content]"></ng-content>
    </div>

    <ng-template #spinner>
      <div class="spinner">
        <mat-spinner [diameter]="32"></mat-spinner>
      </div>
    </ng-template>
  `,
  styleUrls: ['side-list.component.scss'],
})
export class SideListComponent {
  @Input()
  title = '';

  @Input()
  showBackButton = false;

  @Input()
  loading = false;

  @Output()
  back = new EventEmitter<void>();
}
