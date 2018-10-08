import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Book} from "../book";
import {AuthorService} from "../../author.service";

@Component({
  selector: 'app-list-books',
  templateUrl: './list-books.component.html',
  styleUrls: ['./list-books.component.scss']
})
export class ListBooksComponent implements OnInit {

  @Input() authorId: number;
  books: Book[];
  newBook: boolean;
  editBookId: number;

  constructor(private authorService: AuthorService) {
  }

  ngOnInit() {
    this.fetchBooks();
  }

  fetchBooks() {
    this.authorService.fetchBooks(this.authorId).subscribe(books => this.books = books);
  }

  saved($event: string) {
    if ($event === 'success') {
      this.fetchBooks();
    }
    this.newBook = false;
  }

  updated($event: string) {
    if ($event === 'success') {
      this.fetchBooks();
    }
    this.editBookId = null;
  }

  deleteBook(bookId: number) {
    this.authorService.deleteBook(this.authorId, bookId).subscribe( _ => {
      this.fetchBooks();
    });
  }

}
