import {Component, Input, Type} from '@angular/core';
import {ValidationResult} from '../../common/settings-list/settings-list-component';
import {ProjectLink} from '../../model/group-definition';
import {SettingsListDetailsItem} from '../../common/settings-list/settings-list-item';
import {Subject} from 'rxjs/index';
import {ProjectLinkComponent} from './project-link-component';
import {GroupSettingsService} from '../../service/group-settings-service';
import * as uuid from 'uuid';

@Component({
  selector: `sq-project-link-list`,
  template: `
    <div class="sq-settings-container">
      <sq-spinner *ngIf="!loaded"></sq-spinner>
      <sq-settings-list
        [loaded]="loaded"
        [details]="projectLinkDetailsType"
        [(data)]="projectLinks"
        [title]="'Projects'"
        [foldedListLabel]="'%number% more events defined'"
        [newItem]="newItem.asObservable()"
        [validation]="validation.asObservable()"
        [label]="getLabel"
        (addClick)="addEvent()"
        (removeItem)="removeEvent($event)"
        (saveItem)="saveEvent($event)"
      ></sq-settings-list>
    </div>
  `
})
export class ProjectLinkListComponent {
  loaded: boolean = false;
  projectLinks: ProjectLink[] = [];
  projectLinkDetailsType: Type<SettingsListDetailsItem> = ProjectLinkComponent;
  newItem: Subject<ProjectLink> = new Subject<ProjectLink>();
  validation: Subject<ValidationResult> = new Subject<ValidationResult>();

  constructor(private settingsService: GroupSettingsService) {
  }

  _uuid: string;

  @Input()
  set uuid(id: string) {
    this._uuid = id;
    this.load();
  }

  addEvent(): void {
    const newProjectLink: ProjectLink = new ProjectLink();
    this.projectLinks.push(newProjectLink);
    this.newItem.next(newProjectLink);
  }

  removeEvent(projectLink: ProjectLink): void {
    this.loaded = false;
    this.settingsService.deleteProjectLink(this._uuid, projectLink.uuid).subscribe(validationResult => {
      if (validationResult.valid) {
        this.load();
      } else {
        this.loaded = true;
      }
      validationResult.item = projectLink;
      this.validation.next(validationResult);

    }, er => {
      this.loaded = true;
    });
  }

  saveEvent(event: any): void {
    this.loaded = false;
    this.settingsService.saveProjectLink(this._uuid, event.item, event.newItem).subscribe(validationResult => {
      if (validationResult.valid) {
        this.load();
      } else {
        this.loaded = true;
      }
      validationResult.item = event.item;
      this.validation.next(validationResult);
    }, er => {
      this.loaded = true;
    });
  }

  getLabel(item: ProjectLink): string {
    return item.serverId;
  }

  load() {
    this.loaded = false;
    this.settingsService.getProjectLinks(this._uuid).subscribe(data => {
      this.projectLinks = data;
      this.loaded = true;
    });
  }

}
