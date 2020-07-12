import {Component, OnInit} from '@angular/core';
import {MemberConfigService} from '../service/member-config.service';

@Component({
  selector: 'sq-member-settings-remote-users',
  template: `
    <ng-container *ngIf="integrationsSummary">
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

  constructor(private memberService: MemberConfigService) {
  }

  ngOnInit(): void {
    this.loadIntegrationsSummary();
  }

  loadIntegrationsSummary(): void {
    this.memberService.integrationsSummary().subscribe(data => {
      this.integrationsSummary = data;
    });
  }

}
