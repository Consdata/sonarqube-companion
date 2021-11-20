import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SpinnerService {
  private locks: { [key: string]: boolean } = {};

  public lock(id: string): void {
    this.locks[id] = true;
  }

  public unlock(id: string): void {
    this.locks[id] = false;
  }

  public isUnlocked(id: string): boolean {
    return !this.locks[id];
  }

  public isLocked(id: string): boolean {
    return this.locks[id];
  }
}
