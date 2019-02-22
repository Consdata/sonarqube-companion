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
import {SynchronizationService} from 'app/synchronization/synchronization-service';
import {SynchronizationComponent} from './synchronization/synchronization-component';
import {ViolationsHistoryComponent} from './history/violations-history-component';
import {GroupOverviewCardComponent} from './overview/overview-card';
import {GroupOverviewCardsComponent} from 'app/group/group-overview-cards';
import {AppStateService} from 'app/app-state-service';
import {AppState} from 'app/app-state';
import {SettingsMenuComponent} from 'app/navbar/settings-menu-component';
import {GroupProjectsComponent} from './group/group-projects-component';
import {ProjectViolationsComponent} from './group/project-violations-component';
import {ProjectComponent} from './project/project-component';
import {ProjectService} from 'app/project/project-service';
import {ProjectOverviewCardsComponent} from './project/project-overview-cards';
import {SettingsComponent} from "./settings/settings-component";
import {GeneralSettingsComponent} from "./settings/general-settings-component";
import {ServersSettingsComponent} from "./settings/servers-settings-component";
import {GroupsSettingsComponent} from "./settings/groups-settings-component";
import {WebhooksSettingsComponent} from "./settings/webhooks-settings-component";
import {FormsModule} from "@angular/forms";
import {TokenAuthenticationComponent} from "./settings/authentication/token-authentication-component";
import {BasicAuthenticationComponent} from "./settings/authentication/basic-authentication-component";
import {SettingsService} from "./settings/service/settings-service";
import {ServerComponent} from "./settings/server-component";
import {EnumToSelectPipe, SchedulerComponent} from "./settings/scheduler/scheduler-component";
import {GroupSettingsComponent} from "./settings/group/group-settings-component";
import {GroupDetailSettingsComponent} from "./settings/group/group-detail-settings-component";

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
    GroupOverviewTreeItemComponent,
    ViolationsHistoryComponent,
    GroupOverviewCardComponent,
    GroupOverviewCardsComponent,
    SynchronizationComponent,
    SettingsMenuComponent,
    GroupProjectsComponent,
    ProjectViolationsComponent,
    ProjectComponent,
    ProjectOverviewCardsComponent,
    SettingsComponent,
    GeneralSettingsComponent,
    ServersSettingsComponent,
    GroupsSettingsComponent,
    WebhooksSettingsComponent,
    TokenAuthenticationComponent,
    BasicAuthenticationComponent,
    GroupSettingsComponent,
    ServerComponent,
    SchedulerComponent,
    EnumToSelectPipe,
    GroupDetailSettingsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule,
    AmChartsModule,
    FormsModule
  ],
  providers: [
    OverviewService,
    GroupService,
    VersionService,
    ViolationsHistoryService,
    SynchronizationService,
    AppState,
    AppStateService,
    ProjectService,
    SettingsService
  ],
  bootstrap: [
    SonarQubeCompanionComponent
  ]
})
export class AppModule {
}
