import {Component, EventEmitter, Input, Output} from '@angular/core';
import {RegexProjectLinkConfig} from '../../model/group-definition';

@Component({
  selector: 'sq-regex-project-link',
  template: `
    <div>
      <div class="element"><label class="sq-setting-label">Include</label>
        <sq-settings-badge [items]="_projectLinkConfig.include" (itemsChange)="onIncludeItemsChange($event)"
                           [title]=""></sq-settings-badge>
      </div>
      <div class="element"><label class="sq-setting-label">Exclude</label>
        <sq-settings-badge [items]="_projectLinkConfig.exclude" (itemsChange)="onExcludeItemsChange($event)"
                           [title]=""></sq-settings-badge>
      </div>
    </div>`
})

export class RegexProjectLinkComponent {
  @Output()
  projectLinkConfigChange: EventEmitter<RegexProjectLinkConfig> = new EventEmitter();

  _projectLinkConfig: RegexProjectLinkConfig;

  @Input()
  set projectLinkConfig(data: RegexProjectLinkConfig) {
    if (data && data.include && data.exclude) {
      this._projectLinkConfig = data;
    } else {
      this._projectLinkConfig = new RegexProjectLinkConfig({include: [], exclude: []});
    }
  }

  onIncludeItemsChange(items: string[]) {
    this._projectLinkConfig.include = items;
    this.projectLinkConfigChange.emit(this._projectLinkConfig);
  }

  onExcludeItemsChange(items: string[]) {
    this._projectLinkConfig.exclude = items;
    this.projectLinkConfigChange.emit(this._projectLinkConfig);
  }
}
