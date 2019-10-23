import {Component} from '@angular/core';
import {ProjectLink} from '../../model/group-definition';
import {SettingsListDetailsItem} from '../../common/settings-list/settings-list-item';


@Component({
  selector: `sq-settings-project-link-component`,
  template: `
    <div class="sq-settings-detail">
      <div class="element">
        <label class="sq-setting-label">Source server id</label>
        <input type="text" [(ngModel)]="projectLink.serverId"/>
      </div>
      <div class="element">
        <label class="sq-setting-label">Project links type</label>
        <select [(ngModel)]="projectLink.type">
          <option value="REGEX">Regex</option>
          <option value="DIRECT">Direct</option>
        </select>
      </div>
      <sq-regex-project-link *ngIf="projectLink.type === 'REGEX'"
                             [(projectLinkConfig)]="projectLink.config"></sq-regex-project-link>
      <sq-direct-project-link *ngIf="projectLink.type === 'DIRECT'"
                              [(projectLinkConfig)]="projectLink.config"></sq-direct-project-link>

    </div>`
})
export class ProjectLinkComponent implements SettingsListDetailsItem {

  projectLink: ProjectLink;

  setModel(model: any): void {
    this.projectLink = model;
  }

  setMetadata(data: any): void {
  }

  getModel(): any {
    return this.projectLink;
  }

  getTitle(): string {
    return this.projectLink.serverId;
  }
}
