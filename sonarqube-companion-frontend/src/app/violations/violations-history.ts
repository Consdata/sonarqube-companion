import {ViolationsHistoryEntry} from './violations-history-entry';

export class ViolationsHistory {

  history: ViolationsHistoryEntry[];

  constructor(data) {
    this.history = data.violationHistoryEntries.map(entry => new ViolationsHistoryEntry(entry));
  }

  isEmpty(): boolean {
    return !this.history || this.history.length === 0;
  }

}
