import {Component} from '@angular/core';


@Component({
  selector: `sq-settings-general`,
  template: `
    <div class="sq-settings-group-title">
      <div>General</div>
    </div>
    <hr>
    <div class="sq-settings-container">
      <sq-scheduler></sq-scheduler>
    </div>
  `
})
export class GeneralSettingsComponent {
}
