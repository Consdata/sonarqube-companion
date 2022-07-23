import {ChangeDetectionStrategy, Component, EventEmitter, Input, Output} from '@angular/core';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from '@angular/material/chips';

@Component({
  selector: 'sqc-chips',
  template: `
    <sqc-frame [label]="label">
      <div class="chips">
        <mat-chip-list #chipList aria-label="Fruit selection">
          <mat-chip
            *ngFor="let item of value"
            [selectable]="selectable"
            [removable]="removable"
            (removed)="remove(item)"
          >
            {{ item }}
            <mat-icon matChipRemove>cancel</mat-icon>
          </mat-chip>
          <input
            [placeholder]="placeholder"
            [matChipInputFor]="chipList"
            [matChipInputSeparatorKeyCodes]="separatorKeysCodes"
            [matChipInputAddOnBlur]="addOnBlur"
            (matChipInputTokenEnd)="add($event)"
          />
        </mat-chip-list>
      </div>
    </sqc-frame>
  `,
  styleUrls: ['./ui-chips.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UiChipsComponent {

  @Input()
  value: string [] = [];

  @Input()
  label = '';

  @Input()
  placeholder = '';

  @Output()
  valueChange = new EventEmitter<string[]>();

  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    if (value) {
      this.value.push(value);
    }

    event.chipInput.clear();
    this.valueChange.emit(this.value);
  }

  remove(item: string): void {
    const index = this.value.indexOf(item);

    if (index >= 0) {
      this.value.splice(index, 1);
    }
    this.valueChange.emit(this.value);
  }
}
