import {Injectable} from '@angular/core';
import {Actions, Effect, ofType} from "@ngrx/effects";
import {AuthorService} from "../../author/author.service";
import {catchError, map, switchMap, withLatestFrom} from "rxjs/operators";
import {of} from "rxjs";
import {AppState} from "../../state/app.state";
import {select, Store} from "@ngrx/store";
import {
  BooksActionTypes,
  DeleteBook,
  DeleteBookFail,
  DeleteBookSuccess,
  LoadBooks,
  LoadBooksFail,
  LoadBooksSuccess,
  ReorderBooks,
  ReorderBooksFail,
  ReorderBooksSuccess,
  SaveBook,
  SaveBookFail,
  SaveBookSuccess
} from "./books.actions";
import {AuthorBook, AuthorIdBookId, Book} from "../book";
import {getSelectedAuthor} from "../../author/state/author.reducer";

@Injectable()
export class BooksEffects {

  constructor(private actions$: Actions,
              private store: Store<AppState>,
              private authorService: AuthorService) {
  }

  @Effect()
  loadBooks$ = this.actions$.pipe(
    ofType(BooksActionTypes.LoadBooks),
    map((action: LoadBooks) => action.payload),
    switchMap(authorId => this.authorService.fetchBooks(authorId).pipe(
      map((books: Book[]) => (new LoadBooksSuccess(books))),
      catchError(error => of(new LoadBooksFail(error)))
    ))
  );

  @Effect()
  saveBook$ = this.actions$.pipe(
    ofType(BooksActionTypes.SaveBook),
    map((action: SaveBook) => action.payload),
    switchMap((authorBook: AuthorBook) => this.authorService.addBook(authorBook.authorId, authorBook.book).pipe(
      map((book: Book) => (new SaveBookSuccess(book))),
      catchError(error => of(new SaveBookFail(error)))
    ))
  );

  @Effect()
  deleteBook$ = this.actions$.pipe(
    ofType(BooksActionTypes.DeleteBook),
    map((action: DeleteBook) => action.payload),
    switchMap((ids: AuthorIdBookId) => this.authorService.deleteBook(ids.authorId, ids.bookId).pipe(
      map(() => (new DeleteBookSuccess(ids.bookId))),
      catchError(error => of(new DeleteBookFail(error)))
    ))
  );

  @Effect()
  reorderBook$ = this.actions$.pipe(
    ofType(BooksActionTypes.ReorderBooks),
    map((action: ReorderBooks) => action.payload),
    withLatestFrom(this.store.pipe(
      select(getSelectedAuthor),
      map(a => !!a ? a.id : -1))),
    switchMap(([bookIds, authorId]) => {
      return this.authorService.reorderBooks(authorId, bookIds).pipe(
        map(() => (new ReorderBooksSuccess(bookIds))),
        catchError(error => of(new ReorderBooksFail(error)))
      );
    })
  );

}
