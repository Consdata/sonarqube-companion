import {Component, OnInit} from '@angular/core';
import {GroupDetails} from '../group/group-details';
import {GroupViolationsHistoryDiff} from '../violations/group-violations-history-diff';
import {ProjectSummary} from './project-summary';

@Component({
  selector: 'projects-summary',
  template: `
    <div>
      <h2>Projects</h2>
      <div class="group-projects-filter">
        <a class="project-filter-item" [class.active]="'changed' === projectsFilter" [routerLink]
           [queryParams]="{'projects.filter.severity': 'changed'}" queryParamsHandling="merge">changed</a>
        | <a class="project-filter-item" [class.active]="'regression' === projectsFilter" [routerLink]
             [queryParams]="{'projects.filter.severity': 'regression'}" queryParamsHandling="merge">regression</a>
        | <a class="project-filter-item" [class.active]="'improvement' === projectsFilter" [routerLink]
             [queryParams]="{'projects.filter.severity': 'improvement'}" queryParamsHandling="merge">improvement</a>
        | <a class="project-filter-item" [class.active]="'all' === projectsFilter" [routerLink]
             [queryParams]="{'projects.filter.severity': 'all'}" queryParamsHandling="merge">all</a>
      </div>
      <hr/>
      <sq-group-projects
        [projects]="projects"
        [filter]="projectsFilter"
        [violationsHistoryDiff]="violationsHistoryDiff"
        [uuid]="''">
      </sq-group-projects>
    </div>

  `
})

export class ProjectsComponent {

  projectsFilter: string = 'changed';
  projects: ProjectSummary[];
  violationsHistoryDiff: GroupViolationsHistoryDiff;

  constructor() {
  }
}
