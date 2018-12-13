import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Sort} from "../sort";

@Component({
  selector: 'app-sortable-control',
  templateUrl: './sortable-control.component.html',
  styleUrls: ['./sortable-control.component.scss']
})
export class SortableControlComponent {

  @Input() label: string;
  @Input() sort: Sort;
  @Output() sortChange = new EventEmitter<void>();

  clicked() {
    this.sortChange.emit();
  }

}
