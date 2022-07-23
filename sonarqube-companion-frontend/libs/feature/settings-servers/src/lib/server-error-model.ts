import {ValidationResult} from '@sonarqube-companion-frontend/data-access/settings';

export interface ServerErrorModel {
  id: string;
  url: string;
  authToken: string;
  authPassword: string;
  authUser: string;
}


export function setErrors(validationResult: ValidationResult): ServerErrorModel {
  const errors = {
    id: '',
    authUser: '',
    authPassword: '',
    authToken: '',
    url: ''
  };
  if (validationResult.code === 'ID_CONSTRAINT') {
    errors.id = validationResult.message;
  }
  if (validationResult.code === 'URL_CONSTRAINT') {
    errors.url = validationResult.message;
  }
  if (validationResult.code === 'AUTH_USER_CONSTRAINT') {
    errors.authUser = validationResult.message;
  }
  if (validationResult.code === 'AUTH_PASSWORD_CONSTRAINT') {
    errors.authPassword = validationResult.message;
  }
  if (validationResult.code === 'AUTH_TOKEN_CONSTRAINT') {
    errors.authToken = validationResult.message;
  }
  return errors;
}
