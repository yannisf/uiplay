import {Component, ElementRef, EventEmitter, HostListener, Input, Output, ViewChild} from '@angular/core';
import {AuthorBook, Book} from "../book";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-insert-book',
  templateUrl: './insert-book.component.html',
  styleUrls: ['./insert-book.component.scss']
})
export class InsertBookComponent {

  private _el: ElementRef;

  @Input() authorId: number;
  @Output() submitted = new EventEmitter<AuthorBook>();
  formEnabled: boolean;

  bookForm = new FormGroup({
    title: new FormControl('', [
      Validators.required,
      Validators.minLength(2)])
  });

  @ViewChild('inputBook')
  set input(el: ElementRef<HTMLInputElement>) {
    this._el = el;
    if (el) {
      el.nativeElement.focus();
    }
  };

  @HostListener('window:keyup.b', ['$event'])
  addBook($event): boolean {
    if ($event !== undefined && $event !== null && $event.target.localName !== 'body') {
      return false;
    }
    this.formEnabled = true;
  }

  get title() {
    return this.bookForm.get('title');
  }

  submit() {
    const book = new Book();
    book.title = this.bookForm.value.title;
    const authorBook = new AuthorBook();
    authorBook.authorId = this.authorId;
    authorBook.book = book;
    this.submitted.emit(authorBook);
    this.bookForm.reset();
    this.formEnabled = false;
  }

  cancel() {
    this.bookForm.reset();
    this.formEnabled = false;
  }

}
