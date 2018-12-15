import {Component, EventEmitter, Input, Output, TemplateRef} from '@angular/core';
import {AuthorIdBookId, Book} from "../book";
import {BsModalRef, BsModalService} from "ngx-bootstrap";

@Component({
  selector: 'app-display-book',
  templateUrl: './display-book.component.html',
  styleUrls: ['./display-book.component.scss']
})
export class DisplayBookComponent {

  @Input() book: Book;
  @Input() authorId: number;
  @Output() editingBook = new EventEmitter<number>();
  @Output() deletedBook = new EventEmitter<AuthorIdBookId>();
  authorBookId: AuthorIdBookId;
  modalRef: BsModalRef;

  constructor(private modalService: BsModalService) {
  }

  editBook() {
    this.editingBook.emit(this.book.id);
  }

  deleteBook() {
    this.deletedBook.emit({...this.authorBookId});
    this.modalRef.hide();
    this.authorBookId = null;
  }

  openModal(template: TemplateRef<any>, authorId, bookId) {
    this.authorBookId = {authorId, bookId};
    this.modalRef = this.modalService.show(template);
  }

}
