import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

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
import {SettingsComponent} from './config/settings-component';
import {GeneralSettingsComponent} from './config/general-settings-component';
import {ServersSettingsComponent} from './config/servers-settings-component';
import {GroupsSettingsComponent} from './config/groups-settings-component';
import {FormsModule} from '@angular/forms';
import {TokenAuthenticationComponent} from './config/authentication/token-authentication-component';
import {BasicAuthenticationComponent} from './config/authentication/basic-authentication-component';
import {ServerComponent} from './config/server-component';
import {EnumToSelectPipe, SchedulerComponent} from './config/scheduler/scheduler-component';
import {GroupDetailSettingsComponent} from './config/group/group-detail-settings-component';
import {HttpClientModule} from '@angular/common/http';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {EventComponent} from './config/group/event/event-component';
import {ProjectLinkComponent} from './config/group/project-link/project-link-component';
import {SubgroupsSettingsComponent} from './config/group/subgroups-settings-component';
import {TreeModule} from '@circlon/angular-tree-component';
import {WebhooksListComponent} from './config/group/webhook/webhooks-list-component';
import {SettingsListDetailsComponent} from './config/common/settings-list/settings-list-details-component';
import {SettingsListComponent} from './config/common/settings-list/settings-list-component';
import {BadgeComponent} from './config/common/badge/badge-component';
import {AutofocusDirective} from './config/common/autofocus-directive';
import {EventDetailsComponent} from './config/group/event/event-details-component';
import {WebhookDetailsComponent} from './config/group/webhook/webhook-details-component';
import {ProjectLinkListComponent} from './config/group/project-link/project-link-list-component';
import {PeriodGroupEvent} from './config/group/event/period-group-event';
import {DateGroupEvent} from './config/group/event/date-group-event';
import {ServerSettingsService} from './config/service/server-settings-service';
import {SchedulerSettingsService} from './config/service/scheduler-settings-service';
import {GroupSettingsService} from './config/service/group-settings-service';
import {RegexProjectLinkComponent} from './config/group/project-link/regex-project-link-component';
import {DirectProjectLinkComponent} from './config/group/project-link/direct-project-link-component';
import {WebhookCallbackDetailComponent} from './config/group/webhook/callback/webhook-callback-detail-component';
import {RestWebhookTriggerComponent} from './config/group/webhook/trigger/rest-webhook-trigger-component';
import {JsonWebhookCallbackComponent} from './config/group/webhook/callback/json-webhook-callback-component';
import {CronWebhookTriggerComponent} from './config/group/webhook/trigger/cron-webhook-trigger-component';
import {NoImprovementActionComponent} from './config/group/webhook/action/no-improvement-action-component';
import {NoImprovementActionCallbackParamsComponent} from './config/group/webhook/callback/no-improvement-action-callback-params-component';
import {PostWebhookCallbackComponent} from './config/group/webhook/callback/post-webhook-callback-component';
import {MembersSettingsComponent} from './config/member/members-settings.component';
import {MemberComponent} from './config/member/member-component';
import {MemberConfigService} from './config/service/member-config.service';
import {GroupSelectBadgeComponent} from './config/common/badge/group-select-badge-component';
import {GroupMembersComponent} from './group/group-members.component';
import {MemberService} from './config/member/member-service';
import {SettingsIntegrationsComponent} from './config/integrations/sq-settings-integrations.component';
import {SettingsLdapIntegrationsComponent} from './config/integrations/ldap/sq-settings-ldap-integrations.component';
import {LdapIntegrationService} from './config/integrations/ldap/ldap-integration-service';
import {SqSettingsMapComponent} from './config/common/map/sq-settings-map-component';
import {MemberSettingsRemoteUsersComponent} from './config/member/member-settings-remote-users.component';

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
    PostWebhookCallbackComponent,
    MembersSettingsComponent,
    MemberComponent,
    GroupSelectBadgeComponent,
    GroupMembersComponent,
    SettingsIntegrationsComponent,
    SettingsLdapIntegrationsComponent,
    SqSettingsMapComponent,
    MemberSettingsRemoteUsersComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    DragDropModule,
    TreeModule
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
    GroupSettingsService,
    MemberConfigService,
    MemberService,
    LdapIntegrationService
  ],
  bootstrap: [
    SonarQubeCompanionComponent
  ]
})
export class AppModule {
}
