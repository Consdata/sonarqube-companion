import {Component, OnInit, Pipe, PipeTransform} from "@angular/core";
import {SchedulerConfig, TimeUnit} from "../model/scheduler-config";
import {SettingsService} from "../service/settings-service";


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
    <div *ngIf="loaded">
      <div class="edit wrapper">
        <div class="interval">
          <input (blur)="onSave()"
                 type="number"
                 [(ngModel)]="config.interval"/>
        </div>
        <div class="timeUnit">
          <select (change)="onTimeUnitSelect($event)">
            <option *ngFor="let item of timeUnits | enumToSelect" [value]="item.key"
                    [selected]="timeUnits[item.key] === config.timeUnit"
            >{{item.value}}
            </option>
          </select>
        </div>
      </div>
    </div>`
})
export class SchedulerComponent implements OnInit {

  private config: SchedulerConfig = new SchedulerConfig();
  private timeUnits = TimeUnit;
  private loaded: boolean = false;

  constructor(private settingsService: SettingsService) {
  }

  ngOnInit(): void {
    this.load();
  }

  onSave() {
    this.settingsService.saveScheduler(this.config).subscribe(response => {
      this.load();
    });
  }

  onTimeUnitSelect(event: any) {
    this.config.timeUnit = Number(event.target.value);
    this.onSave();
  }

  load() {
    this.settingsService.getScheduler().subscribe(config => {
      this.config = config;
      this.loaded = true;
    });
  }
}
