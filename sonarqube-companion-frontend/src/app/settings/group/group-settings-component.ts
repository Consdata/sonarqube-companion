import {Component, Input} from "@angular/core";
import {GroupDefinition} from "../model/group-definition";

@Component({
  selector: `sq-settings-group`,
  template: `
    <div class="name" routerLink="/settings/group/{{definition?.uuid}}">{{definition?.name}}</div>
    <div *ngIf="definition?.groups" class="subgroups">
      <sq-settings-group *ngFor="let group of definition.groups"
                         [definition]="group"></sq-settings-group>
    </div>
  `
})
export class GroupSettingsComponent {
  @Input()
  private definition: GroupDefinition;

}
