import {AfterViewInit, Component, OnDestroy} from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {BehaviorSubject, Observable, Subscription} from 'rxjs';
import {Select} from '@ngxs/store';
import {GroupSettingsState} from '../state/group-settings-state';
import {ProjectLink} from '../model/group-config';
import {DevService} from '../../../../ui-components/table/src/lib/table/dev.service';

@Component({
  selector: 'sqc-group-projects',
  template: `
    <ng-container *ngIf="projects$ | async as projectLinks">
      <sqc-table [data]="dev.get() | async"></sqc-table>
    </ng-container>
  `,
  styleUrls: ['./group-projects.component.scss']
})
export class GroupProjectsComponent {
  displayedColumns: string[] = ['Project', 'Server', 'Regexp', 'Exclude'];
  dataSource!: DataSource<ProjectLink>;
  sub!: Subscription;
  @Select(GroupSettingsState.projects)
  projects$!: Observable<ProjectLink[]>;

  constructor(public dev: DevService) {
  }


}
