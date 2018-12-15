import {Action} from "@ngrx/store";
import {AuthorBook, AuthorIdBookId, Book} from "../book";

export enum BooksActionTypes {
  LoadBooks = '[Books] Load',
  LoadBooksSuccess = '[Books] Load success',
  LoadBooksFail = '[Books] Load fail',
  SaveBook = '[Books] Save',
  SaveBookSuccess = '[Books] Save success',
  SaveBookFail = '[Books] Save fail',
  DeleteBook = '[Books] Delete',
  DeleteBookSuccess = '[Books] Delete success',
  DeleteBookFail = '[Books] Delete fail',
}

export class LoadBooks implements Action {
  readonly type = BooksActionTypes.LoadBooks;

  constructor(public payload: number) {
  }
}

export class LoadBooksSuccess implements Action {
  readonly type = BooksActionTypes.LoadBooksSuccess;

  constructor(public payload: Book[]) {
  }
}

export class LoadBooksFail implements Action {
  readonly type = BooksActionTypes.LoadBooksFail;

  constructor(public payload: string) {
  }
}

export class SaveBook implements Action {
  readonly type = BooksActionTypes.SaveBook;

  constructor(public payload: AuthorBook) {
  }
}

export class SaveBookSuccess implements Action {
  readonly type = BooksActionTypes.SaveBookSuccess;

  constructor(public payload: Book) {
  }
}

export class SaveBookFail implements Action {
  readonly type = BooksActionTypes.SaveBookFail;

  constructor(public payload: string) {
  }
}

export class DeleteBook implements Action {
  readonly type = BooksActionTypes.DeleteBook;

  constructor(public payload: AuthorIdBookId) {
  }
}

export class DeleteBookSuccess implements Action {
  readonly type = BooksActionTypes.DeleteBookSuccess;

  constructor(public payload: number) {
  }
}

export class DeleteBookFail implements Action {
  readonly type = BooksActionTypes.DeleteBookFail;

  constructor(public payload: string) {
  }
}

export type BooksActions = LoadBooks |
  LoadBooksSuccess |
  LoadBooksFail |
  SaveBook |
  SaveBookSuccess |
  SaveBookFail |
  DeleteBook |
  DeleteBookSuccess |
  DeleteBookFail;


