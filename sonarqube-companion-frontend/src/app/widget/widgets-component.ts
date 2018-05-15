import {AfterViewInit, Component, Input, QueryList, ViewChildren} from '@angular/core';
import {WidgetService} from "./widget-service";
import {WidgetModel} from "./widget-model";
import {WidgetWrapperComponent} from "./widget-wrapper-component";


@Component({
  selector: "widgets",
  template: `
    <div id="widgets">
      <div *ngFor="let model of models" class="widget-wrapper">
        <widget-wrapper [model]="model" #widget></widget-wrapper>
      </div>
    </div>`

})
export class WidgetsComponent implements AfterViewInit {

  @Input()
  uuid: string;
  models: WidgetModel[];
  @ViewChildren('widget')
  private widgets: QueryList<WidgetWrapperComponent>;

  constructor(private widgetService: WidgetService) {
  }

  ngAfterViewInit(): void {
    this.widgetService.getWidgetModelsForGroup(this.uuid).subscribe(models => {
      this.models = models;
    });
  }

  public onEvent(event: any) {
    this.widgets.forEach(widget => widget.onEvent(event));
  }
}
