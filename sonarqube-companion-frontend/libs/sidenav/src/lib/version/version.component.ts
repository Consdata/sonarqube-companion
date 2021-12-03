import {Component} from '@angular/core';
import {VersionService} from './version.service';
import {Observable} from 'rxjs';
import {ApplicationVersion} from './app-version';

@Component({
  selector: 'sqc-version',
  template: `
    <ng-container *ngIf="version$ | async as version">
      {{ version.version }}
    </ng-container>
  `,
  styleUrls: ['./version.component.scss']
})
export class VersionComponent {

  version$: Observable<ApplicationVersion> = this.versionService.get();


  constructor(private versionService: VersionService) {
  }


}
