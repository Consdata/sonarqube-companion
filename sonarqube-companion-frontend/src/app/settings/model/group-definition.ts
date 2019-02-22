import {WebhookDefinition} from "./webhook-definition";
import {GroupEvent} from "../../group/group-event";

export class GroupDefinition {
  constructor(data: any) {
    if (data) {
      this.uuid = data.uuid;
      this.name = data.name;
      this.description = data.description;

      //this.projectLinks = data.projectLinks.map(projectLink => new ProjectLink(projectLink));
      this.groups = data.groups.map(group => new GroupDefinition(group));
     // this.events = data.events.map(event => new GroupEvent(event));
    }
  }


  private uuid: string;
  private name: string;
  private description: string;

  private projectLinks: ProjectLink[];
  private groups: GroupDefinition[];
  private events: GroupEvent[];
  private webhooks: WebhookDefinition[];
}

export class ProjectLink {
  constructor(data: any) {
    if (data) {
      this.serverId = data.serverId;
      this.type = data.type;
      this.config = new ProjectLinkConfig(data.config);
    }
  }

  private serverId: string;
  private type: string;
  private config: ProjectLinkConfig;
}

export class ProjectLinkConfig {
  constructor(data: any) {
    this.include = data.include;
    this.exclude = data.exclude;
  }

  private include: string[];
  private exclude: string[];
}
