import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import { appRoutes } from './app.routing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UiComponentsSidebarModule } from '@sonarqube-companion-frontend/ui-components/sidebar';
import { NotFoundComponent } from './not-found/not-found.component';
import { HttpClientModule } from '@angular/common/http';
import { TopbarComponent } from './topbar.component';
import { AppSidenavComponent } from './sidenav/app-sidenav.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { StoreModule } from '@ngrx/store';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { StoreRouterConnectingModule } from '@ngrx/router-store';
import {EffectsModule} from '@ngrx/effects';
import {NgxsRouterPluginModule} from '@ngxs/router-plugin';
import {NgxsModule} from '@ngxs/store';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    TopbarComponent,
    AppSidenavComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    UiComponentsSidebarModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    StoreModule.forRoot({}),
    StoreDevtoolsModule.instrument(),
    StoreRouterConnectingModule.forRoot(),
    NgxsModule.forRoot(),
    NgxsRouterPluginModule.forRoot(),
    EffectsModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
