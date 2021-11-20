import {Component, Input} from '@angular/core';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from '@angular/material/chips';

@Component({
  selector: 'sqc-chips',
  template: `
    <label>{{label}}</label>
    <div class="chips">
      <mat-chip-list #chipList aria-label="Fruit selection">
        <mat-chip *ngFor="let item of items" [selectable]="selectable"
                  [removable]="removable" (removed)="remove(item)">
          {{item}}
          <mat-icon matChipRemove>cancel</mat-icon>
        </mat-chip>
        <input [placeholder]="placeholder"
               [matChipInputFor]="chipList"
               [matChipInputSeparatorKeyCodes]="separatorKeysCodes"
               [matChipInputAddOnBlur]="addOnBlur"
               (matChipInputTokenEnd)="add($event)">
      </mat-chip-list>
    </div>
  `,
  styleUrls: ['./chips.component.scss']
})
export class ChipsComponent {
  @Input()
  label: string = '';

  @Input()
  placeholder: string = '';

  @Input()
  items: string[] = [];

  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    if (value) {
      this.items.push(value);
    }

    event.chipInput!.clear();
  }

  remove(item: string): void {
    const index = this.items.indexOf(item);

    if (index >= 0) {
      this.items.splice(index, 1);
    }
  }
}
