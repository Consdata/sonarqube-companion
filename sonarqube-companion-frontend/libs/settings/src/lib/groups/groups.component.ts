import {AfterViewInit, Component} from '@angular/core';
import {combineLatest, ReplaySubject} from 'rxjs';
import {map, switchMap} from 'rxjs/operators';
import {GroupsConfigService} from '../service/groups-config.service';
import {GroupLightModel} from '@sonarqube-companion-frontend/group-overview';
import {Router} from '@angular/router';
import {SpinnerService} from '../../../../utils/src/lib/spinner.service';

@Component({
  selector: 'sqc-groups',
  template: `
    <ng-container *ngIf="vm$ | async as vm">
      <div class="list" *ngFor="let group of vm.groups; trackBy:uuid">
        <sqc-group-preview [group]="group" (click)="navigate(group)"></sqc-group-preview>
      </div>
      <div class="add">
              <span mat-ripple (click)="addGroup()"
                    *ngIf="isAddGroupAllowed(); else spinner">+ new server</span>
      </div>
      <ng-template #spinner>
        <mat-spinner diameter="20"></mat-spinner>
      </ng-template>
    </ng-container>
  `,
  styleUrls: ['./groups.component.scss']
})
export class GroupsComponent implements AfterViewInit {
  subject: ReplaySubject<void> = new ReplaySubject<void>();
  vm$ = this.subject.asObservable().pipe(
    switchMap(() =>
      combineLatest([
        this.configService.all()
      ]).pipe(
        map(([
               groups,
             ]) => ({
          groups: groups,
        }))
      )
    )
  )

  constructor(private configService: GroupsConfigService, private router: Router, private spinnerService: SpinnerService) {
  }

  ngAfterViewInit(): void {
    this.subject.next();
  }

  uuid(index: number, group: GroupLightModel): string {
    return group.uuid;
  }

  addServer(): void {
  //  this.spinnerService.lock(Locks.ADD_SERVER);
    // this.configService.create().subscribe(() => {
    //   this.subject.next();
    //   this.spinnerService.unlock(Locks.ADD_SERVER);
    // });
  }

  save(): void {
    this.subject.next();
  }

  delete(): void {
    this.subject.next();
  }

  isAddGroupAllowed(): boolean {
   // return this.spinnerService.isUnlocked(Locks.ADD_SERVER);
    return false;
  }
  onSelect(group: GroupLightModel) {

  }

  navigate(group: GroupLightModel): void {
    this.router.navigate(['settings', 'groups', group.uuid])
  }

  addGroup(): void {

  }
}
