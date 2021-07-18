import {ChangeDetectionStrategy, Component} from '@angular/core';
import {combineLatest, Observable, of} from 'rxjs';
import {GroupDetails, GroupService} from '@sonarqube-companion-frontend/group';
import {ActivatedRoute} from '@angular/router';
import {distinctUntilChanged, map, switchMap} from 'rxjs/operators';

interface GroupOverviewModel {
  details: GroupDetails;
}

@Component({
  selector: 'sqc-group-overview',
  template: `
    <div class="group" *ngIf="vm$ | async as vm">
      {{vm.details.name}}
    </div>
  `,
  styleUrls: ['./group-overview.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GroupOverviewComponent {

  vm$: Observable<GroupOverviewModel> = this.route.params.pipe(
    map(params => params['groupId']),
    distinctUntilChanged(),
    switchMap(id =>
      combineLatest([
        this.groupService.groupDetails(id)
      ]).pipe(
        map(([details]) => ({details: details}))
      )
    )
  );

  constructor(private groupService: GroupService, private route: ActivatedRoute) {
  }

}
