import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

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
import {SettingsComponent} from './settings/settings-component';
import {GeneralSettingsComponent} from './settings/general-settings-component';
import {ServersSettingsComponent} from './settings/servers-settings-component';
import {GroupsSettingsComponent} from './settings/groups-settings-component';
import {FormsModule} from '@angular/forms';
import {TokenAuthenticationComponent} from './settings/authentication/token-authentication-component';
import {BasicAuthenticationComponent} from './settings/authentication/basic-authentication-component';
import {ServerComponent} from './settings/server-component';
import {EnumToSelectPipe, SchedulerComponent} from './settings/scheduler/scheduler-component';
import {GroupDetailSettingsComponent} from './settings/group/group-detail-settings-component';
import {HttpClientModule} from '@angular/common/http';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {EventComponent} from './settings/group/event/event-component';
import {ProjectLinkComponent} from './settings/group/project-link/project-link-component';
import {SubgroupsSettingsComponent} from './settings/group/subgroups-settings-component';
import {TreeModule} from 'angular-tree-component';
import {WebhooksListComponent} from './settings/group/webhook/webhooks-list-component';
import {SettingsListDetailsComponent} from './settings/common/settings-list-details-component';
import {SettingsListComponent} from './settings/common/settings-list-component';
import {BadgeComponent} from './settings/common/badge/badge-component';
import {AutofocusDirective} from './settings/common/autofocus-directive';
import {EventDetailsComponent} from './settings/group/event/event-details-component';
import {WebhookDetailsComponent} from './settings/group/webhook/webhook-details-component';
import {ProjectLinkListComponent} from './settings/group/project-link/project-link-list-component';
import {PeriodGroupEvent} from './settings/group/event/period-group-event';
import {DateGroupEvent} from './settings/group/event/date-group-event';
import {ServerSettingsService} from './settings/service/server-settings-service';
import {SchedulerSettingsService} from './settings/service/scheduler-settings-service';
import {GroupSettingsService} from './settings/service/group-settings-service';
import {RegexProjectLinkComponent} from './settings/group/project-link/regex-project-link-component';
import {DirectProjectLinkComponent} from './settings/group/project-link/direct-project-link-component';
import {WebhookCallbackDetailComponent} from './settings/group/webhook/callback/webhook-callback-detail-component';
import {RestWebhookTriggerComponent} from './settings/group/webhook/trigger/rest-webhook-trigger-component';
import {JsonWebhookCallbackComponent} from './settings/group/webhook/callback/json-webhook-callback-component';
import {CronWebhookTriggerComponent} from './settings/group/webhook/trigger/cron-webhook-trigger-component';
import {NoImprovementActionComponent} from './settings/group/webhook/action/no-improvement-action-component';
import {NoImprovementActionCallbackParamsComponent} from './settings/group/webhook/callback/no-improvement-action-callback-params-component';
import {PostWebhookCallbackComponent} from './settings/group/webhook/callback/post-webhook-callback-component';

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
    SubgroupsSettingsComponent,
    ServerComponent,
    SchedulerComponent,
    EnumToSelectPipe,
    GroupDetailSettingsComponent,
    EventComponent,
    ProjectLinkComponent,
    WebhooksListComponent,
    SettingsListComponent,
    SettingsListDetailsComponent,
    BadgeComponent,
    AutofocusDirective,
    EventDetailsComponent,
    WebhookDetailsComponent,
    ProjectLinkListComponent,
    PeriodGroupEvent,
    DateGroupEvent,
    RegexProjectLinkComponent,
    DirectProjectLinkComponent,
    WebhookCallbackDetailComponent,
    RestWebhookTriggerComponent,
    JsonWebhookCallbackComponent,
    CronWebhookTriggerComponent,
    NoImprovementActionComponent,
    NoImprovementActionCallbackParamsComponent,
    PostWebhookCallbackComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    AmChartsModule,
    FormsModule,
    DragDropModule,
    TreeModule.forRoot()
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
    SchedulerSettingsService,
    ServerSettingsService,
    GroupSettingsService
  ],
  entryComponents: [
    ServerComponent,
    EventDetailsComponent,
    WebhookDetailsComponent,
    ProjectLinkComponent,
    WebhookCallbackDetailComponent
  ],
  bootstrap: [
    SonarQubeCompanionComponent
  ]
})
export class AppModule {
}
