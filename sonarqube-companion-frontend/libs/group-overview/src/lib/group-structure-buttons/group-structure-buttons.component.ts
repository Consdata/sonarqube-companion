import {ChangeDetectionStrategy, Component, EventEmitter, Input, Output} from '@angular/core';
import {DateRange} from '@sonarqube-companion-frontend/ui-components/time-select';
import {DEFAULT_DATE_RANGE} from '../../../../ui-components/time-select/src/lib/time-select/time-select.component';
import {Observable} from 'rxjs';
import {GroupDetails, GroupService} from '@sonarqube-companion-frontend/group';

@Component({
  selector: 'sqc-group-structure-buttons',
  template: `
    <ng-container *ngIf="details$ | async as details; else spinner">
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

    <ng-template #spinner>
    </ng-template>
  `,
  styleUrls: ['./group-structure-buttons.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GroupStructureButtonsComponent {
  @Output()
  select: EventEmitter<string> = new EventEmitter<string>();

  constructor(private groupService: GroupService) {
  }

  _uuid: string = '';

  @Input()
  set uuid(uuid: string) {
    this._uuid = uuid;
    this.reload();
  }

  _range: DateRange = DEFAULT_DATE_RANGE;

  @Input()
  set range(range: DateRange) {
    this._range = range;
    this.reload();
  }

  details$: Observable<GroupDetails> = this.groupService.groupDetails(this._uuid, this._range);

  onSelect(id: string): void {
    this.select.emit(id);
  }

  private reload() {
    this.details$ = this.groupService.groupDetails(this._uuid, this._range);
  }
}
