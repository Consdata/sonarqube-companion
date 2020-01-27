import {
  Component,
  ComponentFactory,
  ComponentFactoryResolver,
  ComponentRef,
  Input,
  Type,
  ViewChild,
  ViewContainerRef
} from '@angular/core';
import {SettingsListDetailsItem} from './settings-list-item';


@Component({
  selector: `sq-settings-list-details`,
  template: `
    <div #details></div>
  `

})
export class SettingsListDetailsComponent {

  @ViewChild('details', {read: ViewContainerRef, static: false})
  container: ViewContainerRef;
  componentRef: ComponentRef<SettingsListDetailsItem>;

  constructor(private resolver: ComponentFactoryResolver) {
  }

  @Input()
  set details(type: Type<SettingsListDetailsItem>) {
    if (type) {
      this.createComponent(type);
    }
  }

  _model: any;

  @Input()
  set model(data: any) {
    this._model = data;
    if (this.componentRef) {
      this.componentRef.instance.setModel(data);
    }
  }

  _metadata: any;

  @Input()
  set metadata(data: any) {
    this._metadata = data;
    if (this.componentRef) {
      this.componentRef.instance.setMetadata(data);
    }
  }

  createComponent(type: Type<SettingsListDetailsItem>) {
    if (this.container) {
      this.container.clear();
      const factory: ComponentFactory<SettingsListDetailsItem> = this.resolver.resolveComponentFactory(type);
      this.componentRef = this.container.createComponent(factory);
      if (this._model) {
        this.componentRef.instance.setModel(this._model);
      }
    }
  }
}
