import {AfterViewInit, Component, Input} from '@angular/core';
import {WidgetService} from "./widget-service";
import {WidgetModel} from "./ranking/ranking-model";


@Component({
  selector: "widgets",
  template: `
    <div id="widgets">
      <div *ngFor="let model of models">
        <widget-wrapper [model]="model"></widget-wrapper>
      </div>
    </div>`

})
export class WidgetsComponent implements AfterViewInit {

  @Input()
  private uuid: string;

  constructor(private widgetService: WidgetService) {
  }

  private models: WidgetModel[];

  ngAfterViewInit(): void {
    this.widgetService.getWidgetModelsForGroup(this.uuid).subscribe(models => {
      this.models = models;
    });
  }
}
