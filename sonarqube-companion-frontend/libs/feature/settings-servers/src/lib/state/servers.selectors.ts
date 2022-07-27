import {FEATURE, State} from './servers.reducer';
import {createFeatureSelector, createSelector} from '@ngrx/store';

export const featureSelector = createFeatureSelector<State>(FEATURE);
export const selectServers = createSelector(featureSelector, (state: any) => state.servers);
export const selectServersLoading = createSelector(featureSelector, (state: any) => state.loaders.servers);
export const selectedServer = createSelector(featureSelector, (state: any) => state.selectedServer);
export const selectServerErrors = createSelector(featureSelector, (state: any) => state.errors);
export const selectCurrentServerLoading = createSelector(featureSelector, (state: any) => state.loaders.currentServer);
