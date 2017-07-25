import {Component} from '@angular/core';

import {BaseComponent} from '../base-component';

@Component({
  selector: 'sq-spinner',
  template: `
    <div class="sk-rotating-plane"></div>
  `,
  styles: [
    BaseComponent.DISPLAY_BLOCK
  ]
})
export class SpinnerComponent {
}
