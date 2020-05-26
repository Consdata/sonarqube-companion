import {Component, OnInit, Type} from '@angular/core';
import {SettingsListDetailsItem} from '../common/settings-list/settings-list-item';
import {Subject} from 'rxjs';
import {ValidationResult} from '../common/settings-list/settings-list-component';
import {MemberComponent} from './member-component';
import {Member} from '../model/member';
import {MemberConfigService} from '../service/member-config.service';

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
  newItem: Subject<Member> = new Subject();
  validation: Subject<ValidationResult> = new Subject();

  constructor(private memberService: MemberConfigService) {
  }

  addMember(): void {
    const newMember: Member = new Member({});
    this.members.push(newMember);
    this.newItem.next(newMember);
  }

  removeMember(member: Member): void {
    this.loaded = false;
    this.memberService.delete(member).subscribe(validationResult => {
      if (validationResult.valid) {
        this.load();
      } else {
        this.loaded = true;
      }
      validationResult.item = member;
      this.validation.next(validationResult);
    }, er => {
      this.loaded = true;
      this.validation.next({valid: false, message: 'Unable to delete item'});
    });
  }

  saveMember(member: { item: Member, newItem: boolean }): void {
    this.loaded = false;
    this.memberService.save(member.item, member.newItem).subscribe(validationResult => {
      if (validationResult.valid) {
        this.load();
      } else {
        this.loaded = true;
      }
      validationResult.item = member.item;
      this.validation.next(validationResult);
    }, er => {
      this.validation.next({valid: false, message: 'Unable to save item'});
      this.loaded = true;
    });
  }


  ngOnInit(): void {
    this.load();
  }

  load(): void {
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
    return `${item.firstName || ''} ${item.lastName || ''}`;
  }
}
