import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {RouterModule} from '@angular/router';
import {appRoutes} from './app.routing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {UiComponentsSidebarModule} from '@sonarqube-companion-frontend/ui-components/sidebar';
import { NotFoundComponent } from './not-found/not-found.component';
import {HttpClientModule} from '@angular/common/http';
import {SidenavModule} from '@sonarqube-companion-frontend/sidenav';
import {TopbarComponent} from './topbar.component';

@NgModule({
  declarations: [AppComponent, NotFoundComponent, TopbarComponent],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    UiComponentsSidebarModule,
    BrowserAnimationsModule,
    HttpClientModule,
    SidenavModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {
}
