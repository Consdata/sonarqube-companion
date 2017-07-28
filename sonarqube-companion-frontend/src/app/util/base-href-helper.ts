export class BaseHrefHelper {

  static getBaseHref(): string {
    return document.getElementsByTagName('base')[0].getAttribute('href');
  }

}
