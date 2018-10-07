import {Component, Input, OnInit} from '@angular/core';
import {Book} from "../book";
import {AuthorService} from "../../author.service";

@Component({
  selector: 'app-list-books',
  templateUrl: './list-books.component.html',
  styleUrls: ['./list-books.component.scss']
})
export class ListBooksComponent implements OnInit {

  @Input()
  authorId: number;
  books: Book[];

  constructor(private authorService: AuthorService) {
  }

  ngOnInit() {
    this.authorService.fetchBooks(this.authorId).subscribe(books => this.books = books);
  }

  addBook(): void {
    let book = new Book();
    book.title = Math.random().toString(36).replace(/[^a-z]+/g, '');
    this.books.push(book);
  }

  cancel(): void {

  }

  save(): void {

  }

}
