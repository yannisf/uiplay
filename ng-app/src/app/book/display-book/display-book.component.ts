import {Component, EventEmitter, Input, OnInit, Output, TemplateRef} from '@angular/core';
import {AuthorService} from "../../author/author.service";
import {Book} from "../book";
import {BsModalRef, BsModalService} from "ngx-bootstrap";

class AuthorBookIds {
  authorId: number;
  bookId: number;
}

@Component({
  selector: 'app-display-book',
  templateUrl: './display-book.component.html',
  styleUrls: ['./display-book.component.scss']
})
export class DisplayBookComponent implements OnInit {

  @Input() book: Book;
  @Input() authorId: number;
  @Output() editingBook = new EventEmitter<number>();
  @Output() deletedBook = new EventEmitter<boolean>();
  authorBookId: AuthorBookIds;
  modalRef: BsModalRef;

  constructor(private authorService: AuthorService,
              private modalService: BsModalService) {
  }

  ngOnInit() {
  }

  editBook() {
    this.editingBook.emit(this.book.id);
  }

  deleteBook() {
    this.authorService.deleteBook(this.authorBookId.authorId, this.authorBookId.bookId).subscribe(_ => {
      this.deletedBook.emit(true);
      this.authorBookId = null;
      this.modalRef.hide();
    });
  }

  openModal(template: TemplateRef<any>, authorId, bookId) {
    this.authorBookId = { authorId, bookId};
    this.modalRef = this.modalService.show(template);
  }

}
