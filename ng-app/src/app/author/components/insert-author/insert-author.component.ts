import {Component, ElementRef, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Author} from "../../author";

@Component({
  selector: 'app-insert-author',
  templateUrl: './insert-author.component.html'
})
export class InsertAuthorComponent implements OnInit {

  @Output() authorSubmitted = new EventEmitter<Author>();
  authorForm = new FormGroup({
    name: new FormControl('', [
      Validators.required,
      Validators.minLength(2)])
  });
  @ViewChild('inputAuthor') private input: ElementRef;

  get name() {
    return this.authorForm.get('name');
  }

  ngOnInit() {
    this.input.nativeElement.focus();
  }

  onSubmit() {
    const author = new Author();
    author.name = this.authorForm.value.name;
    this.authorSubmitted.emit(author);
    this.authorForm.reset();
  }

}
