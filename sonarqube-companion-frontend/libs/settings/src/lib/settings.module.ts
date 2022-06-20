import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {settingsRouting} from './settings.routing';
import {SettingsComponent} from './settings.component';
import {MatDividerModule} from '@angular/material/divider';
import {MatRippleModule} from '@angular/material/core';
import {ServersComponent} from './servers/servers.component';
import {ServerComponent} from './server/server.component';
import {HttpClientModule} from '@angular/common/http';
import {BasicAuthComponent} from './basic-auth/basic-auth.component';
import {TokenAuthComponent} from './token-auth/token-auth.component';
import {UiComponentsInputModule} from '@sonarqube-companion-frontend/ui-components/input';
import {UiComponentsChipsModule} from '@sonarqube-companion-frontend/ui-components/chips';
import {UiComponentsToggleModule} from '@sonarqube-companion-frontend/ui-components/toggle';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {UiComponentsSelectModule} from '@sonarqube-companion-frontend/ui-components/select';
import {UtilsModule} from '@sonarqube-companion-frontend/utils';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {GroupsComponent} from './groups/groups.component';
import {GroupComponent} from './group/group.component';
import {MembersComponent} from './members/members.component';
import {MemberComponent} from './member/member.component';
import {IntegrationsComponent} from './integrations/integrations.component';
import {WebhooksComponent} from './webhooks/webhooks.component';
import {WebhookComponent} from './webhook/webhook.component';
import {UiComponentsSideGroupsTreeModule} from '@sonarqube-companion-frontend/ui-components/side-groups-tree';
import {GroupPreviewComponent} from './group-preview/group-preview.component';
import {UiComponentsCrumbsModule} from '@sonarqube-companion-frontend/ui-components/crumbs';
import {GroupProjectsComponent} from './group-projects/group-projects.component';
import {GroupEventsComponent} from './group-events/group-events.component';
import {NgxsModule} from '@ngxs/store';
import {NgxsReduxDevtoolsPluginModule} from '@ngxs/devtools-plugin';
import {SettingsState} from './state/settings-state';
import {GroupsSettingsState} from './state/groups-settings-state';
import {MatTreeModule} from '@angular/material/tree';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {HoverClassDirective} from './utils/hover.directive';
import {MatTooltipModule} from '@angular/material/tooltip';
import {NgxsActionsExecutingModule} from '@ngxs-labs/actions-executing';
import {GroupSettingsState} from './state/group-settings-state';
import {CdkAccordionModule} from '@angular/cdk/accordion';
import {GroupMembersComponent} from './group-members/group-members.component';
import {CdkTableModule} from '@angular/cdk/table';
import {UiComponentsTableModule} from '@sonarqube-companion-frontend/ui-components/table';
import {GROUPS_SETTINGS_SERVICE_TOKEN} from './groups/groups-settings.service';
import {GroupsSettingsStateService} from './state/services/groups-settings-state.service';
import {SidenavModule} from '@sonarqube-companion-frontend/sidenav';

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(settingsRouting),
        NgxsModule.forRoot([SettingsState, GroupsSettingsState, GroupSettingsState], {developmentMode: true}),
        NgxsReduxDevtoolsPluginModule.forRoot(),
        NgxsActionsExecutingModule.forRoot(),
        MatDividerModule,
        MatRippleModule,
        HttpClientModule,
        UiComponentsInputModule,
        UiComponentsChipsModule,
        UiComponentsToggleModule,
        MatIconModule,
        MatButtonModule,
        UiComponentsSelectModule,
        UtilsModule,
        MatProgressSpinnerModule,
        UiComponentsSideGroupsTreeModule,
        UiComponentsCrumbsModule,
        MatTreeModule,
        DragDropModule,
        MatTooltipModule,
        CdkAccordionModule,
        CdkTableModule,
        UiComponentsTableModule,
        SidenavModule
    ],
  declarations: [
    SettingsComponent,
    ServersComponent,
    ServerComponent,
    BasicAuthComponent,
    TokenAuthComponent,
    GroupsComponent,
    GroupComponent,
    MembersComponent,
    MemberComponent,
    IntegrationsComponent,
    WebhooksComponent,
    WebhookComponent,
    GroupPreviewComponent,
    GroupProjectsComponent,
    GroupEventsComponent,
    HoverClassDirective,
    GroupMembersComponent
  ],
  exports: [
    SettingsComponent
  ],
  providers: [
    {provide: GROUPS_SETTINGS_SERVICE_TOKEN, useClass: GroupsSettingsStateService}
  ]
})

export class SettingsModule {
}
