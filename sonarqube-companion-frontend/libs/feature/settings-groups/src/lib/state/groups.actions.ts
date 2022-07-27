import {createAction, props} from '@ngrx/store';
import {GroupLightModel} from '@sonarqube-companion-frontend/data-access/groups';

export const loadGroups = createAction(
  '[Settings Groups] Load groups'
);

export const loadGroupsSuccess = createAction(
  '[Settings Groups] Load groups success',
  props<{ rootGroup: GroupLightModel }>()
);

export const loadGroupsFailed = createAction(
  '[Settings Groups] Load groups failed'
);
