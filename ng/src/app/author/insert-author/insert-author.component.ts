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

  get name() {
    return this.authorForm.get('name');
  }

  insert(): void {
    const author = new Author();
    author.name = this.authorForm.value.name;
    this.authorService.insert(author).subscribe(author => {
      this.authorService.addedAuthor();
      this.lastSavedAuthor = author;
      this.authorForm.reset();
      this.input.nativeElement.focus();
    });
  }

}
