import {Component, EventEmitter, Output} from "@angular/core";
import {SettingsService} from "./service/settings-service";


@Component({
  selector: `sq-settings-general`,
  template: `
    <div class="sq-settings-group-title">
      <div>General</div>
      <hr>
    </div>
    <div class="sq-settings-container">
      <sq-scheduler></sq-scheduler>
      <div class="error-message" *ngIf="errorMessage">{{errorMessage}}</div>
    </div>
  `
})
export class GeneralSettingsComponent {

  @Output()
  private reloadSettings = new EventEmitter<void>();
  private errorMessage: string;

  constructor(private settingsService: SettingsService) {
  }

  restoreDefaultSettings() {
    this.settingsService.restoreDefaultSettings().subscribe(confirmed => {
      if (confirmed) {
        this.reloadSettings.emit();
      } else {
        this.errorMessage = "Unable to restore default settings";
      }
    });
  }
}
