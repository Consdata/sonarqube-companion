import {AfterViewInit, Component, OnDestroy} from '@angular/core';
import {SynchronizationService} from './synchronization-service';
import {SynchronizationState} from './synchronization-state';
import {TimerObservable} from 'rxjs/observable/TimerObservable';
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'sq-synchronization',
  template: `
    <span *ngIf="!synchronizationInProgress">
      Last synchronization took: {{lastSynchronizationTime}} ms
    </span>
    <div class="sq-synchronization-progress-bar" *ngIf="synchronizationInProgress">
      <div class="sq-synchronization-progress" [style.width.px]="progress"></div>
    </div>
    <span
      [ngClass]="{'synchronization-in-progress': synchronizationInProgress}"
      class="fa fa-refresh sq-synchronize-button"
      title="Start synchronization"
      (click)="startSynchronization()"></span>
  `
})
export class SynchronizationComponent implements AfterViewInit, OnDestroy {
  readonly SYNCHRONIZATION_IN_PROGRESS_INTERVAL = 1000;
  readonly SYNCHRONIZED_INTERVAL = 20000;
  lastSynchronizationTime: number;
  synchronizationInProgress = true;
  pollScheduleInterval = this.SYNCHRONIZATION_IN_PROGRESS_INTERVAL;
  pollTimerSubscription: Subscription;
  pollSubscription: Subscription;
  startSynchronizationSubscription: Subscription;
  progress: number;

  constructor(private synchronizationService: SynchronizationService) {

  }

  ngOnDestroy(): void {
    if (this.pollTimerSubscription) {
      this.pollTimerSubscription.unsubscribe();
    }
    if (this.pollSubscription) {
      this.pollSubscription.unsubscribe();
    }
    if (this.startSynchronizationSubscription) {
      this.startSynchronizationSubscription.unsubscribe();
    }
  }

  ngAfterViewInit(): void {
    this.pollState();
  }

  startSynchronization(): void {
    if (this.synchronizationInProgress) {
      return;
    }
    this.synchronizationInProgress = true;
    if (this.startSynchronizationSubscription) {
      this.startSynchronizationSubscription.unsubscribe();
    }
    this.startSynchronizationSubscription = this.synchronizationService.startSynchronization().subscribe(() => {
      this.pollState();
    });
  }

  private processSynchronizationState(state: SynchronizationState) {
    if (state.finished) {
      this.synchronizationInProgress = false;
      this.progress = 0;
      this.pollScheduleInterval = this.SYNCHRONIZED_INTERVAL;
      this.lastSynchronizationTime = state.duration;
    } else {
      this.synchronizationInProgress = true;
      this.pollScheduleInterval = this.SYNCHRONIZATION_IN_PROGRESS_INTERVAL;
      const estimatedProgress = ((state.currentServerTimestamp - state.startTimestamp) / state.estimatedDuration) * 100.0;
      if (estimatedProgress > 99.0) {
        this.progress = 99.0;
      } else {
        this.progress = estimatedProgress;
      }
    }
    this.scheduleNextPool();
  }

  private pollState(): void {
    this.pollSubscription = this.synchronizationService
      .synchronizationState()
      .subscribe((state: SynchronizationState) => this.processSynchronizationState(state));
  }

  private scheduleNextPool(): void {
    if (this.pollTimerSubscription) {
      this.pollTimerSubscription.unsubscribe();
    }
    this.pollTimerSubscription = TimerObservable.create(this.pollScheduleInterval).subscribe(() => {
      this.pollState();
    });
  }
}
