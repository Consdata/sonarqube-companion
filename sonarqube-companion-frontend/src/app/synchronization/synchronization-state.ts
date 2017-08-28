export interface SynchronizationState {
  startTimestamp: number;
  finishTimestamp: number;
  allTasks: number;
  finishedTasks: number;
  failedTasks: number;
  finished: boolean;
  estimatedDuration: number;
  currentServerTimestamp: number;
  duration: number;
}
