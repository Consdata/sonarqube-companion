export interface Row {
  cells: { [key: string]: Cell };
  action?: Action;
}

export interface Cell {
  column: string;
  value: string;
  type?: Type;
  removable?: boolean;
  action?: Action;
}

export interface Type {
  name: string;
  domain?: { [key: string]: string };
}

export interface Action {
  type: string;
  config: { [key: string]: string };
}

export interface Column {
  name: string;
  id: string;
}

export interface Table {
  readonly?: boolean;
  rows: Row[];
  columns: { [key: string]: Column };
}
