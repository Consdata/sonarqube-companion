import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {SelectItem} from './select-item';

@Component({
  selector: 'sqc-select',
  template: `
    <div class="select">
      <button mat-stroked-button>
        <span *ngIf="text">{{text}}</span>
        <mat-icon>expand_more</mat-icon>
      </button>
    </div>
  `,
  styleUrls: ['./select.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SelectComponent {
  @Input()
  items!: SelectItem[];
  selected: SelectItem[] = [];
  @Input()
  text?: string;
}
