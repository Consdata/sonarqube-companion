import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {Table} from './table-model';

@Injectable({providedIn: 'root'})
export class DevService {


  public get(): Observable<Table> {
    return of({
      columns: {
        'project': {
          name: 'Project',
          id: 'project'
        },
        'server': {
          name: 'server',
          id: 'server'
        },
        'regexp': {
          name: 'regexp',
          id: 'regexp'
        },
        'exclude': {
          name: 'exclude',
          id: 'exclude'
        },
      },
      rows: [{
        cells: {
          'project': {
            column: 'project',
            value: 'elo',
            type: {
              name: 'input'
            }
          },
          'server': {
            column: 'server',
            value: 'elo'
          },
          'regexp': {
            column: 'regexp',
            value: 'elo'
          },
          'exclude': {
            column: 'exclude',
            value: 'elo'
          }
        }
      }]
    });
  }

}
