import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {RouterModule} from '@angular/router';
import {appRoutes} from './app.routing';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {UiComponentsSidebarModule} from '@sonarqube-companion-frontend/ui-components/sidebar';
import {NotFoundComponent} from './not-found/not-found.component';
import {HttpClientModule} from '@angular/common/http';
import {TopbarComponent} from './topbar.component';
import {AppSidenavComponent} from './sidenav/app-sidenav.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    TopbarComponent,
    AppSidenavComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    UiComponentsSidebarModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule

  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {
}
