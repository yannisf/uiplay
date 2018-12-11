import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Sort} from "../sort";
import {AuthorService} from "../author.service";

@Component({
  selector: 'app-sortable-control',
  templateUrl: './sortable-control.component.html',
  styleUrls: ['./sortable-control.component.scss']
})
export class SortableControlComponent implements OnInit {

  @Input() label: string;
  @Output() updatedSort = new EventEmitter<Sort>();
  selectedSort: Sort;

  constructor(private authorService: AuthorService) {
  }

  ngOnInit() {
    if (this.authorService.sort) {
      this.selectedSort = Sort[this.authorService.sort];
    }
  }

  clickedHeader($event) {
    if (!this.selectedSort) {
      this.selectedSort = Sort.NONE
    }
    this.selectedSort = ++this.selectedSort % 3;
    this.updatedSort.emit(this.selectedSort);
  }
}
