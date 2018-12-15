import {createFeatureSelector, createSelector} from "@ngrx/store";
import {BooksActions, BooksActionTypes} from "./books.actions";
import {Book} from "../book";

export interface BooksState {
  books: Book[];
  error: string;
}

const initialState: BooksState = {
  books: [],
  error: ''
};

export const getBooksState = createFeatureSelector<BooksState>('books');
export const getBooks = createSelector(getBooksState, state => state.books);
export const getError = createSelector(getBooksState, state => state.error);

export function booksReducer(state = initialState, action: BooksActions): BooksState {

  switch (action.type) {

    case BooksActionTypes.LoadBooksSuccess: {
      return {
        ...state,
        books: action.payload,
        error: ''
      };
    }

    case BooksActionTypes.LoadBooksFail:
      return {
        ...state,
        books: [],
        error: action.payload
      };

    case BooksActionTypes.SaveBookSuccess:
      let isEdit = false;
      let updatedBooks = state.books.map(book => {
        if (book.id == action.payload.id) {
          isEdit = true;
          return action.payload;
        } else {
          return book;
        }
      });
      if (!isEdit) {
        updatedBooks.push(action.payload);
      }

      return {
        ...state,
        books: updatedBooks,
        error: ''
      };

    case BooksActionTypes.SaveBookFail:
      return {
        ...state,
        error: action.payload
      };

    case BooksActionTypes.DeleteBookSuccess:
      const books = state.books.filter(b => b.id !== action.payload);
      return {
        ...state,
        books,
        error: ''
      };

    case BooksActionTypes.DeleteBookFail:
      return {
        ...state,
        error: action.payload
      };

    default:
      return state;
  }

}
