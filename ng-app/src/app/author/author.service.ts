import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import {Author} from './author';
import {Book} from "./book/book";
import {PagedAuthor} from "./paged-author";
import {Sort} from "./sort";

const RESOURCE_AUTHOR = "api/author";

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  currentPage = 0;
  sort: string = Sort[Sort.NONE];
  filter = '';
  private subject = new Subject<any>();

  constructor(private http: HttpClient) {
  }

  list(): Observable<Author[]> {
    return this.http.get<Author[]>(RESOURCE_AUTHOR);
  }

  page(pageNumber: number, pageSize = 10): Observable<PagedAuthor> {
    return this.http.get<PagedAuthor>(`${RESOURCE_AUTHOR}/page/${pageNumber}?pageSize=${pageSize}&sort=${this.sort}&filter=${this.filter}`);
  }

  search(query: string): Observable<PagedAuthor> {
    return this.http.get<PagedAuthor>(`${RESOURCE_AUTHOR}/search?q=${query}`);
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

  addedAuthor() {
    this.subject.next({added: true});
  }

  receiveAddedAuthor(): Observable<any> {
    return this.subject.asObservable();
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

}
