import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import {Author} from './author';
import {Book} from "../book/book";
import {PagedAuthors} from "./paged-authors";
import {Sort} from "../generic/sort";
import {Store} from "@ngrx/store";
import {AppState} from "../state/app.state";
import {environment} from "../../environments/environment";

const RESOURCE_AUTHOR = "api/author";

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  private subject = new Subject<any>();

  constructor(private store: Store<AppState>,
              private http: HttpClient) {
  }

  list(): Observable<Author[]> {
    return this.http.get<Author[]>(RESOURCE_AUTHOR);
  }

  page(page: number, filter: string, sort: Sort, pageSize = environment.pageSize): Observable<PagedAuthors> {
    const url = `${RESOURCE_AUTHOR}/page/${page}?pageSize=${pageSize}&sort=${Sort[sort]}&filter=${filter}`;
    return this.http.get<PagedAuthors>(url);
  }

  search(query: string): Observable<PagedAuthors> {
    return this.http.get<PagedAuthors>(`${RESOURCE_AUTHOR}/search?q=${query}`);
  }

  insert(author: Author): Observable<Author> {
    return this.http.post<Author>(RESOURCE_AUTHOR, author);
  }

  delete(authorId: number): Observable<void> {
    return this.http.delete<void>(`${RESOURCE_AUTHOR}/${authorId}`);
  }

  fetch(authorId: number): Observable<Author> {
    return this.http.get<Author>(`${RESOURCE_AUTHOR}/${authorId}`)
  }

  fetchBooks(authorId: number) {
    return this.http.get<Book[]>(`${RESOURCE_AUTHOR}/${authorId}/book`);
  }

  addBook(authorId: number, book: Book) {
    return this.http.post<Book>(`${RESOURCE_AUTHOR}/${authorId}/book`, book);
  }

  deleteBook(authorId: number, bookId: number) {
    return this.http.delete<void>(`${RESOURCE_AUTHOR}/${authorId}/book/${bookId}`);
  }

  reorderBooks(authorId: number, bookIds: number[]) {
    return this.http.post<void>(`${RESOURCE_AUTHOR}/${authorId}/book/reorder`, bookIds);
  }

}
