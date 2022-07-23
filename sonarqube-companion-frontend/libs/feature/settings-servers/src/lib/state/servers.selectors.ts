import {FEATURE, State} from './servers.reducer';
import {createSelector} from '@ngxs/store';
import {createFeatureSelector} from '@ngrx/store';

// TODO bug in feature selectors?
export const featureSelector = createFeatureSelector<State>(FEATURE);
export const selectServers = createSelector([featureSelector], (state: any) => state[FEATURE].servers);
export const selectServersLoading = createSelector([featureSelector], (state: any) => state[FEATURE].loaders.servers);
export const selectedServer = createSelector([featureSelector], (state: any) => state[FEATURE].selectedServer);
export const selectServerErrors = createSelector([featureSelector], (state: any) => state[FEATURE].errors);
export const selectCurrentServerLoading = createSelector([featureSelector], (state: any) => state[FEATURE].loaders.currentServer);
