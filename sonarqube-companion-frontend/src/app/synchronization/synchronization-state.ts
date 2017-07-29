export interface SynchronizationState {
  id: string;
  startTimestamp: number;
  finishTimestamp: number;
  allTasks: number;
  finishedTasks: number;
  failedTasks: number;
}
