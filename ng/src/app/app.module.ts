import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {ModalModule} from 'ngx-bootstrap/modal';
import {TooltipModule} from 'ngx-bootstrap/tooltip';

import {AppComponent} from './app.component';
import {InsertAuthorComponent} from './author/insert-author/insert-author.component';
import {ListAuthorsComponent} from './author/list-authors/list-authors.component';
import {AppRoutingModule} from './app-routing.module';
import {DetailAuthorComponent} from './author/detail-author/detail-author.component';
import {InsertBookComponent} from './author/book/insert-book/insert-book.component';
import {ListBooksComponent} from './author/book/list-books/list-books.component';
import {UpdateAuthorComponent} from './author/detail-author/update-author/update-author.component';
import {UpdateBookComponent} from './author/book/list-books/update-book/update-book.component';
import {DisplayBookComponent} from './author/book/list-books/display-book/display-book.component';
import {NgProgressModule} from "@ngx-progressbar/core";
import {NgProgressHttpModule} from "@ngx-progressbar/http";

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
    DisplayBookComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    HttpClientModule,
    ModalModule.forRoot(),
    TooltipModule.forRoot(),
    NgProgressModule.forRoot(),
    NgProgressHttpModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
