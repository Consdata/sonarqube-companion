import {Component, Input} from '@angular/core';
import {NoImprovementWebhookAction} from '../../../model/webhook-definition';

@Component({
  selector: 'sq-no-improvement-action',
  template: `
    <div class="element">
      <label class="sq-setting-label">Period</label>
      <select [(ngModel)]="_model.period">
        <option value="DAILY">DAILY</option>
        <option value="WEEKLY">WEEKLY</option>
        <option value="MONTHLY">MONTHLY</option>
      </select>
    </div>
    <div class="element">
      <label class="sq-setting-label">Severity</label>
      <label><input type="checkbox" value="blockers" (change)="onChange($event)"
                    [checked]="_model.severity?.includes('blockers')"> Blockers</label>
      <label><input type="checkbox" value="critical" (change)="onChange($event)"
                    [checked]="_model.severity?.includes('critical')">
        Criticals</label>
      <label><input type="checkbox" value="majors" (change)="onChange($event)"
                    [checked]="_model.severity?.includes('majors')"> Majors</label>
      <label><input type="checkbox" value="minors" (change)="onChange($event)"
                    [checked]="_model.severity?.includes('minors')"> Minors</label>
      <label><input type="checkbox" value="info" (change)="onChange($event)"
                    [checked]="_model.severity?.includes('info')"> Info</label>
    </div>
  `
})
export class NoImprovementActionComponent {
  _model: NoImprovementWebhookAction;
  @Input()
  set model(data: NoImprovementWebhookAction) {
    console.log(data);
    this._model = data;
    if (!this._model.severity) {
      this._model.severity = [];
    }
  }

  onChange(event: any): void {
    if (event.target.checked) {
      this._model.severity.push(event.target.value);
    } else {
      this._model.severity = this._model.severity.filter(item => item !== event.target.value);
    }
  }
}
