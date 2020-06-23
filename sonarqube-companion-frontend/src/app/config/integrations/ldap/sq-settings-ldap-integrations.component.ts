import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {LdapIntegrationService} from './ldap-integration-service';
import {LdapConfig} from './ldap-config';
import {GroupLightModel} from '../../model/group-light-model';
import {Subscription} from 'rxjs';

@Component({
  selector: 'sq-settings-ldap-integrations',
  template: `
    <sq-spinner *ngIf="!loaded"></sq-spinner>
    <div class="sq-settings-group-sub-container" *ngIf="loaded">
      <div class="header">
        <div class="sq-setting-label">
          <div class="item">
            <label>LDAP</label>
            <input type="checkbox" [(ngModel)]="ldapConfig.enabled">
            <div class="features">
              <span>members</span>
              <span>membership</span>
            </div>
          </div>
        </div>

        <button *ngIf="ldapConfig.enabled" (click)="save()">Save</button>
      </div>
      <hr/>
      <ng-container>
        <div class="sq-setting-container" *ngIf="ldapConfig.enabled">
          <div class="sq-setting-error">{{errorMsg}}</div>
          <div>
            <div class="section">
              <label class="section">Connection</label>
              <div>
                <div class="item">
                  <sq-settings-badge [(items)]="ldapConfig.connection.urls"
                                     [title]="'Urls'"></sq-settings-badge>
                </div>
                <div class="item">
                  <label>Base</label>
                  <input type="text" [(ngModel)]="ldapConfig.connection.base">
                </div>
                <div class="section">
                  <label>Credentials</label>
                  <div class="horizontal">
                    <div class="item">
                      <label>Anonymous</label>
                      <input type="checkbox" [(ngModel)]="ldapConfig.connection.anonymous">
                    </div>
                    <div *ngIf="!ldapConfig.connection.anonymous" class="item">
                      <label>UserDn</label>
                      <input type="text" [(ngModel)]="ldapConfig.connection.userDn">
                    </div>
                    <div *ngIf="!ldapConfig.connection.anonymous" class="item">
                      <label>Password</label>
                      <input type="password" [(ngModel)]="ldapConfig.connection.password">
                    </div>
                  </div>
                </div>

              </div>
            </div>

            <div class="section">
              <label>Members</label>
              <div>
                <div class="item">
                  <label>Member object class</label>
                  <input type="text" [(ngModel)]="ldapConfig.members.memberObjectClass">
                </div>
                <div class="item">
                  <label>Id attribute</label>
                  <input type="text" [(ngModel)]="ldapConfig.members.idAttribute">
                </div>
                <div class="item">
                  <label>First name attribute</label>
                  <input type="text" [(ngModel)]="ldapConfig.members.firstNameAttribute">
                </div>
                <div class="item">
                  <label>Last name attribute</label>
                  <input type="text" [(ngModel)]="ldapConfig.members.lastNameAttribute">
                </div>
                <div class="item">
                  <label>Mail attribute</label>
                  <input type="text" [(ngModel)]="ldapConfig.members.mailAttribute">
                </div>
                <div>
                  <sq-settings-badge [(items)]="ldapConfig.members.aliasesAttributes"
                                     [title]="'Aliases attributes'"></sq-settings-badge>
                </div>
              </div>
            </div>


            <div class="section">
              <label>Groups</label>
              <div>
                <div class="item">
                  <label>Group object class</label>
                  <input type="text" [(ngModel)]="ldapConfig.groups.groupObjectClass">
                </div>
                <div class="item">
                  <label>Group name attribute</label>
                  <input type="text" [(ngModel)]="ldapConfig.groups.groupNameAttribute">
                </div>
                <div class="item">
                  <label>Membership attribute</label>
                  <input type="text" [(ngModel)]="ldapConfig.groups.membershipAttribute">
                </div>
                <div class="groups">
                  <sq-settings-map
                    [title]="'Group mappings'"
                    [loaded]="loaded"
                    [(data)]="ldapConfig.groups.groupMappings"
                  ></sq-settings-map>
                </div>
              </div>
            </div>
          </div>
        </div>
      </ng-container>
    </div>
  `
})

export class SettingsLdapIntegrationsComponent implements OnInit {

  ldapConfig: LdapConfig;
  loaded: boolean = false;
  groups: GroupLightModel[] = [];
  errorMsg: string = '';

  constructor(private ldapIntegrationService: LdapIntegrationService) {
  }

  ngOnInit(): void {
    this.load();
  }

  save(): void {
    this.ldapIntegrationService.updateLdapConfig(this.ldapConfig).subscribe(data => {
      if (data.valid) {
        this.load();
        this.errorMsg = '';
      } else {
        this.errorMsg = data.message;
      }
    });
  }

  private load(): void {
    this.loaded = false;
    this.ldapIntegrationService.getLdapConfig().subscribe(data => {
      this.ldapConfig = data;
      this.loaded = true;
    });
  }

}
