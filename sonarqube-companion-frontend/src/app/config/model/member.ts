import {GroupLightModel} from './group-light-model';

export class Member {
  firstName: string;
  lastName: string;
  mail: string;
  uuid: string;
  sonarId: string;
  aliases: string[];
  memberOf: GroupLightModel[];
}
