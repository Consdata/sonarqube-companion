import { Component, Input } from '@angular/core';
import { GroupLightModel } from '@sonarqube-companion-frontend/group-overview';

@Component({
  selector: 'sqc-group-preview',
  template: `
    <ng-container *ngIf="group">
      <div class="preview">
        <div class="name">{{ group.name }}</div>
      </div>
    </ng-container>
  `,
  styleUrls: ['./group-preview.component.scss'],
})
export class GroupPreviewComponent {
  @Input()
  group?: GroupLightModel;
}
