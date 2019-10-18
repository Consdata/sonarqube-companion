import {WebhookDefinition} from './webhook-definition';
import {GroupEvent} from '../../group/group-event';

export class GroupDefinition {
  uuid: string;
  name: string;
  description: string;
  projectLinks: ProjectLink[] = [];
  groups: GroupDefinition[] = [];
  events: GroupEvent[] = [];
  webhooks: WebhookDefinition[] = [];

  constructor(data: any) {
    if (data) {
      this.uuid = data.uuid;
      this.name = data.name;
      this.description = data.description;

      if (data.projectLinks) {
        this.projectLinks = data.projectLinks.map(projectLink => new ProjectLink(projectLink));
      }
      if (data.groups) {
        this.groups = data.groups.map(group => new GroupDefinition(group));
      }
      if (data.events) {
        this.events = data.events.map(event => new GroupEvent(event));
      }
      if (data.webhooks) {
        this.webhooks = data.webhooks.map(webhook => new WebhookDefinition(webhook));
      }
    }
  }
}

export class ProjectLink {
  uuid: string;
  serverId: string;
  type: string;
  config: ProjectLinkConfig;

  constructor(data: any = {}) {
    if (data) {
      this.uuid = data.uuid;
      this.serverId = data.serverId;
      this.type = data.type;


      if (this.type === 'REGEX') {
        this.config = new RegexProjectLinkConfig(data.config);
      }

      if (this.type === 'DIRECT') {
        this.config = new DirectProjectLinkConfig(data.config);
      }

    }
  }
}

export class ProjectLinkConfig {

}

export class RegexProjectLinkConfig extends ProjectLinkConfig {
  include: string[];
  exclude: string[];

  constructor(data: any) {
    super();
    this.include = data.include;
    this.exclude = data.exclude;
  }
}


export class DirectProjectLinkConfig extends ProjectLinkConfig {
  link: string;

  constructor(data: any) {
    super();
    this.link = data.link;
  }
}
