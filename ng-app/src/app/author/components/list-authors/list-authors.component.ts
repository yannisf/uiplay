import {Component, EventEmitter, Input, Output, TemplateRef} from '@angular/core';
import {BsModalService} from 'ngx-bootstrap/modal';
import {BsModalRef} from 'ngx-bootstrap/modal/bs-modal-ref.service';
import {Sort} from "../../../generic/sort";
import {Author} from "../../author";
import {PageChangedEvent} from "ngx-bootstrap";

@Component({
  selector: 'app-list-authors',
  templateUrl: './list-authors.component.html',
  styleUrls: ['./list-authors.component.scss']
})
export class ListAuthorsComponent {

  @Input() currentPage: number;
  @Input() filter: string;
  @Input() sort: Sort;
  @Input() authors: Author[];
  @Input() totalItems: number;
  @Input() createdAuthor: Author;
  @Output() filterChange = new EventEmitter<string>();
  @Output() filterClear = new EventEmitter<void>();
  @Output() sortChange = new EventEmitter<void>();
  @Output() pageChange = new EventEmitter<PageChangedEvent>();
  @Output() authorDelete = new EventEmitter<number>();

  modalRef: BsModalRef;
  selectedAuthorId: number;

  constructor(private modalService: BsModalService) {
  }

  openModal(template: TemplateRef<any>, authorId) {
    this.selectedAuthorId = authorId;
    this.modalRef = this.modalService.show(template);
  }

  onFilterChange($event: string) {
    this.filterChange.emit($event);
  }

  onClearFilter() {
    this.filterClear.emit();
  }

  onSortChange() {
    this.sortChange.emit();
  }

  onPageChange($event) {
    this.pageChange.emit($event);
  }

  onAuthorDelete() {
    this.authorDelete.emit(this.selectedAuthorId);
    this.selectedAuthorId = null;
    this.modalRef.hide();
  }

}

