import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import {Author} from './author';
import {Book} from "./book/book";

const RESOURCE_AUTHOR = "/api/author";

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  private subject = new Subject<any>();

  constructor(private http: HttpClient) {
  }

  list(): Observable<Author[]> {
    return this.http.get<Author[]>(RESOURCE_AUTHOR);
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
    return this.http.get<Book[]>(`${RESOURCE_AUTHOR}/${authorId}/books`);
  }

}
