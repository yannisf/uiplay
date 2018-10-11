import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Sort} from "../sort";

@Component({
  selector: 'app-sortable-control',
  templateUrl: './sortable-control.component.html',
  styleUrls: ['./sortable-control.component.scss']
})
export class SortableControlComponent implements OnInit {

  @Input() label: string;
  @Output() updatedSort = new EventEmitter<Sort>();
  selectedSort = Sort.NONE;

  constructor() {
  }

  ngOnInit() {
  }

  clickedHeader($event) {
    this.selectedSort = ++this.selectedSort % 3;
    this.updatedSort.emit(this.selectedSort);
  }
}
