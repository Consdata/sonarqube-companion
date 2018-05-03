import {AfterViewInit, Component, Input, QueryList, ViewChildren} from '@angular/core';
import {WidgetService} from "./widget-service";
import {WidgetModel} from "./widget-model";
import {WidgetWrapperComponent} from "./widget-wrapper-component";


@Component({
  selector: "widgets",
  template: `
    <div id="widgets">
      <div *ngFor="let model of models" class="widget-wrapper">
        <widget-wrapper [uuid]="uuid" [model]="model" #widget></widget-wrapper>
      </div>
    </div>`

})
export class WidgetsComponent implements AfterViewInit {

  @Input()
  private uuid: string;

  @ViewChildren('widget')
  private widgets: QueryList<WidgetWrapperComponent>;
  models: WidgetModel[];

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
