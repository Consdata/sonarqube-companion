import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {GroupDefinition} from '../model/group-definition';
import {GroupSettingsService} from '../service/group-settings-service';


@Component({
  selector: `sq-group-detail-settings`,
  template: `
    <sq-spinner *ngIf="!loaded"></sq-spinner>
    <div *ngIf="loaded">
      <div class="sq-settings-group-title ">
        <div>Group</div>
        <div class="error">{{errorMessage}}</div>
        <div>
          <button (click)="saveGroup()">Save</button>
          <button (click)="deleteGroup()">Delete</button>
        </div>
      </div>
      <hr>
      <div class="sq-settings-container">
        <div><label class="sq-setting-label">Name</label>
          <div class="sq-setting-container"><input type="text" [(ngModel)]="config.name"/></div>
        </div>
        <div><label class="sq-setting-label">Description</label>
          <div class="sq-setting-container"><input type="text" [(ngModel)]="config.description"/></div>
        </div>
      </div>
      <div>
        <div>
          <sq-settings-subgroups [uuid]="config.uuid"></sq-settings-subgroups>
          <sq-project-link-list [uuid]="config.uuid"></sq-project-link-list>
          <sq-settings-event-component [uuid]="config.uuid"></sq-settings-event-component>
          <webhooks-list [uuid]="config.uuid"></webhooks-list>
        </div>

      </div>
    </div>`
})
export class GroupDetailSettingsComponent {
  config: GroupDefinition;
  errorMessage: string = '';
  uuid: string;
  loaded: boolean = false;
  private parentUuid: string = '';

  constructor(private settingsService: GroupSettingsService, private route: ActivatedRoute, private router: Router) {
    route
      .paramMap
      .subscribe(params => {
        this.uuid = params.get('uuid');
        this.parentUuid = params.get('parentUuid');
        this.getGroup();
      });
  }

  getGroup() {
    this.settingsService.getGroup(this.uuid).subscribe(data => {
      this.config = data;
      this.loaded = true;
    });
  }

  saveGroup() {
    this.loaded = false;
    this.settingsService.updateGroup(this.config).subscribe(data => {
      if (data.valid) {
        this.errorMessage = '';
        this.getGroup();
      } else {
        this.errorMessage = data.message;
      }
    });
  }

  deleteGroup() {
    this.loaded = false;
    this.settingsService.deleteGroup(this.config.uuid, this.parentUuid).subscribe(data => {
      if (data.valid) {
        this.router.navigateByUrl(`/settings`);
      } else {
        this.errorMessage = data.message;
      }
    });
  }
}
