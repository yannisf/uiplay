import {Component, EventEmitter, Input, Output} from '@angular/core';
import {PageChangedEvent} from "ngx-bootstrap";

@Component({
  selector: 'app-paginate-authors',
  templateUrl: './paginate-authors.component.html'
})
export class PaginateAuthorsComponent {

  @Input() currentPage: number;
  @Input() totalItems: number;
  @Output() pageChanged = new EventEmitter<PageChangedEvent>();

  onPageChanged($event) {
    this.pageChanged.emit($event);
  }

}
