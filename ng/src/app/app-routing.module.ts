import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {InsertAuthorComponent} from "./author/insert-author/insert-author.component";
import {DetailAuthorComponent} from "./author/detail-author/detail-author.component";

const routes: Routes = [
  {path: '', redirectTo: '/author/insert', pathMatch: 'full'},
  {path: 'author/insert', component: InsertAuthorComponent},
  {path: 'author/:id', component: DetailAuthorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
