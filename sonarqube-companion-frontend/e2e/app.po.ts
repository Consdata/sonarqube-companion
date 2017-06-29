import { browser, by, element } from 'protractor';

export class SonarqubeCompanionFrontendPage {
  navigateTo() {
    return browser.get('/');
  }

  getParagraphText() {
    return element(by.css('sqc-root h1')).getText();
  }
}
