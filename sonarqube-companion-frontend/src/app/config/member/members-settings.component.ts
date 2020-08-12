import {Component, OnInit, Type} from '@angular/core';
import {SettingsListDetailsItem} from '../common/settings-list/settings-list-item';
import {BehaviorSubject, Subject} from 'rxjs';
import {ValidationResult} from '../common/settings-list/settings-list-component';
import {MemberComponent} from './member-component';
import {Member} from '../model/member';
import {MemberConfigService} from '../service/member-config.service';

@Component({
  selector: 'sq-settings-members',
  template: `
    <div class="sq-settings-container">
      <sq-spinner *ngIf="!(loaded | async)"></sq-spinner>
      <sq-settings-list
        [loaded]="loaded | async"
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
    <div class="sq-settings-container">
      <sq-member-settings-remote-users></sq-member-settings-remote-users>
    </div>
  `
})

export class MembersSettingsComponent implements OnInit {
  loaded: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  members: Member[] = [];
  memberType: Type<SettingsListDetailsItem> = MemberComponent;
  newItem: Subject<Member> = new Subject();
  validation: Subject<ValidationResult> = new Subject();

  constructor(private memberService: MemberConfigService) {
  }

  addMember(): void {
    const newMember: Member = new Member({
      firstName: '',
      lastName: '',
      aliases: [],
      groups: [],
      mail: '',
      uuid: ''
    });
    this.members.push(newMember);
    this.newItem.next(newMember);
  }

  removeMember(member: Member): void {
    this.loaded.next(false);
    this.memberService.delete(member).subscribe(validationResult => {
      this.onValidationResult(validationResult, {item: member, newItem: false});
    }, er => {
      this.onError('Unable to delete item');
    });
  }

  saveMember(member: { item: Member, newItem: boolean }): void {
    this.loaded.next(false);
    this.memberService.save(member.item, member.newItem).subscribe(validationResult => {
      this.onValidationResult(validationResult, member);
    }, er => {
      this.onError('Unable to save item');
    });
  }


  ngOnInit(): void {
    this.load();
  }

  onError(msg: string): void {
    this.validation.next({valid: false, message: msg});
    this.loaded.next(true);
  }

  onValidationResult(validationResult: ValidationResult, member: { item: Member, newItem: boolean }): void {
    if (validationResult.valid) {
      this.load();
    } else {
      this.loaded.next(true);
    }
    validationResult.item = member.item;
    this.validation.next(validationResult);
  }

  load(): void {
    this.loaded.next(false);
    this.memberService.all().subscribe(data => {
      this.members = data;
      this.loaded.next(true);
    }, er => {
      this.onError('Unable to load items');
    });
  }

  getLabel(item: Member): string {
    return `${item.firstName || ''} ${item.lastName || ''}`;
  }
}
