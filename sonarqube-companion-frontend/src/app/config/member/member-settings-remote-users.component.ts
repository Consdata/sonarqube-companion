import {Component, OnInit} from '@angular/core';
import {MemberConfigService} from '../service/member-config.service';
import {BehaviorSubject} from 'rxjs';

@Component({
  selector: 'sq-member-settings-remote-users',
  template: `
    <sq-spinner *ngIf="!(loaded | async)"></sq-spinner>
    <ng-container *ngIf="loaded | async">
      <div class="header">
        <div class="sq-settings-group-title">Remote members</div>
        <hr>
      </div>
      <div *ngFor="let integration of integrationsSummary | keyvalue">
        {{integration.key}}: {{integration.value}}
      </div>
    </ng-container>
  `
})

export class MemberSettingsRemoteUsersComponent implements OnInit {
  integrationsSummary: { [key: string]: string };
  loaded: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(private memberService: MemberConfigService) {
  }

  ngOnInit(): void {
    this.loadIntegrationsSummary();
  }

  loadIntegrationsSummary(): void {
    this.loaded.next(true);
    this.memberService.integrationsSummary().subscribe(data => {
      this.integrationsSummary = data;
      this.loaded.next(true);
    });
  }

}
