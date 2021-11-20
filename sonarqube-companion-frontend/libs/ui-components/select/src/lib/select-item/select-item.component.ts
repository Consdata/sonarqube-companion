import {Component, ElementRef, EventEmitter, Input, Output, ViewChild} from '@angular/core';

@Component({
  selector: 'sqc-select-item',
  template: `
    <div class="wrapper" (click)="click.emit(id)">
      <div #content class="content">
        <ng-content></ng-content>
      </div>
    </div>
  `,
  styleUrls: ['./select-item.component.scss']
})
export class SelectItemComponent {

  @Input()
  id: string = '';

  @ViewChild('content')
  content!: ElementRef;

  @Output()
  click: EventEmitter<string> = new EventEmitter();

}
