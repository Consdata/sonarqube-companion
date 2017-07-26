import {Component, OnDestroy, OnInit} from '@angular/core';

import {BaseComponent} from '../base-component';

@Component({
  selector: 'sq-spinner',
  template: `
    <div *ngIf="show" class="sk-rotating-plane"></div>
  `,
  styles: [
    BaseComponent.DISPLAY_BLOCK
  ]
})
export class SpinnerComponent implements OnInit, OnDestroy {

  static readonly DELAY = 100;
  show = false;
  private delayedTimeout;

  ngOnInit(): void {
    this.delayedTimeout = setTimeout(
      () => {
        this.delayedTimeout = null;
        this.show = true;
      },
      SpinnerComponent.DELAY
    );
  }

  ngOnDestroy(): void {
    if (this.delayedTimeout) {
      clearTimeout(this.delayedTimeout);
    }
  }

}
