import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[hover-class]',
})
export class HoverClassDirective {
  @Input('hover-class') hoverClass: any;

  constructor(public elementRef: ElementRef) {}

  @HostListener('mouseenter') onMouseEnter() {
    this.elementRef.nativeElement.classList.add(this.hoverClass);
  }

  @HostListener('mouseleave') onMouseLeave() {
    this.elementRef.nativeElement.classList.remove(this.hoverClass);
  }
}
