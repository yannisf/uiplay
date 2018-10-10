import {AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Book} from "../../book";
import {AuthorService} from "../../../author.service";

@Component({
  selector: 'app-update-book',
  templateUrl: './update-book.component.html',
  styleUrls: ['./update-book.component.scss']
})
export class UpdateBookComponent implements OnInit, AfterViewInit {

  @Input() book: Book;
  @Input() authorId: number;
  @Output() updated = new EventEmitter<string>();
  updatedBook: Book;
  @ViewChild('input') private input: ElementRef;

  constructor(private authorService: AuthorService) { }

  ngOnInit() {
    this.updatedBook = JSON.parse(JSON.stringify(this.book));
  }

  ngAfterViewInit(): void {
    this.input.nativeElement.focus();
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
