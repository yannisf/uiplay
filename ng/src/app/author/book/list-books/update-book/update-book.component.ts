import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Book} from "../../book";
import {AuthorService} from "../../../author.service";

@Component({
  selector: 'app-update-book',
  templateUrl: './update-book.component.html',
  styleUrls: ['./update-book.component.scss']
})
export class UpdateBookComponent implements OnInit {

  @Input() book: Book;
  @Input() authorId: number;
  @Output() updated = new EventEmitter<string>();
  private updatedBook: Book;

  constructor(private authorService: AuthorService) { }

  ngOnInit() {
    this.updatedBook = JSON.parse(JSON.stringify(this.book));
  }

  cancelEdit() {
    this.updated.emit('cancel');
  }

  saveEdit(){
    this.authorService.addBook(this.authorId, this.updatedBook).subscribe(book => {
      this.updated.emit('success');
    });
  }

}
