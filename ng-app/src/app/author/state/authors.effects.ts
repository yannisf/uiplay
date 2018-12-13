import {Injectable} from '@angular/core';
import {Actions, Effect, ofType} from "@ngrx/effects";
import {AuthorService} from "../author.service";
import {
  AuthorsActionTypes,
  DeleteAuthor,
  DeleteAuthorFail,
  DeleteAuthorSuccess,
  LoadAuthorDetails,
  LoadAuthorDetailsFail,
  LoadAuthorDetailsSuccess,
  LoadAuthors,
  LoadAuthorsFail,
  LoadAuthorsSuccess,
  SaveAuthor,
  SaveAuthorFail,
  SaveAuthorSuccess
} from "./authors.actions";
import {catchError, map, switchMap, withLatestFrom} from "rxjs/operators";
import {PagedAuthors} from "../paged-authors";
import {of} from "rxjs";
import {Author} from "../author";
import {AppState} from "../../state/app.state";
import {select, Store} from "@ngrx/store";
import {getListParams} from "./author.reducer";

@Injectable()
export class AuthorsEffects {

  constructor(private actions$: Actions,
              private store: Store<AppState>,
              private authorService: AuthorService) {
  }

  @Effect()
  loadAuthors$ = this.actions$.pipe(
    ofType(AuthorsActionTypes.LoadAuthors),
    map((action: LoadAuthors) => action.payload),
    switchMap(listParams => this.authorService.page(listParams.page, listParams.filter, listParams.sort).pipe(
      map((pageAuthors: PagedAuthors) => (new LoadAuthorsSuccess(pageAuthors))),
      catchError(error => of(new LoadAuthorsFail(error)))
    ))
  );
  @Effect()
  loadAuthorDetails$ = this.actions$.pipe(
    ofType(AuthorsActionTypes.LoadAuthorDetails),
    map((action: LoadAuthorDetails) => action.payload),
    switchMap((authorId: number) => this.authorService.fetch(authorId).pipe(
      map((author: Author) => (new LoadAuthorDetailsSuccess(author))),
      catchError(error => of(new LoadAuthorDetailsFail(error)))
    ))
  );
  @Effect()
  saveAuthor$ = this.actions$.pipe(
    ofType(AuthorsActionTypes.SaveAuthor),
    map((action: SaveAuthor) => action.payload),
    switchMap((author: Author) => this.authorService.insert(author).pipe(
      map((author: Author) => (new SaveAuthorSuccess(author))),
      catchError(error => of(new SaveAuthorFail(error)))
    ))
  );

  @Effect()
  deleteAuthor$ = this.actions$.pipe(
    ofType(AuthorsActionTypes.DeleteAuthor),
    map((action: DeleteAuthor) => action.payload),
    switchMap((authorId: number) => this.authorService.delete(authorId).pipe(
      map(() => (new DeleteAuthorSuccess(authorId))),
      catchError(error => of(new DeleteAuthorFail(error)))
    ))
  );

  @Effect()
  reloadAuthor$ = this.actions$.pipe(
    ofType(AuthorsActionTypes.SaveAuthorSuccess, AuthorsActionTypes.DeleteAuthorSuccess),
    withLatestFrom(this.store.pipe(select(getListParams))),
    map(([action, listParams]) => listParams),
    switchMap(listParams => of(new LoadAuthors(listParams))));

}
