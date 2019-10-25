import {Component, OnInit} from '@angular/core';
import {GroupDefinition} from './model/group-definition';
import {GroupSettingsService} from './service/group-settings-service';


@Component({
  selector: `sq-settings-groups`,
  template: `
    <div class="sq-settings-container">
      <sq-settings-subgroups *ngIf="rootGroup" [uuid]="rootGroup.uuid"></sq-settings-subgroups>
    </div>
  `
})
export class GroupsSettingsComponent implements OnInit {

  rootGroup: GroupDefinition;

  constructor(private settingsService: GroupSettingsService) {
  }

  ngOnInit(): void {
    this.settingsService.getRootGroup().subscribe(data => this.rootGroup = data);
  }

}
