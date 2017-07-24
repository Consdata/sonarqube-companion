import {Component} from '@angular/core';

import {BaseComponent} from '../base-component';

@Component({
  selector: 'sq-sidebar',
  template: `
    sidebar
  `,
  styles: [
    BaseComponent.DISPLAY_BLOCK,
    `:host { display: none; }`
  ]
})
export class SidebarComponent {
}
