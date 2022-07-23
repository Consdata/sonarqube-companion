import { ChangeDetectionStrategy, Component } from '@angular/core';
import { GroupService } from '@sonarqube-companion-frontend/group';
import { Router } from '@angular/router';

@Component({
  selector: 'sqc-overview',
  template: ``,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class OverviewComponent {
  constructor(private groupService: GroupService, private router: Router) {
    groupService
      .getRootGroup()
      .subscribe((details) => router.navigate([`/group/${details.uuid}`]));
  }
}
