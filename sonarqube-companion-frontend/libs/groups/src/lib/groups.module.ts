import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {groupsRouting} from './groups.routing';
import {GroupsComponent} from './groups.component';
import {SidenavModule} from '@sonarqube-companion-frontend/sidenav';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(groupsRouting),
    SidenavModule

  ],
  declarations: [
    GroupsComponent
  ],
})
export class GroupsModule {
}
