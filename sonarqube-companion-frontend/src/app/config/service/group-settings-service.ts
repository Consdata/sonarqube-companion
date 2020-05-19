import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/index';
import {ValidationResult} from '../common/settings-list/settings-list-component';
import {HttpClient} from '@angular/common/http';
import {GroupDefinition, ProjectLink} from '../model/group-definition';
import {GroupEvent} from '../../group/group-event';
import {WebhookCallback, WebhookDefinition} from '../model/webhook-definition';
import {GroupLightModel} from '../model/group-light-model';


@Injectable()
export class GroupSettingsService {
  constructor(private http: HttpClient) {

  }

  getRootGroup(): Observable<GroupDefinition> {
    return this.http
      .get<GroupDefinition>('/api/v1/settings/group/');
  }

  getGroup(uuid: string): Observable<GroupDefinition> {
    return this.http
      .get<GroupDefinition>(`api/v1/settings/group/${uuid}`);
  }

  getSubgroups(uuid: string): Observable<GroupDefinition[]> {
    return this.http
      .get<GroupDefinition[]>(`api/v1/settings/group/${uuid}/groups`);
  }

  updateSubgroups(uuid: string, definitions: GroupDefinition[]): Observable<ValidationResult> {
    return this.http
      .post<ValidationResult>(`api/v1/settings/group/${uuid}/groups`, definitions);
  }


  createGroup(uuid: string, group: GroupDefinition): Observable<ValidationResult> {
    return this.http
      .post<ValidationResult>(`api/v1/settings/group/${uuid}/create`, group);
  }

  updateGroup(group: GroupDefinition): Observable<ValidationResult> {
    return this.http
      .post<ValidationResult>(`api/v1/settings/group/update`, group);
  }

  deleteGroup(uuid: string, parentUuid: string): Observable<ValidationResult> {
    return this.http
      .delete<ValidationResult>(`api/v1/settings/group/${parentUuid}/${uuid}`);
  }

  getProjectLinks(groupUuid: string): Observable<ProjectLink[]> {
    return this.http
      .get<ProjectLink[]>(`api/v1/settings/group/${groupUuid}/projectLinks`);
  }

  saveProjectLink(groupUuid: string, projectLink: ProjectLink, newItem: boolean = false): Observable<ValidationResult> {
    if (newItem) {
      return this.createProjectLink(groupUuid, projectLink);
    } else {
      return this.updateProjectLink(groupUuid, projectLink);
    }
  }

  createProjectLink(groupUuid: string, projectLink: ProjectLink): Observable<ValidationResult> {
    return this.http
      .post<ValidationResult>(`api/v1/settings/group/${groupUuid}/projectLinks/create`, projectLink);
  }

  updateProjectLink(groupUuid: string, projectLink: ProjectLink): Observable<ValidationResult> {
    return this.http
      .post<ValidationResult>(`api/v1/settings/group/${groupUuid}/projectLinks/update`, projectLink);
  }

  deleteProjectLink(groupUuid: string, projectLinkUuid: string): Observable<ValidationResult> {
    return this.http
      .delete<ValidationResult>(`api/v1/settings/group/${groupUuid}/projectLinks/${projectLinkUuid}`);
  }

  getWebhooks(groupUuid: string): Observable<WebhookDefinition[]> {
    return this.http
      .get<WebhookDefinition[]>(`api/v1/settings/group/${groupUuid}/webhooks`);
  }

  saveWebhook(groupUuid: string, webhook: WebhookDefinition, newItem: boolean = false): Observable<ValidationResult> {
    if (newItem) {
      return this.createWebhook(groupUuid, webhook);
    } else {
      return this.updateWebhook(groupUuid, webhook);
    }
  }

  createWebhook(groupUuid: string, webhook: WebhookDefinition): Observable<ValidationResult> {
    return this.http
      .post<ValidationResult>(`api/v1/settings/group/${groupUuid}/webhooks/create`, webhook);
  }

  updateWebhook(groupUuid: string, webhook: WebhookDefinition): Observable<ValidationResult> {
    return this.http
      .post<ValidationResult>(`api/v1/settings/group/${groupUuid}/webhooks/update`, webhook);
  }

  deleteWebhook(groupUuid: string, webhookUuid: string): Observable<ValidationResult> {
    return this.http
      .delete<ValidationResult>(`api/v1/settings/group/${groupUuid}/webhooks/${webhookUuid}`);
  }

  getCallbacks(groupUuid: string, webhookUuid: string): Observable<WebhookCallback[]> {
    return this.http
      .get<WebhookCallback[]>(`api/v1/settings/group/${groupUuid}/webhooks/${webhookUuid}/callbacks`);
  }

  saveCallback(groupUuid: string, webhookUuid: string, callback: WebhookCallback, newItem: boolean = false): Observable<ValidationResult> {
    if (newItem) {
      return this.createCallback(groupUuid, webhookUuid, callback);
    } else {
      return this.updateCallback(groupUuid, webhookUuid, callback);
    }
  }

  createCallback(groupUuid: string, webhookUuid: string, callback: WebhookCallback): Observable<ValidationResult> {
    return this.http
      .post<ValidationResult>(`api/v1/settings/group/${groupUuid}/webhooks/${webhookUuid}/callbacks/create`, callback);
  }

  updateCallback(groupUuid: string, webhookUuid: string, callback: WebhookCallback): Observable<ValidationResult> {
    return this.http
      .post<ValidationResult>(`api/v1/settings/group/${groupUuid}/webhooks/${webhookUuid}/callbacks/update`, callback);
  }

  deleteCallback(groupUuid: string, webhookUuid: string, callbackUuid: string): Observable<ValidationResult> {
    return this.http
      .delete<ValidationResult>(`api/v1/settings/group/${groupUuid}/webhooks/${webhookUuid}/callbacks/${callbackUuid}`);
  }


  getEvents(groupUuid: string): Observable<GroupEvent[]> {
    return this.http
      .get<GroupEvent[]>(`api/v1/settings/group/${groupUuid}/events`);
  }

  saveEvent(groupUuid: string, event: GroupEvent, newItem: boolean = false): Observable<ValidationResult> {
    if (newItem) {
      return this.createEvent(groupUuid, event);
    } else {
      return this.updateEvent(groupUuid, event);
    }
  }

  createEvent(groupUuid: string, event: GroupEvent): Observable<ValidationResult> {
    return this.http
      .post<ValidationResult>(`api/v1/settings/group/${groupUuid}/events/create`, event);
  }

  updateEvent(groupUuid: string, event: GroupEvent): Observable<ValidationResult> {
    return this.http
      .post<ValidationResult>(`api/v1/settings/group/${groupUuid}/events/update`, event);
  }

  deleteEvent(groupUuid: string, eventUuid: string): Observable<ValidationResult> {
    return this.http
      .delete<ValidationResult>(`api/v1/settings/group/${groupUuid}/events/${eventUuid}`);
  }

  getParent(nodeId: string): Observable<string> {
    return this.http
      .get(`api/v1/settings/group/${nodeId}/parent/`, {responseType: 'text'});
  }

  getAll(): Observable<GroupLightModel[]> {
    return this.http.get<GroupLightModel[]>(`api/v1/settings/group/all`);
  }

}
