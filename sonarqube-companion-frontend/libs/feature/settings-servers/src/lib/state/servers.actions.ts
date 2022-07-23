import {createAction, props} from '@ngrx/store';
import {ServerConfig, ValidationResult} from '@sonarqube-companion-frontend/data-access/settings';

export const loadServers = createAction(
  '[Settings Servers] Load servers'
);

export const loadServersSuccess = createAction(
  '[Settings Servers] Load servers success',
  props<{ servers: ServerConfig[] }>()
);

export const loadServersFailed = createAction(
  '[Settings Servers] Load servers failed'
);

export const selectServer = createAction(
  '[Settings Servers] Select server',
  props<{ server: ServerConfig }>()
);

export const selectServerById = createAction(
  '[Settings Servers] Select server by id',
  props<{ id: string }>()
);

export const saveServer = createAction(
  '[Settings Servers] Save server',
  props<{ server: ServerConfig }>()
);

export const updateServer = createAction(
  '[Settings Servers] Update server',
  props<{ patch: any }>()
);

export const saveServerSuccess = createAction(
  '[Settings Servers] Save server success',
  props<{ validationResult: ValidationResult }>()
);

export const saveServerFailed = createAction(
  '[Settings Servers] Save server failed',
  props<{ validationResult: ValidationResult }>()
);

export const deleteServer = createAction(
  '[Settings Servers] Delete server',
  props<{ server: ServerConfig }>()
);

export const deleteServerSuccess = createAction(
  '[Settings Servers] Delete server success'
);


export const deleteServerFailed = createAction(
  '[Settings Servers] Delete server failed'
);

export const createServer = createAction(
  '[Settings Servers] Create server'
);

export const createServerSuccess = createAction(
  '[Settings Servers] Create server success'
);

export const createServerFailed = createAction(
  '[Settings Servers] Create server failed'
);
