import {Component, Input, OnInit} from '@angular/core';
import {Book} from "../book";
import {AuthorService} from "../../author/author.service";

@Component({
  selector: 'app-list-books',
  templateUrl: './list-books.component.html',
  styleUrls: ['./list-books.component.scss']
})
export class ListBooksComponent implements OnInit {

  private _authorId: number;
  books: Book[];
  editBookId: number;

  constructor(private authorService: AuthorService) {
  }

  ngOnInit() {
  }

  @Input()
  set authorId(authorId: number) {
    this._authorId = authorId;
    this.fetchBooks();
  }

  get authorId() {
    return this._authorId;
  }

  fetchBooks() {
    this.authorService.fetchBooks(this._authorId)
      .subscribe(books => this.books = books);
  }

  onSave($event: string) {
    if ($event === 'success') {
      this.fetchBooks();
    }
  }

  updated($event: string) {
    if ($event === 'success') {
      this.fetchBooks();
    }
    this.editBookId = null;
  }

  onEditBook($event: number) {
    this.editBookId = $event;
  }

  onDeleteBook($event: boolean) {
    if ($event) {
      this.fetchBooks();
    }
  }

}
