import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthorBook, AuthorIdBookId, Book} from "../book";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../state/app.state";
import {takeWhile} from "rxjs/operators";
import {getBooks} from "../state/book.reducer";
import {DeleteBook, LoadBooks, SaveBook} from "../state/books.actions";
import {getSelectedAuthor} from "../../author/state/author.reducer";

@Component({
  selector: 'app-list-books',
  templateUrl: './list-books.component.html',
  styleUrls: ['./list-books.component.scss']
})
export class ListBooksComponent implements OnInit, OnDestroy {

  authorId: number;
  books: Book[];
  editBookId: number;
  componentActive = true;

  constructor(private store: Store<AppState>) {
  }

  ngOnInit() {
    this.store.pipe(
      select(getSelectedAuthor),
      takeWhile(() => this.componentActive))
      .subscribe(author => {
        this.authorId = author.id;
        this.store.dispatch(new LoadBooks(author.id));
      });
    this.store.pipe(
      select(getBooks),
      takeWhile(() => this.componentActive))
      .subscribe((books) => {
        this.books = books;
      });
  }

  ngOnDestroy(): void {
    this.componentActive = false;
  }

  onSubmit($event: AuthorBook) {
    this.store.dispatch(new SaveBook($event));
  }

  onUpdateBook($event: AuthorBook) {
    if ($event) {
      this.store.dispatch(new SaveBook($event));
    }
    this.editBookId = null;
  }

  onEditBook($event: number) {
    this.editBookId = $event;
  }

  onDeleteBook($event: AuthorIdBookId) {
    this.store.dispatch(new DeleteBook($event));
  }

}
