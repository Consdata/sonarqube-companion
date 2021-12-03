import {ChangeDetectionStrategy, Component, EventEmitter, Input, Output} from '@angular/core';
import {DateRange} from '@sonarqube-companion-frontend/ui-components/time-select';
import {DEFAULT_DATE_RANGE} from '../../../../ui-components/time-select/src/lib/time-select/time-select.component';
import {ReplaySubject} from 'rxjs';
import {GroupService} from '@sonarqube-companion-frontend/group';
import {switchMap, tap} from 'rxjs/operators';
import {GroupFilter} from '../group-filter';

@Component({
  selector: 'sqc-group-structure-buttons',
  template: `
    <ng-container *ngIf="vm$ | async as details">
      <ng-container *ngIf="!loading">
        <button mat-button (click)="onSelect('projects')" matTooltip="Projects">
          <div class="item">
            <span class="label">{{details.projects}}</span>
            <mat-icon class="settings">code</mat-icon>
          </div>
        </button>
        <mat-divider vertical></mat-divider>
        <button mat-button (click)="onSelect('members')" matTooltip="Members">
          <div class="item">
            <span class="label">{{details.members}}</span>
            <mat-icon class="settings">group</mat-icon>
          </div>
        </button>
        <mat-divider vertical></mat-divider>
      </ng-container>
    </ng-container>

    <ng-container *ngIf="loading">
      <button mat-button matTooltip="Projects">
        <div class="item">
          <mat-spinner diameter="20"></mat-spinner>
          <mat-icon class="settings">code</mat-icon>
        </div>
      </button>
      <mat-divider vertical></mat-divider>
      <button mat-button matTooltip="Members">
        <div class="item">
          <mat-spinner diameter="20"></mat-spinner>
          <mat-icon class="settings">group</mat-icon>
        </div>
      </button>
      <mat-divider vertical></mat-divider>
    </ng-container>
  `,
  styleUrls: ['./group-structure-buttons.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GroupStructureButtonsComponent {
  @Output()
  select: EventEmitter<string> = new EventEmitter<string>();
  loading: boolean = false;
  filter: GroupFilter = {uuid: '', range: DEFAULT_DATE_RANGE};
  filterSubject: ReplaySubject<GroupFilter> = new ReplaySubject<GroupFilter>();
  vm$ = this.filterSubject.asObservable().pipe(
    tap(_ => this.loading = true),
    switchMap(data => this.groupService.groupDetails(data.uuid, data.range)),
    tap(_ => this.loading = false)
  );

  constructor(private groupService: GroupService) {
  }

  _uuid: string = '';

  @Input()
  set uuid(data: string) {
    if (data) {
      this.filter.uuid = data;
      this.filterSubject.next(this.filter);
    }
  }

  @Input()
  set range(data: DateRange) {
    if (data && this.filter.uuid !== '') {
      this.filter.range = data;
      this.filterSubject.next(this.filter);
    }
  }

  onSelect(id: string): void {
    this.select.emit(id);
  }
}
