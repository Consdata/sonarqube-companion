import {Component, OnInit} from '@angular/core';
import {SettingsListDetailsItem} from '../common/settings-list/settings-list-item';

@Component({
  selector: 'sq-member-component',
  template: ``
})

export class MemberComponent implements OnInit, SettingsListDetailsItem {
  getModel(): any {
  }

  getTitle(): string {
    return '';
  }

  setMetadata(data: any): void {
  }

  setModel(model: any): void {
  }
  constructor() {
  }

  ngOnInit() {
  }
}
