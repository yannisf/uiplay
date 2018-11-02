import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Author} from '../author';
import {AuthorService} from '../author.service';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-insert-author',
  templateUrl: './insert-author.component.html',
  styleUrls: ['./insert-author.component.scss']
})
export class InsertAuthorComponent implements OnInit {

  author = new Author();
  authorForm = new FormGroup({
    name: new FormControl('', [
      Validators.required,
      Validators.minLength(2)])
  });
  lastSavedAuthor: Author;
  @ViewChild('inputAuthor') private input: ElementRef;

  constructor(private authorService: AuthorService) {
  }

  ngOnInit() {
    this.input.nativeElement.focus();
  }

  insert(): void {
    this.authorService.insert(this.author).subscribe(author => {
      this.authorService.addedAuthor();
      this.lastSavedAuthor = author;
      // this.authorName.setValue('');
      this.input.nativeElement.focus();
    });
  }

}
