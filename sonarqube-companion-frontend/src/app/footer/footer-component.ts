import {AfterViewInit, Component, OnDestroy} from '@angular/core';

import {BaseComponent} from '../base-component';
import {BaseHrefHelper} from '../util/base-href-helper';
import {VersionService} from "../version/version-service";
import {ApplicationVersion} from "../version/application-version";
import {Subscription} from "rxjs/Subscription";

@Component({
  selector: 'sq-footer',
  template: `
    <div>
      {{appName}} - {{version}} - {{buildTimestamp}}
    </div>
    <div>
      (<a [href]="href + '/tree/' + gitbranch">{{gitbranch}}</a>)
      (<a [href]="href + '/commit/' + gitsha">{{gitsha}}</a>)
      (<a [href]="baseHref + 'swagger/index.html'">rest api</a>)
    </div>
  `,
  styles: [
    BaseComponent.DISPLAY_BLOCK
  ]
})
export class FooterComponent implements AfterViewInit, OnDestroy {


  appName = 'SonarQube Companion';
  version = '';
  gitsha = '';
  gitbranch = '';
  buildTimestamp = '';
  href = 'https://github.com/glipecki/sonarqube-companion';
  baseHref = BaseHrefHelper.getBaseHref();
  versionSubscription: Subscription;

  constructor(private versionService: VersionService) {
  }

  ngAfterViewInit(): void {
    this.versionSubscription = this.versionService.getAppliactionVersion().subscribe((applicationVersion: ApplicationVersion) => {
      this.version = applicationVersion.version;
      this.gitbranch = applicationVersion.branch;
      this.buildTimestamp = applicationVersion.buildTimestamp;
      this.gitsha = applicationVersion.commitId;
    })
  }

  ngOnDestroy(): void {
    if (this.versionSubscription) {
      this.versionSubscription.unsubscribe();
    }
  }
}
