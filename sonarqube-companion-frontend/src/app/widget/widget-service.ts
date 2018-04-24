import {Injectable} from '@angular/core';
import {WidgetModel} from "./ranking/ranking-model";
import {Observable} from "rxjs/Observable";
import {GroupService} from "../group/group-service";
import {WidgetModelFactory} from "./widget-model-factory-service";

export class Widget<M extends WidgetModel> {
  model: M;

  public setModel(model: M) {
    this.model = model;
  }
}

@Injectable()
export class WidgetService {

  constructor(private groupService: GroupService, private widgetModelFactory: WidgetModelFactory) {

  }

  private getWidgetModels(data: any[]): WidgetModel[] {
    return data.map(model => this.widgetModelFactory.getWidgetModel(model));
  }

  public getWidgetModelsForGroup(uuid: string): Observable<any[]> {
    return this.groupService.getGroupWidgets(uuid).map(models => this.getWidgetModels(models));
  }

}
