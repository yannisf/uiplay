import {Action} from "@ngrx/store";
import {PagedAuthors} from "../paged-authors";
import {Author} from "../author";
import {ListParams} from "./author.reducer";

export enum AuthorsActionTypes {
  UpdateAuthorsSortOrder = '[Authors] Update authors sort order',
  UpdateAuthorsFilter = '[Authors] Update authors filter',
  UpdateAuthorsPage = '[Authors] Update authors page',
  LoadAuthors = '[Authors] Load',
  LoadAuthorsSuccess = '[Authors] Load success',
  LoadAuthorsFail = '[Authors] Load fail',
  LoadAuthorDetails = '[Authors] Load author details',
  LoadAuthorDetailsSuccess = '[Authors] Load author details success',
  LoadAuthorDetailsFail = '[Authors] Load author details fail',
  SaveAuthor = '[Authors] Save',
  SaveAuthorSuccess = '[Authors] Save success',
  SaveAuthorFail = '[Authors] Save fail',
  DeleteAuthor = '[Authors] Delete',
  DeleteAuthorSuccess = '[Authors] Delete success',
  DeleteAuthorFail = '[Authors] Delete fail',
  AuthorCreatedClear = '[Authors] Clear created author'
}

export class UpdateAuthorsSortOrder implements Action {
  readonly type = AuthorsActionTypes.UpdateAuthorsSortOrder;
}

export class UpdateAuthorsFilter implements Action {
  readonly type = AuthorsActionTypes.UpdateAuthorsFilter;

  constructor(public payload: string) {
  }
}

export class UpdateAuthorsPage implements Action {
  readonly type = AuthorsActionTypes.UpdateAuthorsPage;

  constructor(public payload: number) {
  }
}

export class LoadAuthors implements Action {
  readonly type = AuthorsActionTypes.LoadAuthors;

  constructor(public payload: ListParams) {
  }
}

export class LoadAuthorsSuccess implements Action {
  readonly type = AuthorsActionTypes.LoadAuthorsSuccess;

  constructor(public payload: PagedAuthors) {
  }
}

export class LoadAuthorsFail implements Action {
  readonly type = AuthorsActionTypes.LoadAuthorsFail;

  constructor(public payload: string) {
  }
}

export class LoadAuthorDetails implements Action {
  readonly type = AuthorsActionTypes.LoadAuthorDetails;

  constructor(public payload: number) {
  }
}

export class LoadAuthorDetailsSuccess implements Action {
  readonly type = AuthorsActionTypes.LoadAuthorDetailsSuccess;

  constructor(public payload: Author) {
  }
}

export class LoadAuthorDetailsFail implements Action {
  readonly type = AuthorsActionTypes.LoadAuthorDetailsFail;

  constructor(public payload: string) {
  }
}

export class SaveAuthor implements Action {
  readonly type = AuthorsActionTypes.SaveAuthor;

  constructor(public payload: Author) {
  }
}

export class SaveAuthorSuccess implements Action {
  readonly type = AuthorsActionTypes.SaveAuthorSuccess;

  constructor(public payload: Author) {
  }
}

export class SaveAuthorFail implements Action {
  readonly type = AuthorsActionTypes.SaveAuthorFail;

  constructor(public payload: string) {
  }
}

export class DeleteAuthor implements Action {
  readonly type = AuthorsActionTypes.DeleteAuthor;

  constructor(public payload: number) {
  }
}

export class DeleteAuthorSuccess implements Action {
  readonly type = AuthorsActionTypes.DeleteAuthorSuccess;

  constructor(public payload: number) {
  }
}

export class DeleteAuthorFail implements Action {
  readonly type = AuthorsActionTypes.DeleteAuthorFail;

  constructor(public payload: string) {
  }
}

export class AuthorCreatedClear implements Action {
  readonly type = AuthorsActionTypes.AuthorCreatedClear;
}

export type AuthorsActions = UpdateAuthorsSortOrder
  | UpdateAuthorsFilter
  | UpdateAuthorsPage
  | LoadAuthors
  | LoadAuthorsSuccess
  | LoadAuthorsFail
  | LoadAuthorDetails
  | LoadAuthorDetailsSuccess
  | LoadAuthorDetailsFail
  | SaveAuthor
  | SaveAuthorSuccess
  | SaveAuthorFail
  | DeleteAuthor
  | DeleteAuthorSuccess
  | DeleteAuthorFail
  | AuthorCreatedClear;
