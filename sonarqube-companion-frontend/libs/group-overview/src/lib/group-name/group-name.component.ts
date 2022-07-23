import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { GroupService } from '@sonarqube-companion-frontend/group';
import { ReplaySubject } from 'rxjs';
import { DEFAULT_DATE_RANGE } from '../../../../ui-components/time-select/src/lib/time-select/time-select.component';
import { switchMap, tap } from 'rxjs/operators';

@Component({
  selector: 'sqc-group-name',
  template: `
    <ng-container *ngIf="vm$ | async as vm">
      <div class="label" *ngIf="!loading">{{ vm.name }}</div>
      <div class="description" *ngIf="!loading">{{ vm.description }}</div>
    </ng-container>
    <div class="label" *ngIf="loading">
      <mat-spinner diameter="20"></mat-spinner>
    </div>
  `,
  styleUrls: ['./group-name.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GroupNameComponent {
  loading: boolean = false;
  uuidSubject: ReplaySubject<string> = new ReplaySubject<string>();
  vm$ = this.uuidSubject.asObservable().pipe(
    tap((_) => (this.loading = true)),
    switchMap((uuid) =>
      this.groupService.groupDetails(uuid, DEFAULT_DATE_RANGE)
    ),
    tap((_) => (this.loading = false))
  );

  constructor(private groupService: GroupService) {}

  @Input()
  set uuid(_uuid: string) {
    if (_uuid) {
      this.uuidSubject.next(_uuid);
    }
  }
}
