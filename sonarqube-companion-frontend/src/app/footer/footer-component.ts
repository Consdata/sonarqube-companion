import {Component} from '@angular/core';

import {BaseComponent} from '../base-component';

@Component({
  selector: 'sq-footer',
  template: `
    <div>{{name}} - {{version}} (<a [href]="href + '/commit/' + gitsha">{{gitsha}}</a>)</div>
    <div><a [href]="href">{{href}}</a></div>
  `,
  styles: [
    BaseComponent.DISPLAY_BLOCK
  ]
})
export class FooterComponent {
  name = 'SonarQube Companion';
  version = 'devel/2.0.0';
  gitsha = '3c004175f1695bfe42b3ccc44134f9f07207d264';
  href = 'https://github.com/glipecki/sonarqube-companion';
}
