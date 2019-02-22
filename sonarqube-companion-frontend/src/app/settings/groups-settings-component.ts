import {Component, OnInit} from "@angular/core";
import {GroupDefinition} from "./model/group-definition";
import {SettingsService} from "./service/settings-service";


@Component({
  selector: `sq-settings-groups`,
  template: `
    <div class="sq-settings-group-title">
      <div>Groups</div>
      <hr>
    </div>
    <div class="sq-settings-container">
      <sq-settings-group [definition]="rootGroup"></sq-settings-group>
    </div>
  `
})
export class GroupsSettingsComponent implements OnInit {

  private rootGroup: GroupDefinition;

  constructor(private settingsService: SettingsService) {
  }

  ngOnInit(): void {
    this.settingsService.getRootGroup().subscribe(data => this.rootGroup = data);
  }

}
