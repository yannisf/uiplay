import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DetailAuthorComponent} from "./author/containers/detail-author/detail-author.component";
import {AuthorShellComponent} from "./author/containers/author-shell/author-shell.component";

const routes: Routes = [
  {path: '', redirectTo: '/author/insert', pathMatch: 'full'},
  {path: 'author/insert', component: AuthorShellComponent},
  {path: 'author/:id', component: DetailAuthorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
