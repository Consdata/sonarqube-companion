import {RankingModel} from "./ranking/ranking-model";
import {Injectable} from "@angular/core";

@Injectable()
export class WidgetModelFactory {
  public getWidgetModel(data: any) {
    if (data.type == "ranking") {
      return new RankingModel(data);
    }
  }
}
