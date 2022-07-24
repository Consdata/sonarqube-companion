import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GroupSidenavComponent} from './group-sidenav/group-sidenav.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {MatIconModule} from '@angular/material/icon';
import {HttpClientModule} from '@angular/common/http';
import {UiSideTreeModule} from '@sonarqube-companion-frontend/ui/side-tree';

@NgModule({
  imports: [
    CommonModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    HttpClientModule,
    UiSideTreeModule
  ],
  declarations: [GroupSidenavComponent],
  exports: [GroupSidenavComponent],
})
export class SidenavModule {
}
