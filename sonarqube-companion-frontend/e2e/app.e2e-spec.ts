import { SonarqubeCompanionFrontendPage } from './app.po';

describe('sonarqube-companion-frontend App', () => {
  let page: SonarqubeCompanionFrontendPage;

  beforeEach(() => {
    page = new SonarqubeCompanionFrontendPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to sqc!!');
  });
});
