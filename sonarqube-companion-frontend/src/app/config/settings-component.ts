import {Component} from '@angular/core';


@Component({
  selector: `sq-settings`,
  template: `
    <div class="sq-settings-container">
      <sq-settings-general></sq-settings-general>
      <sq-settings-servers></sq-settings-servers>
      <sq-settings-members></sq-settings-members>
      <sq-settings-groups></sq-settings-groups>
    </div>
  `
})
export class SettingsComponent {
}
