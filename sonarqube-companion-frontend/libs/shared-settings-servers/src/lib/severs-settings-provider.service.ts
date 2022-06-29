import {ServerSettings} from './server-settings';


export interface SeversSettingsProviderService {
  getServersSettings(): ServerSettings[];
}
