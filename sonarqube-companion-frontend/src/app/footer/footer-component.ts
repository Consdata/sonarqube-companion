import {AfterViewInit, Component, OnDestroy} from '@angular/core';
import {BaseHrefHelper} from '../util/base-href-helper';
import {VersionService} from '../version/version-service';
import {ApplicationVersion} from '../version/application-version';
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'sq-footer',
  template: `
    <div class="footer-left">
    </div>
    <div class="footer-center">
      <div>
        {{appName}} - {{version}} - {{buildTimestamp}}
      </div>
      <div>
        (<a [href]="href + '/tree/' + encode(gitbranch)">{{gitbranch}}</a>)
        (<a [href]="href + '/commit/' + gitsha">{{gitsha}}</a>)
        (<a [href]="baseHref + 'swagger/index.html'">rest api</a>)
      </div>
    </div>
    <div class="footer-right">
      <sq-synchronization></sq-synchronization>
    </div>
  `
})
export class FooterComponent implements AfterViewInit, OnDestroy {


  appName = 'SonarQube Companion';
  version = '';
  gitsha = '';
  gitbranch = '';
  buildTimestamp = '';
  href = 'https://github.com/Consdata/sonarqube-companion';
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
    });
  }

  ngOnDestroy(): void {
    if (this.versionSubscription) {
      this.versionSubscription.unsubscribe();
    }
  }

  encode(input: string): string {
    return encodeURIComponent(input);
  }

}
