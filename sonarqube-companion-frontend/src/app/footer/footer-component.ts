import {Component} from '@angular/core';

import {BaseComponent} from '../base-component';
import {BaseHrefHelper} from '../util/base-href-helper';

@Component({
  selector: 'sq-footer',
  template: `
    <div>
      {{appName}} - {{version}}
    </div>
    <div>
      (<a [href]="href + '/commit/' + gitsha">{{gitsha}}</a>)
      (<a [href]="baseHref + 'swagger/index.html'">rest api</a>)
    </div>
  `,
  styles: [
    BaseComponent.DISPLAY_BLOCK
  ]
})
export class FooterComponent {
  appName = 'SonarQube Companion';
  version = 'devel/2.0.0';
  gitsha = '3c004175f1695bfe42b3ccc44134f9f07207d264';
  href = 'https://github.com/glipecki/sonarqube-companion';
  baseHref = BaseHrefHelper.getBaseHref();
}
