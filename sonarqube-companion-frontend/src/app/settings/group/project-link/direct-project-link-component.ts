import {Component, EventEmitter, Input, Output} from '@angular/core';
import {DirectProjectLinkConfig} from '../../model/group-definition';

@Component({
  selector: 'sq-direct-project-link',
  template: `
    <div>
      <label class="sq-setting-label">Link</label>
      <input type="text" (ngModelChange)="onLinkChange($event)" [ngModel]="_projectLinkConfig.link"/>
    </div>`
})

export class DirectProjectLinkComponent {
  _projectLinkConfig: DirectProjectLinkConfig;

  @Input()
  set projectLinkConfig(data: DirectProjectLinkConfig) {
    if (data && data.link) {
      this._projectLinkConfig = data;
    } else {
      this._projectLinkConfig = new DirectProjectLinkConfig({});
    }
  }

  @Output()
  projectLinkConfigChange: EventEmitter<DirectProjectLinkConfig> = new EventEmitter();


  onLinkChange(link: string) {
    this._projectLinkConfig.link = link;
    this.projectLinkConfigChange.emit(this._projectLinkConfig);
  }
}
