import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {SelectItem} from './select-item';

@Component({
  selector: 'sqc-select',
  template: `

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
