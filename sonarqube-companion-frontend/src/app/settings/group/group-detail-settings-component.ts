import {Component} from "@angular/core";
import {SettingsService} from "../service/settings-service";
import {ActivatedRoute} from "@angular/router";
import {GroupDefinition} from "../model/group-definition";



@Component({
  selector: `sq-group-detail-settings`,
  template: `
    <sq-spinner *ngIf="!config"></sq-spinner>
    <div *ngIf="config">
      <div class="sq-settings-group-title">General</div>
      <div>
        <div>
          <label>UUID:</label>
          <input type="text" [(ngModel)]="config.uuid"/>
        </div>
        <div>
          <label>Name:</label>
          <input type="text" [(ngModel)]="config.name"/>
        </div>
        <div>
          <label>Description:</label>
          <input type="text" [(ngModel)]="config.description"/>
        </div>
      </div>
      <div>
        <div class="sq-settings-group-title">Groups</div>
        <div id="tree1"></div>
      </div>
      <div>
        <div class="sq-settings-group-title">Projects</div>
      </div>
      <div>
        <div class="sq-settings-group-title">Events</div>
      </div>
      <div>
        <div class="sq-settings-group-title">Webhooks</div>
      </div>
    </div>`
})
export class GroupDetailSettingsComponent {
  private config: GroupDefinition;

  constructor(private settingsService: SettingsService, private route: ActivatedRoute) {
    route
      .paramMap
      .subscribe(params => {
        settingsService.getGroup(params.get('uuid')).subscribe(data => {
          this.config = data;


        });
      });
  }
}
