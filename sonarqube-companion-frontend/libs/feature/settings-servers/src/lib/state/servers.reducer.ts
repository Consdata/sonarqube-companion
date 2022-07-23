import {Action, createReducer, on} from '@ngrx/store';
import {
  createServer,
  deleteServer,
  loadServers,
  loadServersFailed,
  loadServersSuccess, saveServer,
  saveServerFailed,
  saveServerSuccess,
  selectServer,
  selectServerById,
  updateServer
} from './servers.actions';
import produce from 'immer';
import {ServerConfig} from '@sonarqube-companion-frontend/data-access/settings';
import {ServerErrorModel, setErrors} from '../server-error-model';

export const FEATURE = 'serverSettings';

export interface State {
  servers: ServerConfig[];
  selectedServer: ServerConfig | null;
  loaders: {
    servers: boolean;
    currentServer: boolean
  },
  errors: ServerErrorModel
}

export const initialState: State = {
  servers: [],
  selectedServer: null,
  loaders: {
    servers: false,
    currentServer: false
  },
  errors: {
    id: '',
    authUser: '',
    authPassword: '',
    authToken: '',
    url: ''
  }
};


const serversReducer = createReducer(
  initialState,
  on(loadServers, (state) => produce(state, draft => {
    draft.loaders.servers = true;
  })),
  on(createServer, (state) => produce(state, draft => {
    draft.loaders.servers = true;
  })),
  on(loadServersSuccess, (state, payload) => produce(state, draft => {
    draft.servers = payload.servers;
    draft.loaders.servers = false;
  })),
  on(loadServersFailed, (state) => produce(state, draft => {
    draft.servers = [];
    draft.loaders.servers = false;
  })),
  on(selectServer, (state, action) => produce(state, draft => {
    draft.selectedServer = state.servers.filter(server => server.id === action.server.id)[0];
  })),
  on(selectServerById, (state, action) => produce(state, draft => {
    draft.selectedServer = state.servers.filter(server => server.id === action.id)[0];
  })),
  on(deleteServer, (state) => produce(state, draft => {
    draft.loaders.servers = true;
    draft.selectedServer = null;
  })),
  on(saveServerFailed, (state, action) => produce(state, draft => {
    draft.loaders.currentServer = false;
    draft.errors = setErrors(action.validationResult);
  })),
  on(saveServerSuccess, (state, action) => produce(state, draft => {
    draft.errors = setErrors(action.validationResult);
    draft.loaders.currentServer = false;
  })),
  on(saveServer, (state, action) => produce(state, draft => {
    draft.loaders.currentServer = true;
  })),
  on(updateServer, (state, action) => produce(state, draft => {
    draft.selectedServer = {...state.selectedServer, ...action.patch};
  }))
);

export function reducer(state: State | undefined, action: Action) {
  return serversReducer(state, action);
}
