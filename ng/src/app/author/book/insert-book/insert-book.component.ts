import {Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Book} from "../book";
import {AuthorService} from "../../author.service";

@Component({
  selector: 'app-insert-book',
  templateUrl: './insert-book.component.html',
  styleUrls: ['./insert-book.component.scss']
})
export class InsertBookComponent implements OnInit {

  private _el: ElementRef;

  @Input() authorId: number;
  @Output() saved = new EventEmitter<string>();
  book: Book;

  @ViewChild('inputBook')
  set input(el: ElementRef<HTMLInputElement>) {
    this._el = el;
    if (el) {
      el.nativeElement.focus();
    }
  };

  constructor(private authorService: AuthorService) {
  }

  ngOnInit() {
  }

  @HostListener('window:keyup.b', ['$event'])
  addBook($event): boolean {
    if ($event !== undefined && $event !== null && $event.target.localName !== 'body') {
      return false;
    }
    this.book = new Book();
  }

  cancel() {
    this.book = null;
  }

  save() {
    this.authorService.addBook(this.authorId, this.book).subscribe(book => {
      this.saved.emit('success');
      this.book = null;
    });
  }

}
