import {Component} from '@angular/core';

@Component({
  selector: 'sq-root',
  template: `
    <h1>
      Welcome to {{title}}!!
    </h1>
    <router-outlet></router-outlet>
  `
})
export class AppComponent {
  title = 'sq';
}
