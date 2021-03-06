import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {ModalModule} from 'ngx-bootstrap/modal';
import {TooltipModule} from 'ngx-bootstrap/tooltip';

import {AppComponent} from './app.component';
import {InsertAuthorComponent} from './author/components/insert-author/insert-author.component';
import {ListAuthorsComponent} from './author/components/list-authors/list-authors.component';
import {AppRoutingModule} from './app-routing.module';
import {DetailAuthorComponent} from './author/containers/detail-author/detail-author.component';
import {InsertBookComponent} from './book/insert-book/insert-book.component';
import {ListBooksComponent} from './book/list-books/list-books.component';
import {UpdateAuthorComponent} from './author/components/update-author/update-author.component';
import {UpdateBookComponent} from './book/update-book/update-book.component';
import {DisplayBookComponent} from './book/display-book/display-book.component';
import {NgProgressModule} from "@ngx-progressbar/core";
import {NgProgressHttpModule} from "@ngx-progressbar/http";
import {
  AlertModule,
  BsDropdownModule,
  CollapseModule,
  PaginationModule,
  PopoverModule,
  TypeaheadModule
} from "ngx-bootstrap";
import {SortableControlComponent} from './generic/sortable-control/sortable-control.component';
import {AuthorTypeaheadComponent} from './author/author-typeahead/author-typeahead.component';
import {FilterControlComponent} from './generic/filter-control/filter-control.component';
import {NavbarComponent} from './generic/navbar/navbar.component';
import {AddedAuthorAlertComponent} from "./author/components/added-author-alert/added-author-alert.component";
import {StoreModule} from "@ngrx/store";
import {authorsReducer} from "./author/state/author.reducer";
import {booksReducer} from "./book/state/book.reducer";
import {StoreDevtoolsModule} from "@ngrx/store-devtools";
import {environment} from "../environments/environment";
import {EffectsModule} from "@ngrx/effects";
import {AuthorsEffects} from "./author/state/authors.effects";
import {AuthorShellComponent} from './author/containers/author-shell/author-shell.component';
import { PaginateAuthorsComponent } from './author/components/paginate-authors/paginate-authors.component';
import {BooksEffects} from "./book/state/books.effects";
import {DragulaModule} from "ng2-dragula";

@NgModule({
  declarations: [
    AppComponent,
    InsertAuthorComponent,
    ListAuthorsComponent,
    DetailAuthorComponent,
    InsertBookComponent,
    ListBooksComponent,
    UpdateAuthorComponent,
    UpdateBookComponent,
    DisplayBookComponent,
    AddedAuthorAlertComponent,
    SortableControlComponent,
    AuthorTypeaheadComponent,
    FilterControlComponent,
    NavbarComponent,
    AuthorShellComponent,
    PaginateAuthorsComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    HttpClientModule,
    DragulaModule.forRoot(),
    ModalModule.forRoot(),
    AlertModule.forRoot(),
    PopoverModule.forRoot(),
    TooltipModule.forRoot(),
    TypeaheadModule.forRoot(),
    PaginationModule.forRoot(),
    BsDropdownModule.forRoot(),
    NgProgressModule.forRoot(),
    NgProgressHttpModule.forRoot(),
    CollapseModule.forRoot(),
    AppRoutingModule,
    StoreModule.forRoot(authorsReducer),
    StoreModule.forRoot(booksReducer),
    StoreModule.forFeature('authors', authorsReducer),
    StoreModule.forFeature('books', booksReducer),
    EffectsModule.forRoot([]),
    EffectsModule.forFeature([AuthorsEffects, BooksEffects]),
    StoreDevtoolsModule.instrument({
      name: 'UIPlay Devtools',
      maxAge: 25,
      logOnly: environment.production
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
