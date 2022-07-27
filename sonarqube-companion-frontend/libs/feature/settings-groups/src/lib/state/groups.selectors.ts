import {FEATURE, State} from './groups.reducer';
import {createFeatureSelector, createSelector} from '@ngrx/store';

export const featureSelector = createFeatureSelector<State>(FEATURE);
export const selectRootGroup = createSelector(featureSelector, (state: any) => state.rootGroup);
export const selectRootGroupLoading = createSelector(featureSelector, (state: any) => state.loaders.rootGroup);
