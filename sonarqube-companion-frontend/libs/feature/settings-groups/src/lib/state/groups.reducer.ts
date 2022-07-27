import {GroupLightModel} from '@sonarqube-companion-frontend/data-access/groups';
import {GroupErrorModel} from '../group-error-model';
import {Action, createReducer, on} from '@ngrx/store';
import produce from 'immer';
import {loadGroups, loadGroupsFailed, loadGroupsSuccess} from './groups.actions';

export const FEATURE = 'groupsSettings';

export interface State {
  rootGroup: GroupLightModel | null;
  selectedGroup: GroupLightModel | null;
  loaders: {
    rootGroup: boolean;
    currentGroup: boolean
  },
  errors: GroupErrorModel
}

export const initialState: State = {
  rootGroup: null,
  selectedGroup: null,
  loaders: {
    rootGroup: false,
    currentGroup: false
  },
  errors: {
    id: '',
    authUser: '',
    authPassword: '',
    authToken: '',
    url: ''
  }
};


const groupsReducer = createReducer(
  initialState,
  on(loadGroups, (state) => produce(state, draft => {
    draft.loaders.rootGroup = true;
  })),
  on(loadGroupsSuccess, (state, payload) => produce(state, draft => {
    draft.rootGroup = payload.rootGroup;
    draft.loaders.rootGroup = false;
  })),
  on(loadGroupsFailed, (state) => produce(state, draft => {
    draft.rootGroup = null;
    draft.loaders.rootGroup = false;
  })),
);

export function reducer(state: State | undefined, action: Action) {
  return groupsReducer(state, action);
}
