import {Component, OnInit, Pipe, PipeTransform} from '@angular/core';
import {SchedulerConfig, TimeUnit} from '../model/scheduler-config';
import {SchedulerSettingsService} from '../service/scheduler-settings-service';


@Pipe({name: 'enumToSelect'})
export class EnumToSelectPipe implements PipeTransform {
  transform(value, args: string[]): any {
    let keys = [];
    for (let enumMember in value) {
      if (!isNaN(parseInt(enumMember, 10))) {
        keys.push({key: enumMember, value: value[enumMember]});
      }
    }
    return keys;
  }
}

@Component({
  selector: `sq-scheduler`,
  template: `
    <sq-spinner *ngIf="!loaded"></sq-spinner>
    <div class="sq-settings-group-sub-title">
      <div>Scheduler</div>
      <hr>
    </div>
    <div *ngIf="loaded">
      <div class="edit sq-settings-group-sub-container">
        <div class="sq-setting-label">Interval between synchronizations</div>
        <div class="sq-setting-container">
          <div class="interval">
            <label for="intervalInput">Interval:</label>
            <input (blur)="onSave()"
                   type="number"
                   id="intervalInput"
                   [(ngModel)]="config.interval"/>
          </div>
          <div class="timeUnit">
            <label for="timeUnitSelect">Time unit:</label>
            <select (change)="onTimeUnitSelect($event)" id="timeUnitSelect">
              <option *ngFor="let item of timeUnits | enumToSelect" [value]="item.key"
                      [selected]="timeUnits[item.key] === config.timeUnit"
              >{{item.value}}
              </option>
            </select>
          </div>
          <div class="sq-setting-error left" *ngIf="errorMessage">{{errorMessage}}</div>
        </div>
      </div>
    </div>`
})
export class SchedulerComponent implements OnInit {

  loaded: boolean = false;
  private config: SchedulerConfig = new SchedulerConfig();
  private timeUnits = TimeUnit;
  private errorMessage: string;

  constructor(private schedulerService: SchedulerSettingsService) {
  }

  ngOnInit(): void {
    this.load();
  }

  onSave() {
    this.loaded = false;
    this.schedulerService.update(this.config).subscribe(validationResult => {
        if (validationResult.valid) {
          this.errorMessage = '';
          this.load();
        } else {
          this.errorMessage = validationResult.message;
        }
        this.loaded = true;
      }, er => {
        this.loaded = true;
        this.errorMessage = 'Cannot update scheduler configuration';
      }
    );
  }

  onTimeUnitSelect(event: any) {
    this.config.timeUnit = Number(event.target.value);
    this.onSave();
  }


  load() {
    this.loaded = false;
    this.schedulerService.get().subscribe(config => {
      this.errorMessage = '';
      this.config = config;
      this.loaded = true;
    }, er => {
      this.loaded = true;
      this.errorMessage = 'Cannot load scheduler configuration';
    });
  }
}
