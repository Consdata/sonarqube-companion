import {AfterViewInit, Component} from '@angular/core';
import {SettingsListDetailsItem} from '../common/settings-list/settings-list-item';
import {Member} from '../model/member';
import {MemberService} from './member-service';
import {GroupLightModel} from '../model/group-light-model';

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
        <sq-settings-badge [(items)]="member.aliases"
                           [title]="'Aliases'"></sq-settings-badge>
      </div>
      <div class="element">
        <sq-settings-group-badge [title]="'Member of'" (itemsChange)="onItemsChange($event)"
                                 [items]="memberGroups"></sq-settings-group-badge>
      </div>
    </div>
  `
})

export class MemberComponent implements AfterViewInit, SettingsListDetailsItem {
  member: Member;
  memberGroups: GroupLightModel[] = [];

  constructor(private memberService: MemberService) {
  }

  ngAfterViewInit(): void {
    this.memberService.getGroups(this.member.uuid).subscribe(data => this.memberGroups = data);
  }


  getModel(): any {
    return this.member;
  }

  getTitle(): string {
    return `${this.member.firstName || ''} ${this.member.lastName || ''}`;
  }

  setMetadata(data: any): void {
  }

  setModel(model: any): void {
    this.member = <Member>model;
  }

  onItemsChange(items: GroupLightModel[]) {
    this.memberGroups = items;
    this.member.groups = items.map(i => i.uuid);
  }
}
