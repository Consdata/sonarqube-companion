import {Component, OnInit} from '@angular/core';
import {SettingsListDetailsItem} from '../common/settings-list/settings-list-item';
import {Member} from '../model/member';

@Component({
  selector: 'sq-member-component',
  template: `
    <div class="sq-settings-detail">
      <div class="element">
        <label class="sq-setting-label">First name</label>
        <input type="text" [(ngModel)]="member.firstName"/>
      </div>
      <div class="element">
        <label class="sq-setting-label">Last name</label>
        <input type="text" [(ngModel)]="member.lastName"/>
      </div>
      <div class="element">
        <label class="sq-setting-label">Mail</label>
        <input type="text" [(ngModel)]="member.mail"/>
      </div>
      <div class="element">
        <label class="sq-setting-label">Member of</label>
        <sq-settings-group-badge></sq-settings-group-badge>
      </div>
    </div>
  `
})

export class MemberComponent implements OnInit, SettingsListDetailsItem {
  member: Member;

  constructor() {
  }

  ngOnInit() {
  }

  getModel(): any {
    return this.member;
  }

  getTitle(): string {
    return `${this.member.firstName} ${this.member.lastName}`;
  }

  setMetadata(data: any): void {
  }

  setModel(model: any): void {
    this.member = <Member>model;
  }

}
