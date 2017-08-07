import {Component} from '@angular/core';

@Component({
  selector: 'sq-not-found',
  template: `
    <div class="icon">
      <i class="fa fa-bug" aria-hidden="true"></i>
    </div>
    <div class="message">
      Page not found
    </div>
  `
})
export class NotFoundComponent {
}
