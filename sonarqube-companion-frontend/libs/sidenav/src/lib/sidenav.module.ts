import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GroupSidenavComponent} from './group-sidenav/group-sidenav.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {MatIconModule} from '@angular/material/icon';
import {UiComponentsSideGroupsTreeModule} from '@sonarqube-companion-frontend/ui-components/side-groups-tree';
import { VersionComponent } from './version/version.component';
import {HttpClientModule} from '@angular/common/http';
import {MainSidenavComponent} from './main-sidenav/main-sidenav.component';
import {SettingsSidenavComponent} from './settings-sidenav/settings-sidenav.component';

@NgModule({
  imports: [
    CommonModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    HttpClientModule,
    UiComponentsSideGroupsTreeModule
  ],
  declarations: [
    GroupSidenavComponent,
    VersionComponent,
    MainSidenavComponent,
    SettingsSidenavComponent
  ],
  exports: [
    GroupSidenavComponent,
    MainSidenavComponent,
    SettingsSidenavComponent
  ]
})
export class SidenavModule {
}
