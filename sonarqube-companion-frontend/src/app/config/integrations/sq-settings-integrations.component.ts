import {Component} from '@angular/core';

@Component({
  selector: 'sq-settings-integrations',
  template: `
    <div class="sq-settings-container">
      <div class="header">
        <div class="sq-settings-group-title">Integrations</div>
        <hr>
        <sq-settings-ldap-integrations></sq-settings-ldap-integrations>
      </div>
    </div>

  `
})

export class SettingsIntegrationsComponent {
}
