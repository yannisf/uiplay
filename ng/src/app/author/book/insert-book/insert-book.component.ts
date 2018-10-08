import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Book} from "../book";
import {AuthorService} from "../../author.service";

@Component({
  selector: 'app-insert-book',
  templateUrl: './insert-book.component.html',
  styleUrls: ['./insert-book.component.scss']
})
export class InsertBookComponent implements OnInit {

  @Input() authorId: number;
  @Output() saved = new EventEmitter<string>();
  book = new Book();

  constructor(private authorService: AuthorService) {
  }

  ngOnInit() {
  }

  cancel() {
    this.saved.emit('cancel');
  }

  save() {
    this.authorService.addBook(this.authorId, this.book).subscribe(book => {
      this.saved.emit('success');
    });
  }

}
