import {Component} from '@angular/core';

@Component({
  selector: 'sqc-toggle',
  template: `
    <mat-slide-toggle>
      <ng-content></ng-content>
    </mat-slide-toggle>
  `,
  styleUrls: ['./toggle.component.scss']
})
export class ToggleComponent {

}
