import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpModule} from '@angular/http';

import { AmChartsModule } from '@amcharts/amcharts3-angular';

import {AppRoutingModule} from './app-routing.module';
import {SonarQubeCompanionComponent} from './sonarqube-companion-component';
import {NavbarComponent} from './navbar/navbar-component';
import {FooterComponent} from './footer/footer-component';
import {SidebarComponent} from './sidebar/sidebar-component';
import {OverviewComponent} from './overview/overview-component';
import {NotFoundComponent} from './not-found/not-found-component';
import {OverviewService} from './overview/overview-service';
import {SpinnerComponent} from './spinner/spinner-component';
import {GroupOverviewTreeItemComponent} from './overview/group-overview-tree-item-component';
import {GroupService} from './group/group-service';
import {GroupComponent} from './group/group-component';
import {ViolationsHistoryService} from './violations/violations-history-service';
import {VersionService} from './version/version-service';
import {SynchronizationService} from 'app/synchronization/synchronization-service';
import {SynchronizationComponent} from './synchronization/synchronization-component';
import {GroupViolationsHistoryComponent} from './group/group-violations-history';
import {GroupOverviewCardComponent} from './group/group-overview-card';
import {GroupOverviewCardsComponent} from 'app/group/group-overview-cards';
import {AppStateService} from 'app/app-state-service';
import {AppState} from 'app/app-state';
import {SettingsMenuComponent} from 'app/navbar/settings-menu-component';
import {GroupProjectsComponent} from './group/group-projects-component';
import {ProjectViolationsComponent} from './group/project-violations-component';

@NgModule({
  declarations: [
    SonarQubeCompanionComponent,
    NavbarComponent,
    FooterComponent,
    SidebarComponent,
    OverviewComponent,
    NotFoundComponent,
    SpinnerComponent,
    GroupComponent,
    GroupComponent,
    GroupOverviewTreeItemComponent,
    GroupViolationsHistoryComponent,
    GroupOverviewCardComponent,
    GroupOverviewCardsComponent,
    SynchronizationComponent,
    SettingsMenuComponent,
    GroupProjectsComponent,
    ProjectViolationsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule,
    AmChartsModule
  ],
  providers: [
    OverviewService,
    GroupService,
    VersionService,
    ViolationsHistoryService,
    SynchronizationService,
    AppState,
    AppStateService
  ],
  bootstrap: [
    SonarQubeCompanionComponent
  ]
})
export class AppModule {
}
