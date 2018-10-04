import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {InsertAuthorComponent} from "./author/insert-author/insert-author.component";
import {ListAuthorsComponent} from './author/list-authors/list-authors.component';

const routes: Routes = [
  {path: '', redirectTo: '/author/insert', pathMatch: 'full'},
  {path: 'author/list', component: ListAuthorsComponent},
  {path: 'author/insert', component: InsertAuthorComponent}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
