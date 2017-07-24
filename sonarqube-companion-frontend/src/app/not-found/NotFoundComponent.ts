import {Component} from '@angular/core';

import {BaseComponent} from '../base-component';

@Component({
  selector: 'sq-not-found',
  template: `
    <div class="icon">
      <i class="fa fa-frown-o"></i>
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
