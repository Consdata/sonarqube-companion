import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {GroupService} from '@sonarqube-companion-frontend/group';
import {ReplaySubject} from 'rxjs';
import {DEFAULT_DATE_RANGE} from '../../../../ui-components/time-select/src/lib/time-select/time-select.component';
import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'sqc-group-name',
  template: `
    <div class="label" *ngIf="vm$ | async as vm ; else spinner">{{vm.name}}</div>
    <ng-template #spinner>
      <div class="label" #spinner>
        <mat-spinner diameter="20"></mat-spinner>
      </div>
    </ng-template>
  `,
  styleUrls: ['./group-name.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GroupNameComponent {

  uuidSubject: ReplaySubject<string> = new ReplaySubject<string>();
  vm$ = this.uuidSubject.asObservable().pipe(
    switchMap(uuid => this.groupService.groupDetails(uuid, DEFAULT_DATE_RANGE))
  );

  constructor(private groupService: GroupService) {
  }

  @Input()
  set uuid(_uuid: string) {
    if (_uuid) {
      this.uuidSubject.next(_uuid);
    }
  }
}
