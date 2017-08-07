import {Component} from '@angular/core';

@Component({
  selector: 'sq-sidebar',
  template: `
    sidebar
  `,
  styles: [
    `:host { display: none; }`
  ]
})
export class SidebarComponent {
}
