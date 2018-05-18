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
import {WidgetModel} from "./widget-model";
import {WidgetComponentFactory} from "./widget-compoment-factory";
import {Widget} from "./widget-service";

@Component({
  selector: "widget-wrapper",
  template: `
    <div #widget></div>
  `
})
export class WidgetWrapperComponent implements AfterViewInit, OnDestroy, OnInit {

  @Input()
  public model: WidgetModel;
  @ViewChild('widget', {read: ViewContainerRef})
  private widgetWrapperRef: ViewContainerRef;
  private widgetFactory;

  private widgetRef: ComponentRef<Widget<WidgetModel>>;

  private widgetClass: Type<any>;

  constructor(private componentFactoryResolver: ComponentFactoryResolver, private widgetClassFactory: WidgetComponentFactory) {
  }

  ngOnInit(): void {
    this.widgetClass = this.widgetClassFactory.getWidgetClass(this.model.type);
  }

  ngAfterViewInit(): void {
    if (this.widgetClass) {
      this.widgetFactory = this.componentFactoryResolver.resolveComponentFactory(this.widgetClass);
      this.widgetRef = this.widgetWrapperRef.createComponent(this.widgetFactory);
      this.updateWidgetModel();
      this.widgetRef.changeDetectorRef.detectChanges();
    }
  }

  ngOnDestroy(): void {
    if (this.widgetRef) {
      this.widgetRef.changeDetectorRef.detach();
      this.widgetRef.destroy();
    }
  }

  onEvent(event: any) {
    this.widgetRef.instance.onEvent(event);
  }

  private updateWidgetModel() {
    this.widgetRef.instance.setModel(this.model);
  }
}
