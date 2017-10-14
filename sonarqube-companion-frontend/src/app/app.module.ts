import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpModule} from '@angular/http';

import {AmChartsModule} from '@amcharts/amcharts3-angular';

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
import {SynchronizationComponent} from './synchronization/synchronization-component';
import {ViolationsHistoryComponent} from './history/violations-history-component';
import {GroupOverviewCardComponent} from './overview/overview-card';
import {GroupProjectsComponent} from './group/group-projects-component';
import {ProjectViolationsComponent} from './group/project-violations-component';
import {ProjectComponent} from './project/project-component';
import {ProjectOverviewCardsComponent} from './project/project-overview-cards';
import {GroupOverviewCardsComponent} from './group/group-overview-cards';
import {SettingsMenuComponent} from './navbar/settings-menu-component';
import {SynchronizationService} from './synchronization/synchronization-service';
import {AppState} from './app-state';
import {AppStateService} from './app-state-service';
import {ProjectService} from './project/project-service';

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
    ViolationsHistoryComponent,
    GroupOverviewCardComponent,
    GroupOverviewCardsComponent,
    SynchronizationComponent,
    SettingsMenuComponent,
    GroupProjectsComponent,
    ProjectViolationsComponent,
    ProjectComponent,
    ProjectOverviewCardsComponent
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
    AppStateService,
    ProjectService
  ],
  bootstrap: [
    SonarQubeCompanionComponent
  ]
})
export class AppModule {
}
