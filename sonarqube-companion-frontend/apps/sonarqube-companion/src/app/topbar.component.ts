import { ChangeDetectionStrategy, Component } from '@angular/core';
import { VersionService } from './version/version.service';

@Component({
  selector: 'sqc-topbar',
  template: `
    <div class="wrapper">
      <div class="title">
        <div class="name">SonarQubeCompanion</div>
        <div class="version" *ngIf="version$ | async as version">
          {{ version.version }}
        </div>
      </div>
    </div>
  `,
  styleUrls: ['./topbar.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TopbarComponent {
  version$ = this.versionService.get();

  constructor(private versionService: VersionService) {}
}
