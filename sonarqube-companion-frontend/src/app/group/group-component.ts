import {Component, OnInit} from '@angular/core';

import {BaseComponent} from '../base-component';
import {GroupDetails} from './group-details';
import {GroupService} from './group-service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'sq-group',
  template: `
    <sq-spinner *ngIf="!group"></sq-spinner>
    <pre>{{group|json}}</pre>
  `,
  styles: [
    BaseComponent.DISPLAY_BLOCK
  ]
})
export class GroupComponent implements OnInit {

  group: GroupDetails;

  constructor( private route: ActivatedRoute, private service: GroupService) {
  }

  ngOnInit(): void {
    const parmasSnapshot = this.route.snapshot.params;
    this.service
      .getGroup(parmasSnapshot.uuid)
      .subscribe(group => this.group = group);
  }

}
