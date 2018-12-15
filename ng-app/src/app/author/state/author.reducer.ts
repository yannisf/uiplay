import {Sort} from "../../generic/sort";
import {createFeatureSelector, createSelector} from "@ngrx/store";
import {AuthorsActions, AuthorsActionTypes} from "./authors.actions";
import {Author} from "../author";

export interface ListParams {
  page: number,
  filter: string,
  sort: Sort;
}

export interface AuthorsState {
  listParams: ListParams;
  totalElements: number;
  totalPages: number;
  authors: Author[];
  selectedAuthor: Author;
  createdAuthor: Author;
  error: string;
}

const initialState: AuthorsState = {
  listParams: {
    page: 0,
    filter: '',
    sort: Sort.NONE,
  },
  totalElements: 0,
  totalPages: 0,
  authors: [],
  selectedAuthor: null,
  createdAuthor: null,
  error: ''
};

export const getAuthorsState = createFeatureSelector<AuthorsState>('authors');
export const getListParams = createSelector(getAuthorsState, state => state.listParams);
export const getSelectedAuthor = createSelector(getAuthorsState, state => state.selectedAuthor);
export const getCreatedAuthor = createSelector(getAuthorsState, state => state.createdAuthor);
export const getError = createSelector(getAuthorsState, state => state.error);

export function authorsReducer(state = initialState, action: AuthorsActions): AuthorsState {

  switch (action.type) {

    case AuthorsActionTypes.UpdateAuthorsSortOrder: {
      let listParams = {...state.listParams};
      listParams.sort = ++listParams.sort % 3;
      return {...state, listParams}
    }

    case AuthorsActionTypes.UpdateAuthorsFilter: {
      let listParams = {...state.listParams};
      listParams.page = 0;
      listParams.filter = action.payload;
      return {...state, listParams}
    }

    case AuthorsActionTypes.UpdateAuthorsPage: {
      let listParams = {...state.listParams};
      listParams.page = action.payload;
      return {...state, listParams}
    }

    case AuthorsActionTypes.LoadAuthorsSuccess: {
      return {
        ...state,
        totalElements: action.payload.totalElements,
        totalPages: action.payload.totalPages,
        authors: action.payload.values,
        selectedAuthor: null,
        error: ''
      };
    }

    case AuthorsActionTypes.LoadAuthorsFail:
      return {
        ...state,
        totalElements: 0,
        totalPages: 0,
        authors: [],
        selectedAuthor: null,
        error: action.payload
      };

    case AuthorsActionTypes.LoadAuthorDetailsSuccess:
      return {
        ...state,
        selectedAuthor: action.payload,
        error: ''
      };

    case AuthorsActionTypes.LoadAuthorDetailsFail:
      return {
        ...state,
        selectedAuthor: null,
        error: action.payload
      };

    case AuthorsActionTypes.SaveAuthorSuccess:
      return {
        ...state,
        createdAuthor: action.payload,
      };

    case AuthorsActionTypes.SaveAuthorFail:
      return {
        ...state,
        error: action.payload
      };

    case AuthorsActionTypes.DeleteAuthorSuccess:
      return {
        ...state,
        error: ''
      };

    case AuthorsActionTypes.DeleteAuthorFail:
      return {
        ...state,
        error: action.payload
      };

    case AuthorsActionTypes.AuthorCreatedClear:
      return {
        ...state,
        createdAuthor: null,
      };

    default:
      return state;
  }

}
