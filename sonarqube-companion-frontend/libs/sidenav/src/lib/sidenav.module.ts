import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GroupSidenavComponent} from './group-sidenav/group-sidenav.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {MatIconModule} from '@angular/material/icon';
import {UiComponentsSideGroupsTreeModule} from '@sonarqube-companion-frontend/ui-components/side-groups-tree';
import { VersionComponent } from './version/version.component';
import {HttpClientModule} from '@angular/common/http';

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
    VersionComponent
  ],
  exports: [
    GroupSidenavComponent
  ]
})
export class SidenavModule {
}
