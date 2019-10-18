export enum TimeUnit {
  SECONDS=3,
  MINUTES,
  HOURS,
  DAYS
}

export class SchedulerConfig {

  interval: number;
  timeUnit: TimeUnit;

  constructor(data?: any) {
    if (data) {
      this.interval = data.interval;
      this.timeUnit = data.timeUnit;
    }
  }
}
