import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ServersComponent} from './servers.component';
import {RouterModule} from '@angular/router';
import {serversRouting} from './servers.routing';
import {UiSideListModule} from '@sonarqube-companion-frontend/ui/side-list';
import {MatListModule} from '@angular/material/list';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {ServerComponent} from './server.component';
import {StoreModule} from '@ngrx/store';
import {DataAccessSettingsModule} from '@sonarqube-companion-frontend/data-access/settings';
import {EffectsModule} from '@ngrx/effects';
import {ServersEffects} from './state/servers.effects';
import {SharedEffectsModule} from '@sonarqube-companion-frontend/shared/effects';
import {FEATURE, reducer} from './state/servers.reducer';
import {UtilStateModule} from '@sonarqube-companion-frontend/util/state';
import {UiInputModule} from '@sonarqube-companion-frontend/ui/input';
import {MatDividerModule} from '@angular/material/divider';
import {MatTabsModule} from '@angular/material/tabs';
import {UiChipsModule} from '@sonarqube-companion-frontend/ui/chips';
import {UiButtonModule} from '@sonarqube-companion-frontend/ui/button';
import {UiSelectModule} from '@sonarqube-companion-frontend/ui/select';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(serversRouting),
        UiSideListModule,
        MatListModule,
        MatIconModule,
        MatDividerModule,
        MatButtonModule,
        StoreModule.forFeature(FEATURE, reducer),
        DataAccessSettingsModule,
        EffectsModule.forFeature([ServersEffects]),
        SharedEffectsModule,
        UtilStateModule,
        UiInputModule,
        MatTabsModule,
        UiChipsModule,
        UiButtonModule,
        UiSelectModule,
        MatToolbarModule,
        MatProgressSpinnerModule
    ],
  declarations: [ServersComponent, ServerComponent],
})
export class FeatureSettingsServersModule {
}
