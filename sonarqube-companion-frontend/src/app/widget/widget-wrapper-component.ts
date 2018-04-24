import {
  AfterViewInit,
  Component,
  ComponentFactoryResolver,
  ComponentRef,
  Input,
  OnDestroy,
  OnInit,
  Type,
  ViewChild,
  ViewContainerRef
} from "@angular/core";
import {WidgetModel} from "./ranking/ranking-model";
import {WidgetFactory} from "./widget-factory";
import {Widget} from "./widget-service";

@Component({
  selector: "widget-wrapper",
  template: `
    <div #widget></div>
  `
})
export class WidgetWrapperComponent implements AfterViewInit, OnDestroy, OnInit {

  @ViewChild('widget', {read: ViewContainerRef})
  private widgetWrapperRef: ViewContainerRef;

  @Input()
  public model: WidgetModel;

  private widgetFactory;

  private widgetRef: ComponentRef<Widget<WidgetModel>>;

  private widgetClass: Type<any>;

  constructor(private componentFactoryResolver: ComponentFactoryResolver, private widgetClassFactory: WidgetFactory) {
  }

  ngOnInit(): void {
    this.widgetClass = this.widgetClassFactory.getWidgetClass(this.model.type);
  }

  ngAfterViewInit(): void {
    if (this.widgetClass) {
      this.widgetFactory = this.componentFactoryResolver.resolveComponentFactory(this.widgetClass);
      this.widgetRef = this.widgetWrapperRef.createComponent(this.widgetFactory);
      this.updateWidgetModel();
    }
  }

  ngOnDestroy(): void {
    if (this.widgetRef) {
      this.widgetRef.destroy();
    }
  }

  private updateWidgetModel() {
    this.widgetRef.instance.setModel(this.model);
  }
}
