import {
  AfterViewInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  ContentChildren,
  EventEmitter,
  Input,
  OnDestroy,
  Output,
  QueryList
} from '@angular/core';
import {SelectItemComponent} from '../select-item/select-item.component';
import {Subscription} from 'rxjs';

@Component({
  selector: 'sqc-select',
  template: `
    <div class="select" [class.menu]="menu">
      <span class="label">{{ text }}</span>
      <button class="select-button" (click)="click($event)" type="button" cdkOverlayOrigin #trigger="cdkOverlayOrigin">
        <span *ngIf="selected && !menu" [innerHTML]="selected.content.nativeElement.innerHTML"></span>
        <mat-icon>{{ icon ? icon : 'expand_more'}}</mat-icon>
      </button>
    </div>
    <ng-template
      cdkConnectedOverlay
      [cdkConnectedOverlayOrigin]="trigger"
      [cdkConnectedOverlayOpen]="open"
      [cdkConnectedOverlayHasBackdrop]="true"
      [cdkConnectedOverlayBackdropClass]="'backdrop'"
      (backdropClick)="open = false"
    >
      <div class="items mat-elevation-z3">
        <ng-content #items></ng-content>
      </div>
    </ng-template>
  `,
  styleUrls: ['./select.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SelectComponent implements AfterViewInit, OnDestroy {
  @Input()
  text?: string;
  @Input()
  icon?: string;
  @Input()
  default: string = '';
  @Input()
  menu: boolean = false;
  selected!: SelectItemComponent;
  open: boolean = false;
  @ContentChildren(SelectItemComponent)
  items!: QueryList<SelectItemComponent>;
  @Output()
  onSelect: EventEmitter<string> = new EventEmitter<string>();

  subscriptions: Subscription[] = [];

  constructor(private changeDetector: ChangeDetectorRef) {
  }

  ngAfterViewInit(): void {
    this.items.forEach(item =>
      this.subscriptions.push(item.click.subscribe(() => this.select(item)))
    )

    this.items.forEach(item => {
      if (item.id === this.default) this.selected = item
    })
    this.changeDetector.detectChanges();
  }

  isSelected(id: string): boolean {
    return this.selected && this.selected.id === id;
  }

  select(item: SelectItemComponent) {
    if (!this.menu) {
      this.selected = item;
      this.onSelect.emit(item.id);
    }
    this.open = false;
    this.changeDetector.detectChanges();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  click(event: MouseEvent) {
    event.preventDefault();
    this.open = !this.open
  }
}
