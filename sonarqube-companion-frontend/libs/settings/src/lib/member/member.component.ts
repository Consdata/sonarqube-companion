import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'sqc-member',
  template: `
    <p>
      member works!
    </p>
  `,
  styleUrls: ['./member.component.scss']
})
export class MemberComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
