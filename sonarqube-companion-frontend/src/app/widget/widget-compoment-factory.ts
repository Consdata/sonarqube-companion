import {Injectable, Type} from "@angular/core";
import {RankingWidgetComponent} from "./ranking/ranking-widget-component";

@Injectable()
export class WidgetComponentFactory {
  getWidgetClass(type: string): Type<any> {
    if (type == "ranking") {
      return RankingWidgetComponent;
    } else {
      return null;
    }
  }
}