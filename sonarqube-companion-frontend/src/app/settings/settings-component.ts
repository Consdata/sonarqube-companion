import {Component} from "@angular/core";
import {SettingsService} from "./service/settings-service";


@Component({
  selector: `sq-settings`,
  template: `
    <div class="sq-settings-container">
        <sq-settings-general></sq-settings-general>
        <sq-settings-servers></sq-settings-servers>
        <sq-settings-groups></sq-settings-groups>
    </div>
  `
})
export class SettingsComponent {


}
