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
import { GroupsComponent } from './groups/groups.component';
import { GroupComponent } from './group/group.component';
import { MembersComponent } from './members/members.component';
import { MemberComponent } from './member/member.component';
import { IntegrationsComponent } from './integrations/integrations.component';
import { WebhooksComponent } from './webhooks/webhooks.component';
import { WebhookComponent } from './webhook/webhook.component';
import {UiComponentsSideGroupsTreeModule} from '@sonarqube-companion-frontend/ui-components/side-groups-tree';
import { GroupPreviewComponent } from './group-preview/group-preview.component';
import {UiComponentsCrumbsModule} from '@sonarqube-companion-frontend/ui-components/crumbs';
import { GroupProjectsComponent } from './group-projects/group-projects.component';
import { GroupEventsComponent } from './group-events/group-events.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(settingsRouting),
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
    UiComponentsCrumbsModule
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
    GroupEventsComponent
  ],
  exports: [
    SettingsComponent
  ]
})
export class SettingsModule {
}
