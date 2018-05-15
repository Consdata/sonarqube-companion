import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {GroupService} from "../group/group-service";
import {WidgetModelFactory} from "./widget-model-factory-service";
import {WidgetModel} from "./widget-model";

export abstract class Widget<M extends WidgetModel> {
  public model: M;

  public setModel(model: M) {
    this.model = model;
  }


  abstract onEvent(event);
}

@Injectable()
export class WidgetService {

  constructor(private groupService: GroupService, private widgetModelFactory: WidgetModelFactory) {

  }

  public getWidgetModelsForGroup(uuid: string): Observable<any[]> {
    return this.groupService.getGroupWidgets(uuid).map(models => this.getWidgetModels(models, uuid));
  }

  private getWidgetModels(data: any[], uuid: string): WidgetModel[] {
    return data.map(model => {
      model['uuid'] = uuid;
      return this.widgetModelFactory.getWidgetModel(model);
    });
  }

}
