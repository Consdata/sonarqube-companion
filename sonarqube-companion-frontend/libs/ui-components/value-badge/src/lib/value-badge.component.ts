import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'sqc-value-badge',
  template: `
    <p>
      value-badge works!
    </p>
  `,
  styleUrls: ['./value-badge.component.scss']
})
export class ValueBadgeComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
