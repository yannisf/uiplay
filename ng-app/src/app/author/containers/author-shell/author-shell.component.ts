import {Component, OnInit} from '@angular/core';
import {Author} from '../../author';
import {AppState} from "../../../state/app.state";
import {select, Store} from "@ngrx/store";
import {
  AuthorCreatedClear,
  DeleteAuthor,
  LoadAuthors,
  SaveAuthor,
  UpdateAuthorsFilter,
  UpdateAuthorsPage,
  UpdateAuthorsSortOrder
} from "../../state/authors.actions";
import {getAuthorsState, getCreatedAuthor, getError, getListParams, ListParams} from "../../state/author.reducer";
import {filter, takeWhile} from "rxjs/operators";
import {PageChangedEvent} from "ngx-bootstrap";
import {Sort} from "../../../generic/sort";

@Component({
  selector: 'app-author-shell',
  templateUrl: './author-shell.component.html'
})
export class AuthorShellComponent implements OnInit {

  currentPage: number;
  filter: string;
  sort: Sort;
  authors: Author[];
  totalItems: number;
  createdAuthor: Author;
  private componentActive = true;

  constructor(private store: Store<AppState>) {
  }

  ngOnInit() {
    this.store.pipe(
      select(getAuthorsState),
      takeWhile(() => this.componentActive))
      .subscribe((authorsState) => {
        this.totalItems = authorsState.totalElements;
        this.authors = authorsState.authors;
      });

    this.store.pipe(
      select(getListParams),
      takeWhile(() => this.componentActive))
      .subscribe((listParams: ListParams) => {
        this.sort = listParams.sort;
        this.filter = listParams.filter;
        this.currentPage = listParams.page + 1;
        this.store.dispatch(new LoadAuthors(listParams))
      });

    this.store.pipe(select(getError), filter(error => !!error), takeWhile(() => this.componentActive))
      .subscribe(errorMessage => console.error('Action error:', errorMessage));

    this.store.pipe(
      select(getCreatedAuthor),
      takeWhile(() => this.componentActive))
      .subscribe(createdAuthor => this.createdAuthor = createdAuthor);
  }

  insertAuthor($event): void {
    this.store.dispatch(new SaveAuthor($event));
  }

  authorCreatedClear(): void {
    this.store.dispatch(new AuthorCreatedClear());
  }

  onAuthorsDelete($event): void {
    this.store.dispatch(new DeleteAuthor($event));
  }

  onFilterChange($event: string) {
    this.store.dispatch(new UpdateAuthorsFilter($event));
  }

  onClearFilter() {
    this.store.dispatch(new UpdateAuthorsFilter(''));
  }

  onSortChange() {
    this.store.dispatch(new UpdateAuthorsSortOrder());
  }

  onPageChange($event: PageChangedEvent) {
    this.store.dispatch(new UpdateAuthorsPage($event.page - 1));
  }

}
