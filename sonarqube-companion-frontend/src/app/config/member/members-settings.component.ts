import {Component, OnInit, Type} from '@angular/core';
import {SettingsListDetailsItem} from '../common/settings-list/settings-list-item';
import {Subject} from 'rxjs';
import {ValidationResult} from '../common/settings-list/settings-list-component';
import {MemberComponent} from './member-component';
import {Member} from '../model/member';
import {MemberService} from '../service/member.service';

@Component({
  selector: 'sq-settings-members',
  template: `
    <div class="sq-settings-container">
      <sq-spinner *ngIf="!loaded"></sq-spinner>
      <sq-settings-list
        [loaded]="loaded"
        [details]="memberType"
        [(data)]="members"
        [title]="'Members'"
        [foldedListLabel]="'%number% more members defined'"
        [newItem]="newItem.asObservable()"
        [validation]="validation.asObservable()"
        [label]="getLabel"
        (addClick)="addMember()"
        (removeItem)="removeMember($event)"
        (saveItem)="saveMember($event)"
      ></sq-settings-list>
    </div>
  `
})

export class MembersSettingsComponent implements OnInit {
  loaded: boolean = true;
  members: Member[] = [];
  memberType: Type<SettingsListDetailsItem> = MemberComponent;
  newItem: Subject<any> = new Subject();
  validation: Subject<ValidationResult> = new Subject();

  constructor(private memberService: MemberService) {
  }

  ngOnInit() {
    this.load();
  }


  addMember() {
  }

  removeMember(member: any) {

  }

  saveMember(member: { item: any, newItem: boolean }) {

  }

  load() {
    this.loaded = false;
    this.memberService.all().subscribe(data => {
      this.members = data;
      this.loaded = true;
    }, er => {
      this.loaded = true;
      this.validation.next({valid: false, message: 'Unable to load items'});
    });
  }

  getLabel(item: Member): string {
    return `${item.firstName} ${item.lastName}`;
  }
}
