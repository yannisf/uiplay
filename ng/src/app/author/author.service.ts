import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { Author } from './author';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  private subject = new Subject<any>();

  constructor(private http: HttpClient) { }

  list(): Observable<Author[]> {
    return this.http.get<Author[]>('http://localhost:8080/api/author');
  }

  insert(author: Author): Observable<Author> {
    return this.http.post<Author>('http://localhost:8080/api/author', author);
  }

  delete(authorId: number): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/api/author/${authorId}`);
  }

  addedAuthor() {
      this.subject.next({ added: true });
  }

  receiveAddedAuthor(): Observable<any> {
      return this.subject.asObservable();
  }

}
