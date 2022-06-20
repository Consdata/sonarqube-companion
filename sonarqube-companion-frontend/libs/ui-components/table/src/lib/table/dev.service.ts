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
        'eee': {
          name: 'eee',
          id: 'eee'
        },
        'actions': {
          name: 'actions',
          id: 'actions'
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
            value: 'true',
            type: {
              name: 'checkbox'
            }
          },
          'regexp': {
            column: 'regexp',
            value: 'elo',
            type: {
              name: 'combo',
              domain: {
                '1': 'Test1',
                '2': 'Test2'
              }
            }
          },
          'exclude': {
            column: 'exclude',
            value: '',
            type: {
              name: 'date'
            }
          },
          'eee': {
            column: 'exclude',
            value: 'https://www.google.pl',
            type: {
              name: 'link'
            }
          },
          'actions': {
            column: 'actions',
            value: '',
            type: {
              name: 'actions'
            }
          }
        }
      }]
    });
  }

}
