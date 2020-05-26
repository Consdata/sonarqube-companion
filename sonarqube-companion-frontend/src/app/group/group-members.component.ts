import {Component, Input} from '@angular/core';
import {GroupDetails} from './group-details';
import {GroupService} from './group-service';
import {Member} from '../config/model/member';

@Component({
  selector: 'sq-group-members',
  template: `
    <div class="members">
      <div *ngFor="let member of members" class="member">
        <span>{{ member.firstName }}</span>
        <span>{{ member.lastName }}</span>
      </div>

    </div>
  `
})

export class GroupMembersComponent {

  members: Member[];

  @Input()
  group: GroupDetails;

  constructor(private groupService: GroupService) {
  }

  @Input()
  set zoom(_zoom: { fromDate: string, toDate: string }) {
    if (this.group && _zoom) {
      this.groupService.getGroupMembersBetween(this.group.uuid, _zoom.fromDate, _zoom.toDate).subscribe(data => this.members = data);
    } else {
      this.groupService.getGroupMembers(this.group.uuid).subscribe(data => this.members = data);
    }
  }

}
