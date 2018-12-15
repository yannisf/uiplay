import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {AuthorBook, Book} from "../book";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-update-book',
  templateUrl: './update-book.component.html',
  styleUrls: ['./update-book.component.scss']
})
export class UpdateBookComponent implements OnInit {

  @Input() book: Book;
  @Input() authorId: number;
  @Output() submitted = new EventEmitter<AuthorBook>();
  @ViewChild('input') private input: ElementRef;
  bookForm = new FormGroup({
    title: new FormControl('', [
      Validators.required,
      Validators.minLength(2)])
  });

  ngOnInit(): void {
    this.bookForm.setValue({'title': this.book.title});
    this.input.nativeElement.focus();
  }

  get title() {
    return this.bookForm.get('title');
  }

  submit() {
    this.book.title = this.bookForm.value.title;
    const authorBook = new AuthorBook();
    authorBook.authorId = this.authorId;
    authorBook.book = this.book;
    this.submitted.emit(authorBook);
  }

  cancel() {
    this.submitted.emit();
  }

}
