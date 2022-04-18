import {
  AfterViewInit,
  Component,
  ComponentFactoryResolver,
  ComponentRef,
  Input,
  OnDestroy,
  ViewChild,
  ViewContainerRef
} from '@angular/core';
import {Cell} from '../table-model';
import {TableCell} from './table-cell';
import {TableCellFactoryService} from './table-cell-factory.service';

@Component({
  selector: 'sqc-table-cell',
  template: `
    <ng-container #container></ng-container>
  `
})

export class TableCellComponent implements OnDestroy, AfterViewInit {

  @ViewChild('container', {read: ViewContainerRef})
  container?: ViewContainerRef;
  componentRef?: ComponentRef<TableCell>;

  constructor(private resolver: ComponentFactoryResolver, private cellFactory: TableCellFactoryService) {
  }

  _cell?: Cell;

  @Input()
  set cell(_cell: Cell) {
    this._cell = _cell;
    this.createCell(_cell);
  }


  ngAfterViewInit(): void {
    this.createCell(this._cell);
  }

  createCell(cell: Cell | undefined): void {
    if (this.container && cell) {
      this.container.clear();
      const type = this.cellFactory.createCell(cell);
      this.componentRef = this.container.createComponent(this.resolver.resolveComponentFactory(type));
      this.componentRef.instance.setCell(cell);
      this.componentRef.changeDetectorRef.detectChanges();
    }
  }


  ngOnDestroy(): void {
    if (this.componentRef) {
      this.componentRef.destroy();
    }
  }
}

