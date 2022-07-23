import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SideListComponent } from './side-list.component';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';

@NgModule({
  imports: [CommonModule, MatDividerModule, MatIconModule, MatButtonModule, MatProgressSpinnerModule],
  declarations: [SideListComponent],
  exports: [SideListComponent],
})
export class UiSideListModule {}
