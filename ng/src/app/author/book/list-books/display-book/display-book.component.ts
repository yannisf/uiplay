import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AuthorService} from "../../../author.service";
import {Book} from "../../book";

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

  constructor(private authorService: AuthorService) {
  }

  ngOnInit() {
  }

  editBook() {
    this.editingBook.emit(this.book.id);
  }

  deleteBook() {
    this.authorService.deleteBook(this.authorId, this.book.id).subscribe(_ => {
      this.deletedBook.emit(true);
    });
  }
}
