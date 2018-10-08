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
  book: Book;
  editBookId: number;

  constructor(private authorService: AuthorService) {
  }

  ngOnInit() {
    this.fetchBooks();
  }

  fetchBooks() {
    this.authorService.fetchBooks(this.authorId).subscribe(books => this.books = books);
  }

  newBook(): void {
    this.book = new Book();
  }

  cancel(){
    this.book = null;
  }

  cancelEdit() {
    this.editBookId = null;
  }

  save(){
    this.authorService.addBook(this.authorId, this.book).subscribe(book => {
      this.book = null;
      this.fetchBooks();
    });
  }

  saveEdit(book: Book){
    console.log(book);
    this.authorService.addBook(this.authorId, book).subscribe(book => {
      this.editBookId = null;
      this.fetchBooks();
    });
  }

  deleteBook(bookId: number) {
    this.authorService.deleteBook(this.authorId, bookId).subscribe( _ => {
      this.fetchBooks();
    });
  }

}
