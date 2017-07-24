import {Component} from '@angular/core';

import {BaseComponent} from '../base-component';

@Component({
  selector: 'sq-not-found',
  template: `
    <div class="icon">
      <i class="material-icons">bug_report</i>
    </div>
    <div class="message">
      Page not found
    </div>
  `,
  styles: [
    BaseComponent.DISPLAY_BLOCK
  ]
})
export class NotFoundComponent {
}
