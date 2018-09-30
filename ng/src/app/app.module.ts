import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { InsertAuthorComponent } from './author/insert-author/insert-author.component';
import { ListAuthorsComponent } from './author/list-authors/list-authors.component';

@NgModule({
  declarations: [
    AppComponent,
    InsertAuthorComponent,
    ListAuthorsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
