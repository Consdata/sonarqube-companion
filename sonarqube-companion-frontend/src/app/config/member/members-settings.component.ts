import {Component, OnInit, Type} from '@angular/core';
import {SettingsListDetailsItem} from '../common/settings-list/settings-list-item';
import {Subject} from 'rxjs';
import {ValidationResult} from '../common/settings-list/settings-list-component';
import {MemberComponent} from './member-component';

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
        (addClick)="addServer()"
        (removeItem)="removeMember($event)"
        (saveItem)="saveMember($event)"
      ></sq-settings-list>
    </div>
  `
})

export class MembersSettingsComponent implements OnInit {
  loaded: boolean = true;
  members: any[] = [];
  memberType: Type<SettingsListDetailsItem> = MemberComponent;
  newItem: Subject<any> = new Subject();
  validation: Subject<ValidationResult> = new Subject();

  constructor() {
  }

  ngOnInit() {
  }


  addServer() {
  }

  removeMember(member: any) {

  }

  saveMember(member: { item: any, newItem: boolean }) {

  }

  load() {
  }

  getLabel(item: any): string {
    return item.id;
  }
}
